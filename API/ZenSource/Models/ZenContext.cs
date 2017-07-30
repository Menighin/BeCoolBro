using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace ZenSource.Models
{
    public class ZenContext : DbContext
    {

        public ZenContext()
        {
            Database.EnsureCreated();
        }

        public DbSet<ZenQuote> ZenQuotes { get; set; }
        public DbSet<ZenMessage> ZenMessages { get; set; }
        public DbSet<Language> Languages { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var connectionString = Startup.Configuration.GetValue<string>("ConnectionString");
            optionsBuilder.UseNpgsql(connectionString);
                    
            base.OnConfiguring(optionsBuilder);
        }

    }
}
