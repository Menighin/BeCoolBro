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

        [HttpGet("Test/{id}")]
        public IActionResult Test(int id, string l)
        {
            var quote = _repository.GetById(id);

            if (l == null || l.Length == 0) l = "EN";

            var viewModel = ZenQuoteConverter.Convert(quote, l);

            var quoteImage = new ZenQuoteImage(viewModel, _hostingEnvironment);

            return File(quoteImage.GetImage(), "image/png");

        }
    }
}
