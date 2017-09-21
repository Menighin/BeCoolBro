using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using ZenSource.Models;

namespace ZenSource.Repositories
{
    public class TagsRepository
    {

        private ZenContext _ctx;

        public TagsRepository(ZenContext ctx)
        {
            _ctx = ctx;
        }

        public IEnumerable<Tag> GetAll()
        {
            return _ctx.Set<Tag>().OrderBy(t => t.Name).ToList();
        }

    }
}
