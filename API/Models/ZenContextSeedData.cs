using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenContextSeedData
    {
        private ZenContext _context;

        public ZenContextSeedData(ZenContext context)
        {
            _context = context;
        }

        public void Seed()
        {
            if (!_context.Languages.Any())
            {
                _context.Languages.Add(new Language()
                {
                    Code = "EN",
                    Description = "English"
                });

                _context.Languages.Add(new Language()
                {
                    Code = "PT-BR",
                    Description = "Português (Br)"
                });
            }

            _context.SaveChanges();

            if (!_context.ZenQuotes.Any())
            {
                var quote1 = new ZenQuote()
                {
                    Author = "Buddha",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Peace comes from within. Do not seek it without.",
                            Language = _context.Languages.Where(o => o.Code == "EN").First()
                        },
                        new ZenMessage()
                        {
                            Message = "A paz vem de dentro. Não a procure lá fora.",
                            Language = _context.Languages.Where(o => o.Code == "PT-BR").First()
                        }
                    },
                    User = "Seed",
                    CreatedOn = DateTime.Now,
                    Valid = true
                };

                var quote2 = new ZenQuote()
                {
                    Author = "Nietzsche",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Without music, life would be a mistake.",
                            Language = _context.Languages.Where(o => o.Code == "EN").First()
                        },
                        new ZenMessage()
                        {
                            Message = "Sem música a vida seria um erro.",
                            Language = _context.Languages.Where(o => o.Code == "PT-BR").First()
                        }
                    },
                    User = "Seed",
                    CreatedOn = DateTime.Now,
                    Valid = true
                };

                var quote3 = new ZenQuote()
                {
                    Author = "Test",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a ",
                            Language = _context.Languages.Where(o => o.Code == "EN").First()
                        },
                        new ZenMessage()
                        {
                            Message = "Sem música a vida seria um erro.",
                            Language = _context.Languages.Where(o => o.Code == "PT-BR").First()
                        }
                    },
                    User = "Seed",
                    CreatedOn = DateTime.Now,
                    Valid = true
                };

                _context.ZenQuotes.AddRange(quote1, quote2, quote3);
                _context.ZenMessages.AddRange(quote1.ZenMessages);
                _context.ZenMessages.AddRange(quote2.ZenMessages);
                _context.ZenMessages.AddRange(quote3.ZenMessages);

                _context.SaveChanges();

            }
        }

    }
}
