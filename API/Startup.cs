using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.SpaServices.Webpack;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using ZenSource.Models;
using ZenSource.Repositories;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using ZenSource.ViewModel;
using ZenSource.Converters;

namespace ZenSource
{
    public class Startup
    {

        public static IConfiguration Configuration;


        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
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
            services.AddScoped<LanguagesRepository>();
            services.AddScoped<TagsRepository>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory, ZenContextSeedData seeder)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            app.UseDeveloperExceptionPage();
            seeder.Seed();

            Mapper.Initialize(config =>
            {
                config.CreateMap<IEnumerable<ZenQuote>, IEnumerable<ZenMessageViewModel>>().ConvertUsing<ZenMessageConverter>();
                config.CreateMap<IEnumerable<ZenQuote>, IEnumerable<ZenQuoteViewModel>>().ConvertUsing<ZenQuoteConverter>();
            });

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseWebpackDevMiddleware(new WebpackDevMiddlewareOptions
                {
                    HotModuleReplacement = true
                });
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
            }

            app.UseStaticFiles();

            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");

                routes.MapSpaFallbackRoute(
                    name: "spa-fallback",
                    defaults: new { controller = "Home", action = "Index" });
            });
        }
    }
}
