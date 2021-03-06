using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models.Interfaces;

namespace ZenSource.ViewModel
{
    public class ZenMessageViewModel : IZenDrawable
    {
        public string Message { get; set; }
        public string Author { get; set; }
        public string Language { get; set; }
    }
}
