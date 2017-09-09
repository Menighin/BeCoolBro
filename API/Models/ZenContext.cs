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

        public ZenContext(DbContextOptions<ZenContext> options) : base(options)
        {
        }

        public ZenContext()
        {
        }

        public DbSet<ZenQuote> ZenQuotes { get; set; }
        public DbSet<ZenMessage> ZenMessages { get; set; }
        public DbSet<Language> Languages { get; set; }
        public DbSet<Tag> Tags { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var connectionString = Startup.Configuration.GetValue<string>("ConnectionString");
            optionsBuilder.UseNpgsql(connectionString);
                    
            base.OnConfiguring(optionsBuilder);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<ZenQuoteTag>()
                .HasKey(t => new { t.ZenQuoteId, t.TagId });

            modelBuilder.Entity<ZenQuoteTag>()
                .HasOne(zqt => zqt.ZenQuote)
                .WithMany(z => z.ZenQuoteTags)
                .HasForeignKey(zqt => zqt.ZenQuoteId);

            modelBuilder.Entity<ZenQuoteTag>()
                .HasOne(zqt => zqt.Tag)
                .WithMany(z => z.ZenQuoteTags)
                .HasForeignKey(zqt => zqt.TagId);
        }

    }
}
