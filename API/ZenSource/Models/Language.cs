using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class Language
    {
        [Required]
        public int Id { get; set; }

        [Required]
        [StringLength(5, MinimumLength = 1)]
        public string Code { get; set; }

        [Required]
        [StringLength(30, MinimumLength = 1)]
        public string Description { get; set; }
    }
}
