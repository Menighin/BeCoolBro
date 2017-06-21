﻿using ImageSharp;
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
            new Tuple<int, int>(56, 34),
            new Tuple<int, int>(48, 29),
            new Tuple<int, int>(42, 25),
            new Tuple<int, int>(36, 22),
            new Tuple<int, int>(30, 19)
        };
        
        public ZenQuoteImage(ZenMessageViewModel viewModel)
        {
            _image = Image.Load(@"Resources/imgs/quote_background.png");

            _fonts = new FontCollection();
            _font = _fonts.Install(@"Resources/fonts/Cousine-Italic.ttf");

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
            var MAX_WIDTH = 1050;
            var MAX_HEIGHT = 590;

            var words = viewModel.Message.Split(' ');

            // Find the right font size so it can fit on the specs above
            var fit = false;
            var fontConfigCount = 1;
            var text = new List<String>() { "\"" };
            while (!fit && fontConfigCount < _fontMaps.Count + 1)
            {
                var lineNumber = 0;
                foreach (var w in words)
                {
                    if ((text[lineNumber].Length + w.Length) * FONT_WIDTH + X_TRANSLATE < MAX_WIDTH - X_TRANSLATE)
                    {
                        text[lineNumber] += w + " ";
                    }
                    else
                    {
                        text.Add(" " + w + " ");
                        lineNumber++;
                    }
                }

                // Remove last space
                text[lineNumber] = text[lineNumber].Remove(text[lineNumber].Length - 1);
                text[lineNumber] += "\"";

                if (text.Count * LINE_HEIGHT + Y_TRANSLATE < MAX_HEIGHT || fontConfigCount == _fontMaps.Count)
                {
                    fit = true;
                }
                else
                {
                    FONT_SIZE = _fontMaps[fontConfigCount].Item1;
                    FONT_WIDTH = _fontMaps[fontConfigCount].Item2;
                    LINE_HEIGHT = _fontMaps[fontConfigCount].Item1 - 2;
                    fontConfigCount++;
                    text = new List<String>() { "\"" };
                }

            }

            // Draw the text
            var font = new Font(_font, FONT_SIZE);
            var y = 1;
            var maxX = 0;
            foreach (var l in text)
            {
                _image.DrawText(l, font, Rgba32.White, new Vector2(X_TRANSLATE, Y_TRANSLATE + (y++) * LINE_HEIGHT));
                if (l.Length * FONT_WIDTH + X_TRANSLATE > maxX)
                    maxX = l.Length * FONT_WIDTH + X_TRANSLATE;
            }

            // Drawing Author name

            // Finding the position
            var authorFontWidth = 19;
            var authorX = maxX - viewModel.Author.Length * authorFontWidth;

            while (authorX + viewModel.Author.Length * authorFontWidth > MAX_WIDTH - X_TRANSLATE)
                authorX -= 10;

            // Defining the Font
            var fontAuthor = new Font(_fonts.Install(@"Resources/fonts/Cousine.ttf"), 30);

            _image.DrawText(viewModel.Author, fontAuthor, Rgba32.White, new Vector2(authorX, Y_TRANSLATE + (y++) * LINE_HEIGHT));
        }

    }
}