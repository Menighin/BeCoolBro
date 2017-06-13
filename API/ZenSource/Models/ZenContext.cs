using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
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
            var connString = Startup.Configuration["Data:ConnectionString"];
            optionsBuilder.UseSqlServer(connString);
            base.OnConfiguring(optionsBuilder);
        }

    }
}
