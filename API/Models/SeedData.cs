using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class SeedData
    {

        private static Language _ptBr;
        private static Language _en;
        private static Dictionary<string, Tag> _tags;

        public static void Seed(ZenContext context)
        {

            _ptBr = context.Languages.Where(o => o.Code == "PT-BR").First();
            _en   = context.Languages.Where(o => o.Code == "EN").First();

            _tags = GetTags().ToDictionary(k => k.Name, v => v);
            context.Tags.AddRange(_tags.Values);
            context.SaveChanges();

            context.ZenQuotes.AddRange(GetQuotes1());
            context.SaveChanges();



        }

        private static List<Tag> GetTags()
        {
            return new List<Tag>()
            {
                new Tag() { Name = Tags.Music},
                new Tag() { Name = Tags.Peace},
                new Tag() { Name = Tags.Love},
                new Tag() { Name = Tags.Friendship},
                new Tag() { Name = Tags.Sad},
                new Tag() { Name = Tags.Family},
                new Tag() { Name = Tags.Movies},
                new Tag() { Name = Tags.Books},
                new Tag() { Name = Tags.Science},
                new Tag() { Name = Tags.Religion},
            };
        }

        public static List<ZenQuote> GetQuotes1()
        {
            return new List<ZenQuote>()
            {
                new ZenQuote()
                {
                    Author = "Bob Marley",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "One good thing about music: When it hits you, you feel no pain.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Uma coisa boa sobre música: Quando te acerta, você não sente dor.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Friedrich Nietzsche",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "And those who were seen dancing were thought to be insane by those who could not hear the music.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "E aqueles que estavam dançando eram ditos loucos por aqueles que não podiam ouvir a música.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Forrest Gump",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "My mom always said life was like a box of chocolates: You never know what you're gonna get.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "A vida é como uma caixa de chocolates. Você nunca sabe o que vai encontrar",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Stephen Chbosky, The Perks of Being a Wallflower",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "We accept the love we think we deserve.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Nós aceitamos o amor que acreditamos merecer.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Confucius",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Choose a job you love, and you will never have to work a day in your life.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Escolha um trabalho que você ame e não terá que trabalhar um único dia em sua vida.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "William Shakespeare",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Love looks not with the eyes, but with the mind, and therefore is wing'd cupid painted blind.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "O amor não se vê com os olhos mas com o coração.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Unknown",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Of life, I don't want much. I just want to know that I tried everything I wanted. I had all I could. I loved everything that was worth. And I lost only what, deep down, was never mine.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Da vida, não quero muito. Quero apenas saber que tentei tudo o que quis. Tive tudo o que pude. Amei tudo o que valia. E perdi apenas o que, no fundo, nunca foi meu.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Unknown",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "The first to apologize is the bravest. The first to forgive is the strongest. And the first to forget is the happiest.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "O primeiro a pedir desculpas é o mais corajoso. O primeiro a perdoar é o mais forte. E o primeiro a esquecer é o mais feliz.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Zig Ziglar",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "People often say that motivation doesn't last. Well, neither does bathing. That's why we recommend it daily.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "As pessoas costumam dizer que a motivação não dura sempre. Bem, nem o efeito do banho, por isso recomenda-se diariamente.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Zig Ziglar",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Your attitude, not your aptitude, will determine your altitude.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Sua atitude, não sua aptidão, irá determinar a sua altitude.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Zig Ziglar",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Failure is a detour, not a dead-end street.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Falhar é um desvio, não uma rua sem saída.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Mark Twain",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "It's not the size of the dog in the fight, it's the size of the fight in the dog.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Não é o tamanho do cão na luta, é o tamanho da luta no cão.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Mark Twain",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "Courage is resistance to fear, mastery of fear, not absence of fear.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "Coragem é a resistência ao medo, domínio do medo, e não a ausência do medo.",
                            Language = _ptBr
                        }
                    },
                },
                new ZenQuote()
                {
                    Author = "Mark Twain",
                    User = "Seed",
                    ZenMessages = new List<ZenMessage>()
                    {
                        new ZenMessage()
                        {
                            Message = "It is better to keep your mouth closed and let people think you are a fool than to open it and remove all doubt.",
                            Language = _en
                        },
                        new ZenMessage()
                        {
                            Message = "É melhor manter sua boca fechada e deixar os outros achando que você é um imbecíl do que abrí-la e não deixar dúvidas",
                            Language = _ptBr
                        }
                    },
                },
            };
        }


        public class Tags
        {
            public const string Music      = "music";
            public const string Peace      = "peace";
            public const string Love       = "love";
            public const string Friendship = "friendship";
            public const string Sad        = "sad";
            public const string Family     = "family";
            public const string Movies     = "movies";
            public const string Books      = "books";
            public const string Science    = "science";
            public const string Religion   = "religion";
        }

    }
}
