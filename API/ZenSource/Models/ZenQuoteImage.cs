using ImageSharp;
using SixLabors.Fonts;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Numerics;
using System.Threading.Tasks;
using ZenSource.ViewModel;

namespace ZenSource.Models
{
    public class ZenQuoteImage
    {

        private Image<Rgba32> _image;
        private FontCollection _fonts;
        private Font _font;

        private List<Tuple<int, int>> _fontMaps = new List<Tuple<int, int>>()
        {
            new Tuple<int, int>(48, 21),
            new Tuple<int, int>(42, 18),
            new Tuple<int, int>(36, 15),
            new Tuple<int, int>(30, 12)
        };

        public ZenQuoteImage(ZenMessageViewModel viewModel)
        {
            _image = Image.Load(@"Resources/imgs/quote_background.png");

            _fonts = new FontCollection();
            _font = _fonts.Install(@"Resources/fonts/Cousine.ttf");

            DrawText(viewModel);

        }

        public Stream GetImage()
        {
            Stream output = new MemoryStream();
            _image.Save(output);
            output.Seek(0, SeekOrigin.Begin);
            return output;
        }

        private void DrawText(ZenMessageViewModel viewModel)
        {
            var FONT_WIDTH = _fontMaps[0].Item2;
            var FONT_SIZE = _fontMaps[0].Item1;
            var X_TRANSLATE = 50;
            var Y_TRANSLATE = 90;
            var LINE_HEIGHT = _fontMaps[0].Item1 - 2;
            var MAX_WIDTH = 600;
            var MAX_HEIGHT = 380;

            var words = viewModel.Message.Split(' ');

            // Find the right font size so it can fit on the specs above
            var fit = false;
            var fontConfigCount = 1;
            var text = new List<String>() { "" };
            while (!fit && fontConfigCount < _fontMaps.Count + 1)
            {
                var lineNumber = 0;
                foreach (var w in words)
                {
                    if ((text[lineNumber].Length + w.Length) * FONT_WIDTH < MAX_WIDTH)
                    {
                        text[lineNumber] += w + " ";
                    }
                    else
                    {
                        text.Add(w + " ");
                        lineNumber++;
                    }
                }

                if (text.Count * LINE_HEIGHT < MAX_HEIGHT || fontConfigCount == _fontMaps.Count)
                {
                    fit = true;
                }
                else
                {
                    FONT_SIZE = _fontMaps[fontConfigCount].Item1;
                    FONT_WIDTH = _fontMaps[fontConfigCount].Item2;
                    LINE_HEIGHT = _fontMaps[fontConfigCount].Item1 - 2;
                    fontConfigCount++;
                    text = new List<String>() { "" };
                }

            }

            // Draw the text
            var font = new Font(_font, FONT_SIZE);
            var y = 1;
            foreach (var l in text)
            {
                _image.DrawText(l, font, Rgba32.White, new Vector2(X_TRANSLATE, Y_TRANSLATE + (y++) * LINE_HEIGHT));
            }

            // TODO: Draw author name
            
        }

    }
}
