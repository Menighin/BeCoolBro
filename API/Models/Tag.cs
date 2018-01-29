using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class Tag
    {
        public int Id { get; set; }

        [Required]
        [MaxLength(20)]
        public string Name { get; set; }

        [MaxLength(20)]
        public string Color { get; set; }

        public ICollection<ZenQuoteTag> ZenQuoteTags { get; set; }

        public Tag()
        {
            Color = "";
        }

    }
}
