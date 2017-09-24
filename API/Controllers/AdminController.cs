using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication;
using System.Security.Claims;

namespace ZenSource.Controllers
{
    [Route("api/admin")]
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

        [Route("authorize")]
        public async void AuthorizeAsync()
        {
            var principal = new ClaimsPrincipal(new ClaimsIdentity(new Claim[] { new Claim("Permission", "CanViewPage") }, "Hu3"));
            await HttpContext.SignInAsync("ZenCookieAuthenticationScheme", principal);
        }

    }
}
