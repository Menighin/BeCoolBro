using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ZenSource.Models;
using ZenSource.ViewModel;
using AutoMapper;
using ZenSource.Repositories;
using ZenSource.Converters;
using ImageSharp;
using System.IO;
using SixLabors.Fonts;
using SixLabors.Shapes;
using System.Numerics;
using Newtonsoft.Json;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;

namespace ZenSource.Controllers
{
    [Route("api/zen")]
    public class ZenController : Controller
    {
        private readonly ZenQuotesRepository _repository;
        private readonly IHostingEnvironment _hostingEnvironment;

        public ZenController(ZenQuotesRepository zenRepository, IHostingEnvironment hostingEnvironment)
        {
            _repository = zenRepository;
            _hostingEnvironment = hostingEnvironment;
        }

        /// <summary>
        /// Get all the Zen Messages in all languages
        /// </summary>
        /// <returns>Array of ZenMessageViewModels</returns>
        [HttpGet]
        public JsonResult Get(int? page, bool? valid, string search, string tags)
        {
            var tagsIds = new List<int>();
            if (tags != null) tagsIds = tags.Split(',').Select(t => Convert.ToInt32(t)).ToList();

            var modelList = _repository.GetAll(search, null, tagsIds, page, null, valid);

            var viewModelList = Mapper.Map<IEnumerable<ZenMessageViewModel>>(modelList);

            return Json(viewModelList);
        }
        
        [HttpGet("{id}")]
        public JsonResult Get(int id, string l)
        {
            var quote = _repository.GetById(id);

            if (l == null || l.Length == 0)
                return Json(Mapper.Map<IEnumerable<ZenMessageViewModel>>(new List<ZenQuote>() { quote }));
            return Json(ZenMessageConverter.Convert(quote, l));
        }

        [HttpGet("image/{id}")]
        public IActionResult Image(int id, string l)
        {
            var quote = _repository.GetById(id);

            if (l == null || l.Length == 0) l = "EN";

            var viewModel = ZenMessageConverter.Convert(quote, l);

            var quoteImage = new ZenQuoteImage(viewModel, _hostingEnvironment);

            return File(quoteImage.GetImage(), "image/png");

        }

        [HttpGet("images")]
        public IActionResult Images(string search, string ids, int? page, string l, string tags)
        {
            var tagsIds = new List<int>();
            if (tags != null) tagsIds = tags.Split(',').Select(t => Convert.ToInt32(t)).ToList();

            var quotesIds = new List<int>();
            if (ids != null) quotesIds = ids.Split(',').Select(t => Convert.ToInt32(t)).ToList();

            var modelList = _repository.GetAll(search, quotesIds, tagsIds, page, l, true);

            if (l == null)
                l = "en";

            var viewModelList = Mapper.Map<IEnumerable<ZenQuoteViewModel>>(modelList).Where(q => q.Language.Equals(l, StringComparison.OrdinalIgnoreCase)).ToList();
            foreach (var v in viewModelList)
            {
                var img = new ZenQuoteImage(v, _hostingEnvironment).GetImage();
                var base64 = Convert.ToBase64String(ReadStream(img));
                v.Image64Encoded = base64;
            }

            return Json(viewModelList);

        }

        [HttpGet("invalid")]
        public IActionResult GetInvalidQuotes()
        {
            var modelList = _repository.GetAll(null, null, null, null, null, false);

            var viewModelList = Mapper.Map<IEnumerable<ZenQuoteFullViewModel>>(modelList);

            return Json(viewModelList);
        }

        [HttpPut("{id}/rate")]
        public bool Rate(int id, [FromBody] Dictionary<string, int> data)
        {
            try
            {
                _repository.UpdateRate(data["id"], data["like"], data["dislike"]);
            }
            catch (Exception e)
            {
                return false;
            }

            return true;
        }

        [HttpPut("{id}/validate")]
        public bool Validate(int id, [FromBody] ZenQuoteValidateModel quote)
        {
            try
            {
                _repository.ValidateQuote(quote);
            }
            catch (Exception e)
            {
                return false;
            }

            return true;
        }

        [HttpPost]
        public void SaveZenQuote([FromBody] ZenQuotePostModel postData)
        {

            var zenMessages = new List<ZenMessage>();
            foreach (var m in postData.Messages)
            {
                zenMessages.Add(new ZenMessage
                {
                    IdLanguage = Convert.ToInt32(m["language"]),
                    Message = m["message"]
                });
            }

            var tags = new List<ZenQuoteTag>();
            foreach (var t in postData.Tags)
            {
                tags.Add(new ZenQuoteTag()
                {
                    TagId = t
                });
            }

            var zenQuote = new ZenQuote()
            {
                Author = postData.Author,
                CreatedOn = DateTime.Now,
                Dislikes = 0,
                Likes = 0,
                User = postData.User,
                Valid = false,
                ZenMessages = zenMessages,
                ZenQuoteTags = tags
            };

            _repository.Save(zenQuote);
        }

        private byte[] ReadStream(Stream stream, int initialLength = 0)
        {
            if (initialLength < 1)
            {
                initialLength = 32768;
            }
            byte[] buffer = new byte[initialLength];
            int read = 0;
            int chunk;
            while ((chunk = stream.Read(buffer, read, buffer.Length - read)) > 0)
            {
                read += chunk;
                if (read == buffer.Length)
                {
                    int nextByte = stream.ReadByte();
                    if (nextByte == -1)
                    {
                        return buffer;
                    }
                    byte[] newBuffer = new byte[buffer.Length * 2];
                    Array.Copy(buffer, newBuffer, buffer.Length);
                    newBuffer[read] = (byte)nextByte;
                    buffer = newBuffer;
                    read++;
                }
            }
            byte[] bytes = new byte[read];
            Array.Copy(buffer, bytes, read);
            return bytes;
        }

        public class ZenQuotePostModel
        {
            public string User { get; set; }
            public string Author { get; set; }
            public List<int> Tags { get; set; }
            public List<Dictionary<string, string>> Messages { get; set; }
        }

        public class ZenQuoteValidateModel
        {
            public int Id { get; set; }
            public string Author { get; set; }
            public string En { get; set; }
            public string PtBr { get; set; }
        }
    }
}
