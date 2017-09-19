using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using ZenSource.Models;

namespace ZenSource.Repositories
{
    public class LanguagesRepository
    {

        private ZenContext _ctx;

        public LanguagesRepository(ZenContext ctx)
        {
            _ctx = ctx;
        }

        public IEnumerable<Language> GetAll()
        {
            return _ctx.Set<Language>().ToList();
        }

    }
}
