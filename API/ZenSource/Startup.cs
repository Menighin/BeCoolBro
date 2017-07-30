﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using ZenSource.Models;
using ZenSource.Repositories;
using AutoMapper;
using ZenSource.ViewModel;
using ZenSource.Converters;
using Microsoft.EntityFrameworkCore;
using System.IO;
using Newtonsoft.Json;

namespace ZenSource
{
    public class Startup
    {
        public static IConfiguration Configuration;

        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile("sensitiveConfig.json", optional: false, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            // Add framework services.
            services.AddMvc();

            var connectionString = Configuration.GetValue<string>("ConnectionString");
            services.AddDbContext<ZenContext>(options =>
            {
                options.UseNpgsql(connectionString);
            });

            services.AddTransient<ZenContextSeedData>();
            services.AddScoped<ZenQuotesRepository>();

        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory, ZenContextSeedData seeder)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            app.UseDeveloperExceptionPage();
            app.UseMvc();
            seeder.Seed();

            Mapper.Initialize(config =>
            {
                config.CreateMap<IEnumerable<ZenQuote>, IEnumerable<ZenMessageViewModel>>().ConvertUsing<ZenQuoteConverter>();
            });

        }
    }
}
