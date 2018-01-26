using AutoMapper;
using Microsoft.AspNetCore.Hosting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models;
using ZenSource.Utils;
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
                  Id = s.Id,
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

        public static ZenQuoteViewModel Convert(ZenQuote zenQuote, string language = "EN", IHostingEnvironment hostingEnvironment = null)
        {

            var message = zenQuote.ZenMessages.FirstOrDefault(o => o.Language.Code.Equals(language, StringComparison.OrdinalIgnoreCase));
            if (message == null) return null;

            var z = new ZenQuoteViewModel
            {
                Id = zenQuote.Id,
                Message = message.Message,
                Author = zenQuote.Author,
                Language = message.Language.Code,
                CreatedOn = zenQuote.CreatedOn,
                Likes = zenQuote.Likes,
                Dislikes = zenQuote.Dislikes,
                Tags = zenQuote.ZenQuoteTags?.Select(t => t.Tag.Name).ToList()
            };

            if (hostingEnvironment != null)
            {
                var img = new ZenQuoteImage(z, hostingEnvironment).GetImage();
                var base64 = System.Convert.ToBase64String(ZenSourceUtil.ReadStream(img));
                z.Image64Encoded = base64;
            }

            return z;

        }
    }
}
