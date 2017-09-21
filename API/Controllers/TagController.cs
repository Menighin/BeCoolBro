using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Repositories;

namespace ZenSource.Controllers
{

    [Route("api/zen")]
    public class TagController : Controller
    {

        private readonly TagsRepository _repository;

        public TagController(TagsRepository tagRepository)
        {
            _repository = tagRepository;
        }

        [HttpGet("tags")]
        public JsonResult Get()
        {
            var modelList = _repository.GetAll();

            return Json(modelList);
        }

    }
}
