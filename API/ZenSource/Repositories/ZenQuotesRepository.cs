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

        public ZenQuotesRepository(ZenContext context)
        {
            _context = context;
        }

        public IEnumerable<ZenQuote> GetAll()
        {
            return _context.ZenQuotes
                .Include(o => o.Messages)
                .ThenInclude(o => o.Language)
                .ToList();
        }

        public ZenQuote GetById(int id)
        {
            return _context.ZenQuotes
                .Where(o => o.Id == id)
                .Include(o => o.Messages)
                .ThenInclude(o => o.Language)
                .FirstOrDefault();
        }
    }
}
