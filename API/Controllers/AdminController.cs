using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication;
using System.Security.Claims;
using Microsoft.Extensions.Configuration;
using System.Net;

namespace ZenSource.Controllers
{
    [Route("api")]
    public class AdminController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        [Route("test")]
        [Authorize]
        public string Test()
        {
            return "You are logged";
        }

        [Route("login")]
        public async void Login([FromBody] Dictionary<string, string> data)
        {

            // If already logged in
            if (HttpContext.User.Identity.AuthenticationType == "Hu3")
            {
                Response.StatusCode = (int)HttpStatusCode.OK;
                return;
            }

            var admCred = Startup.Configuration.GetValue<string>("AdmAreaCredentials");
            if (data["password"] == admCred)
            {
                var principal = new ClaimsPrincipal(new ClaimsIdentity(new Claim[] { new Claim("Permission", "CanViewPage") }, "Hu3"));
                await HttpContext.SignInAsync("ZenCookieAuthenticationScheme", principal);
                Response.StatusCode = (int) HttpStatusCode.OK;
            }
            else
            {
                Response.StatusCode = (int) HttpStatusCode.Unauthorized;
            }
        }

    }
}
