using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models;
using ZenSource.ViewModel;

namespace ZenSource.Repositories
{
    public class ZenQuotesRepository
    {
        private ZenContext _context;

        private readonly int PAGE_SIZE = 2;

        public ZenQuotesRepository(ZenContext context)
        {
            _context = context;
        }

        public IEnumerable<ZenQuote> GetAll(int? page = null)
        {
            var query = _context.Set<ZenQuote>().AsQueryable();

            if (page != null)
            {
                int p = Convert.ToInt32(page) - 1;
                if (p < 0) p = 0;
                query = query
                    .Skip(p * PAGE_SIZE)
                    .Take(PAGE_SIZE);
            }

            query = query
                .Include(o => o.ZenMessages)
                .ThenInclude(o => o.Language)
                .Include(o => o.ZenQuoteTags)
                .ThenInclude(o => o.Tag);

            return query
                .ToList();
        }

        public ZenQuote GetById(int id)
        {
            return _context.ZenQuotes
                .Where(o => o.Id == id)
                .Include(o => o.ZenMessages)
                .ThenInclude(o => o.Language)
                .FirstOrDefault();
        }
    }
}
