using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenQuote
    {

        [Required]
        public int Id { get; set; }

        [Required]
        [MaxLength(50)]
        public string Author { get; set; }

        public ICollection<ZenMessage> ZenMessages { get; set; }

        public ICollection<ZenQuoteTag> ZenQuoteTags { get; set; }

        [Required]
        public bool Valid { get; set; }

        [Required]
        public string User { get; set; }

        [Required]
        public DateTime CreatedOn { get; set; }

        public long Likes { get; set; }

        public long Dislikes { get; set; }

        public ZenQuote()
        {
            CreatedOn = new DateTime();
            Likes = 0;
            Dislikes = 0;
            Valid = true;
        }
    }
}
