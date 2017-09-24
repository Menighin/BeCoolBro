using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using ZenSource.Models;

namespace ZenSource.Repositories
{
    public class ZenQuotesRepository
    {
        private ZenContext _ctx;

        private readonly int PAGE_SIZE = 2;

        public ZenQuotesRepository(ZenContext context)
        {
            _ctx = context;
        }

        public IEnumerable<ZenQuote> GetAll(int? page = null, bool? valid = null)
        {
            var query = _ctx.Set<ZenQuote>().AsQueryable();

            if (valid != null)
            {
                query = query.Where(q => q.Valid == valid.Value);
            }

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
            return _ctx.ZenQuotes
                .Where(o => o.Id == id)
                .Include(o => o.ZenMessages)
                .ThenInclude(o => o.Language)
                .FirstOrDefault();
        }

        public int UpdateRate(int id, int like, int dislike)
        {
            // TODO: Use Optmistic Concurrency approach to be thread safe
            var quote = _ctx.Set<ZenQuote>().First(o => o.Id == id);

            if (like > 0) quote.Likes++;
            else if (like < 0) quote.Likes--;

            if (dislike > 0) quote.Dislikes++;
            else if (dislike < 0) quote.Dislikes--;

            return _ctx.SaveChanges();

        }

        public void Save(ZenQuote zenQuote)
        {
            _ctx.ZenQuotes.Add(zenQuote);
            _ctx.SaveChanges();
        }
    }
}
