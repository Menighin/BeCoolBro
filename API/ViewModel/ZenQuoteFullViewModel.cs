using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.ViewModel
{
    public class ZenQuoteFullViewModel
    {
        public int Id { get; set; }
        public string Author { get; set; }
        public DateTime CreatedOn { get; set; }
        public long Likes { get; set; }
        public long Dislikes { get; set; }
        public List<ZenTagViewModel> Tags { get; set; }
        public List<MessageViewModel> Messages { get; set; }
    }
}
