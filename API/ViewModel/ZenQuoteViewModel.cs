using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZenSource.Models.Interfaces;

namespace ZenSource.ViewModel
{
    public class ZenQuoteViewModel : IZenDrawable
    {
        public int Id { get; set; }
        public string Message { get; set; }
        public string Author { get; set; }
        public string Language { get; set; }
        public string Image64Encoded { get; set; }
        public DateTime CreatedOn { get; set; }
        public long Likes { get; set; }
        public long Dislikes { get; set; }
        public List<string> Tags { get; set; }
    }
}
