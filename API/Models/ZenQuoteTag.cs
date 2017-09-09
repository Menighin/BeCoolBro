using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenQuoteTag
    {
        public int ZenQuoteId { get; set; }

        [ForeignKey("ZenQuoteId")]
        public ZenQuote ZenQuote { get; set; }

        public int TagId { get; set; }

        [ForeignKey("TagId")]
        public Tag Tag { get; set; }
    }
}
