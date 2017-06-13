using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using ZenSource.Models;

namespace ZenSource.Migrations
{
    [DbContext(typeof(ZenContext))]
    partial class ZenContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.1.2")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("ZenSource.Models.Language", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Code")
                        .IsRequired()
                        .HasMaxLength(5);

                    b.Property<string>("Description")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("Id");

                    b.ToTable("Languages");
                });

            modelBuilder.Entity("ZenSource.Models.ZenMessage", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("LanguageId");

                    b.Property<string>("Message")
                        .IsRequired()
                        .HasMaxLength(500);

                    b.Property<int?>("ZenQuoteId");

                    b.HasKey("Id");

                    b.HasIndex("LanguageId");

                    b.HasIndex("ZenQuoteId");

                    b.ToTable("ZenMessages");
                });

            modelBuilder.Entity("ZenSource.Models.ZenQuote", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Author")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("CreatedOn");

                    b.Property<string>("User")
                        .IsRequired();

                    b.Property<bool>("Valid");

                    b.HasKey("Id");

                    b.ToTable("ZenQuotes");
                });

            modelBuilder.Entity("ZenSource.Models.ZenMessage", b =>
                {
                    b.HasOne("ZenSource.Models.Language", "Language")
                        .WithMany()
                        .HasForeignKey("LanguageId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("ZenSource.Models.ZenQuote")
                        .WithMany("Messages")
                        .HasForeignKey("ZenQuoteId");
                });
        }
    }
}
