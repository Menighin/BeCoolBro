using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models;
using ZenSource.ViewModel;

namespace ZenSource.Converters
{
    public class ZenQuoteConverter : ITypeConverter<IEnumerable<ZenQuote>, IEnumerable<ZenMessageViewModel>>
    {
        public IEnumerable<ZenMessageViewModel> Convert(IEnumerable<ZenQuote> source, IEnumerable<ZenMessageViewModel> destination, ResolutionContext context)
        {
            return source
            .SelectMany(s => s.Messages
              .Select(o => new ZenMessageViewModel
              {
                  Message = o.Message,
                  Author = s.Author,
                  Language = o.Language.Code
              }))
              .ToList();
        }

        public static ZenMessageViewModel Convert(ZenQuote zenQuote, string language = "EN")
        {
            return zenQuote.Messages.Where(o => o.Language.Code.ToLower() == language.ToLower())
                .Select(o => new ZenMessageViewModel
                {
                    Message = o.Message,
                    Author = zenQuote.Author,
                    Language = o.Language.Code
                })
                .FirstOrDefault();
        }
    }
}
