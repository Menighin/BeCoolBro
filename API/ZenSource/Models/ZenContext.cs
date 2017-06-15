using Microsoft.EntityFrameworkCore;
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
            try
            {
                using (var stream = new FileStream(@"sensitiveConfig.json", FileMode.Open))
                using (StreamReader r = new StreamReader(stream))
                {
                    string json = r.ReadToEnd();
                    try
                    {
                        var jsonDict = JsonConvert.DeserializeObject<Dictionary<string, string>>(json);
                        optionsBuilder.UseNpgsql(jsonDict["ConnectionString"]);
                    } catch (Exception e)
                    {
                        throw (new Exception(@"The property 'ConnectionString' was not found on the 'sensitiveConfig.json' file"));
                    }

                    base.OnConfiguring(optionsBuilder);
                }
            } catch (FileNotFoundException e)
            {
                throw (new Exception(@"The file 'sensitiveConfig.json' was not found at the root of the project."));
            }
        }

    }
}
