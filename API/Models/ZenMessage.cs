using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenMessage
    {

        [Required]
        public int Id { get; set; }

        [Required]
        [StringLength(500, MinimumLength = 1)]
        public string Message { get; set; }

        [Required]
        public Language Language { get; set; }

    }
}
