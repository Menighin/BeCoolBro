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
        [StringLength(50, MinimumLength = 1)]
        public string Author { get; set; }

        public ICollection<ZenMessage> Messages { get; set; }

        [Required]
        public bool Valid { get; set; }

        [Required]
        public string User { get; set; }

        [Required]
        public DateTime CreatedOn { get; set; }

    }
}
