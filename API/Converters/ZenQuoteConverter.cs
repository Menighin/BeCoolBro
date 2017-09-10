using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models;
using ZenSource.ViewModel;

namespace ZenSource.Converters
{
    public class ZenQuoteConverter : ITypeConverter<IEnumerable<ZenQuote>, IEnumerable<ZenQuoteViewModel>>
    {
        public IEnumerable<ZenQuoteViewModel> Convert(IEnumerable<ZenQuote> source, IEnumerable<ZenQuoteViewModel> destination, ResolutionContext context)
        {
            return source
            .SelectMany(s => s.ZenMessages
              .Select(o => new ZenQuoteViewModel
              {
                  Id = o.Id,
                  Message = o.Message,
                  Author = s.Author,
                  Language = o.Language.Code,
                  CreatedOn = s.CreatedOn,
                  Likes = s.Likes,
                  Dislikes = s.Dislikes,
                  Tags = s.ZenQuoteTags.Select(t => t.Tag.Name).ToList()
              }))
              .ToList();
        }
    }
}
