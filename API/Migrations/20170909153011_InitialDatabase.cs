using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using System;
using System.Collections.Generic;

namespace ZenSource.Migrations
{
    public partial class InitialDatabase : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Languages",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int4", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn),
                    Code = table.Column<string>(type: "varchar(5)", maxLength: 5, nullable: false),
                    Description = table.Column<string>(type: "varchar(30)", maxLength: 30, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Languages", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Tags",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int4", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn),
                    Name = table.Column<string>(type: "varchar(20)", maxLength: 20, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Tags", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ZenQuotes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int4", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn),
                    Author = table.Column<string>(type: "varchar(50)", maxLength: 50, nullable: false),
                    CreatedOn = table.Column<DateTime>(type: "timestamp", nullable: false),
                    Dislikes = table.Column<long>(type: "int8", nullable: false),
                    Likes = table.Column<long>(type: "int8", nullable: false),
                    User = table.Column<string>(type: "text", nullable: false),
                    Valid = table.Column<bool>(type: "bool", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ZenQuotes", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ZenMessages",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int4", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn),
                    IdLanguage = table.Column<int>(type: "int4", nullable: false),
                    IdZenQuote = table.Column<int>(type: "int4", nullable: false),
                    Message = table.Column<string>(type: "varchar(500)", maxLength: 500, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ZenMessages", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ZenMessages_Languages_IdLanguage",
                        column: x => x.IdLanguage,
                        principalTable: "Languages",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ZenMessages_ZenQuotes_IdZenQuote",
                        column: x => x.IdZenQuote,
                        principalTable: "ZenQuotes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ZenQuoteTag",
                columns: table => new
                {
                    ZenQuoteId = table.Column<int>(type: "int4", nullable: false),
                    TagId = table.Column<int>(type: "int4", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ZenQuoteTag", x => new { x.ZenQuoteId, x.TagId });
                    table.ForeignKey(
                        name: "FK_ZenQuoteTag_Tags_TagId",
                        column: x => x.TagId,
                        principalTable: "Tags",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ZenQuoteTag_ZenQuotes_ZenQuoteId",
                        column: x => x.ZenQuoteId,
                        principalTable: "ZenQuotes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ZenMessages_IdLanguage",
                table: "ZenMessages",
                column: "IdLanguage");

            migrationBuilder.CreateIndex(
                name: "IX_ZenMessages_IdZenQuote",
                table: "ZenMessages",
                column: "IdZenQuote");

            migrationBuilder.CreateIndex(
                name: "IX_ZenQuoteTag_TagId",
                table: "ZenQuoteTag",
                column: "TagId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ZenMessages");

            migrationBuilder.DropTable(
                name: "ZenQuoteTag");

            migrationBuilder.DropTable(
                name: "Languages");

            migrationBuilder.DropTable(
                name: "Tags");

            migrationBuilder.DropTable(
                name: "ZenQuotes");
        }
    }
}
