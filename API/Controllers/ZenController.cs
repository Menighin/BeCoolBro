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
        public JsonResult Get(int? page)
        {
            var modelList = _repository.GetAll(page);

            var viewModelList = Mapper.Map<IEnumerable<ZenMessageViewModel>>(modelList);

            return Json(viewModelList);
        }
        
        [HttpGet("{id}")]
        public JsonResult Get(int id, string l)
        {
            var quote = _repository.GetById(id);

            if (l == null || l.Length == 0)
                return Json(Mapper.Map<IEnumerable<ZenMessageViewModel>>(new List<ZenQuote>() { quote }));
            return Json(ZenQuoteConverter.Convert(quote, l));
        }

        [HttpGet("image/{id}")]
        public IActionResult Image(int id, string l)
        {
            var quote = _repository.GetById(id);

            if (l == null || l.Length == 0) l = "EN";

            var viewModel = ZenQuoteConverter.Convert(quote, l);

            var quoteImage = new ZenQuoteImage(viewModel, _hostingEnvironment);

            return File(quoteImage.GetImage(), "image/png");

        }

        [HttpGet("Test")]
        public IActionResult Test(string l)
        {
            var quote1 = _repository.GetById(1);
            var quote2 = _repository.GetById(2);

            if (l == null || l.Length == 0) l = "EN";

            var viewModel1 = ZenQuoteConverter.Convert(quote1, l);
            var viewModel2 = ZenQuoteConverter.Convert(quote2, l);

            var quoteImage1 = new ZenQuoteImage(viewModel1, _hostingEnvironment).GetImage();
            var quoteImage2 = new ZenQuoteImage(viewModel2, _hostingEnvironment).GetImage();

            var img1 = Convert.ToBase64String(ReadStream(quoteImage1));
            var img2 = Convert.ToBase64String(ReadStream(quoteImage2));

            var jsonResult = Json(new object[] { img1, img2 });

            return jsonResult;

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
    }
}
