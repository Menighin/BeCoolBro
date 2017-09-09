using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models.Interfaces
{
    public interface IZenDrawable
    {
        string Author { get; }
        string Message { get;  }
    }
}
