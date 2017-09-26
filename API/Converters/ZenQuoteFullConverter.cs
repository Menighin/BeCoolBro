using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models;
using ZenSource.ViewModel;

namespace ZenSource.Converters
{
    public class ZenQuoteFullConverter : ITypeConverter<IEnumerable<ZenQuote>, IEnumerable<ZenQuoteFullViewModel>>
    {
        public IEnumerable<ZenQuoteFullViewModel> Convert(IEnumerable<ZenQuote> source, IEnumerable<ZenQuoteFullViewModel> destination, ResolutionContext context)
        {

            return source.Select(q => new ZenQuoteFullViewModel()
            {
                Id = q.Id,
                Author = q.Author,
                CreatedOn = q.CreatedOn,
                Dislikes = q.Dislikes,
                Likes = q.Likes,
                Messages = q.ZenMessages.Select(m => new MessageViewModel()
                                            {
                                                Id = m.Id,
                                                Message = m.Message,
                                                Language = new LanguageViewModel() { Id = m.Language.Id, Code = m.Language.Code }
                                            }).ToList(),
                Tags = q.ZenQuoteTags.Select(t => new ZenTagViewModel()
                                            {
                                                Id = t.TagId,
                                                Name = t.Tag.Name
                                            }).ToList()
            }).ToList();
        }
    }
}
