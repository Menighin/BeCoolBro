using ImageSharp;
using ImageSharp.Drawing;
using ImageSharp.Drawing.Brushes;
using ImageSharp.Drawing.Pens;
using ImageSharp.Formats;
using Microsoft.AspNetCore.Hosting;
using SixLabors.Fonts;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Numerics;
using System.Threading.Tasks;
using ZenSource.Models.Interfaces;
using ZenSource.ViewModel;

namespace ZenSource.Models
{
    public class ZenQuoteImage
    {

        private Image<Rgba32> _image;
        private FontCollection _fonts;
        private FontFamily _font;
        private Font _fontAuthor;

        public static int BACK_NUM = 16;
        public static int IMAGE_WIDTH = 1024;
        public static int IMAGE_HEIGHT = 576;

        private List<Tuple<int, int>> _fontMaps = new List<Tuple<int, int>>()
        {
            new Tuple<int, int>(56, 34),
            new Tuple<int, int>(48, 29),
            new Tuple<int, int>(42, 25),
            new Tuple<int, int>(36, 22),
            new Tuple<int, int>(30, 19)
        };
        
        public ZenQuoteImage(IZenDrawable drawable, IHostingEnvironment _hostingEnvironment)
        {

            Random rdn = new Random();
            var quoteBackground = rdn.Next(1, BACK_NUM + 1);

            _image = Image.Load($"{_hostingEnvironment.ContentRootPath}\\wwwroot\\img\\back{quoteBackground}.jpg");

            _fonts = new FontCollection();
            _font = _fonts.Install($"{_hostingEnvironment.ContentRootPath}\\wwwroot\\fonts\\Cousine-Italic.ttf");
            _fontAuthor = new Font(_fonts.Install($"{_hostingEnvironment.ContentRootPath}\\wwwroot\\fonts\\Cousine.ttf"), 30);

            DrawText(drawable);

        }

        public Stream GetImage()
        {
            Stream output = new MemoryStream();
            _image.Save(output, new JpegEncoder());
            output.Seek(0, SeekOrigin.Begin);
            return output;
        }

        private void DrawText(IZenDrawable drawable)
        {
            var FONT_WIDTH = _fontMaps[0].Item2;
            var FONT_SIZE = _fontMaps[0].Item1;
            var X_TRANSLATE = 40;
            var Y_TRANSLATE = 40;
            var LINE_HEIGHT = _fontMaps[0].Item1 - 2;
            var MAX_WIDTH = 940;
            var MAX_HEIGHT = 492 - 30; // 30 is for Author name

            var quote = $" {drawable.Message}";

            var textOptions = new TextGraphicsOptions() { WrapTextWidth = MAX_WIDTH, VerticalAlignment = VerticalAlignment.Center};
            var font = new Font(_font, FONT_SIZE, FontStyle.Bold);
            var currentHeight = 0f;
            var currentWidth = 0f;

            while(true)
            {
                var renderOptions = new RendererOptions(font, 72) {WrappingWidth = textOptions.WrapTextWidth };
                var textMeasure = TextMeasurer.Measure(quote, renderOptions);
                currentHeight = textMeasure.Height;
                currentWidth = textMeasure.Width;

                if (currentHeight >= MAX_HEIGHT)
                {
                    FONT_SIZE -= 8;
                    font = new Font(_font, FONT_SIZE, FontStyle.Bold);
                }
                else
                    break;
            }

            var yDraw = (MAX_HEIGHT - currentHeight) > Y_TRANSLATE * 2 ? IMAGE_HEIGHT / 2 - Y_TRANSLATE * 2 : IMAGE_HEIGHT / 2;
            _image.DrawText(quote, font, Brushes.Solid(Rgba32.White), Pens.Solid(Rgba32.DimGray, 1), new Vector2(X_TRANSLATE, yDraw), textOptions);

            // Drawing Author name
            textOptions = new TextGraphicsOptions() { HorizontalAlignment = HorizontalAlignment.Right, VerticalAlignment = VerticalAlignment.Top };
            _image.DrawText(drawable.Author, _fontAuthor, Rgba32.White, new Vector2(currentWidth + X_TRANSLATE, yDraw + currentHeight/2 + 10), textOptions);
        }

    }
}
