using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.ViewModel
{
    public class MessageViewModel
    {
        public int Id { get; set; }
        public string Message { get; set; }
        public LanguageViewModel Language { get; set; }
    }
}
