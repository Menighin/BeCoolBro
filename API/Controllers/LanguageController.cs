using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Repositories;

namespace ZenSource.Controllers
{

    [Route("api/zen")]
    public class LanguageController : Controller
    {

        private readonly LanguagesRepository _repository;

        public LanguageController(LanguagesRepository languageRepository)
        {
            _repository = languageRepository;
        }

        [HttpGet("languages")]
        public JsonResult Get()
        {
            var modelList = _repository.GetAll();

            return Json(modelList);
        }

    }
}
