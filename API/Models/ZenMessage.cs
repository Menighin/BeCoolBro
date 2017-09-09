using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenMessage
    {
        [Required]
        public int Id { get; set; }

        public int IdZenQuote { get; set; }

        public int IdLanguage { get; set; }

        [Required]
        [MaxLength(500)]
        public string Message { get; set; }

        [ForeignKey("IdLanguage")]
        public Language Language { get; set; }

        [ForeignKey("IdZenQuote")]
        public ZenQuote ZenQuote { get; set; }

    }
}
