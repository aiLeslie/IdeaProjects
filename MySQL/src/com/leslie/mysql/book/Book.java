package com.leslie.mysql.book;

import java.sql.Timestamp;
import java.util.Objects;

public class Book {
    private Builder mBuilder;

    public Book(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }
    public Builder info() {
        return mBuilder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;

        if (book.mBuilder.id == mBuilder.id) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(mBuilder);
    }

    @Override
    public String toString() {
        return mBuilder.toString();
    }

    public static class Builder {
        private int id;
        private String name;
        private String author;
        private String content;
        private Timestamp pubDate;
        private float price;


        public Book build() {
            return new Book(this);
        }

        public int getId() {
            return id;
        }

        public Builder id(int id) {
            this.id = id;
            return this;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public Builder author(String author) {
            this.author = author;
            return this;

        }

        public String getContent() {
            return content;
        }

        public Builder content(String content) {
            this.content = content;
            return this;

        }

        public Timestamp getPubDate() {
            return pubDate;
        }

        public Builder pubDate(Timestamp pubDate) {
            this.pubDate = pubDate;
            return this;

        }

        public float getPrice() {
            return price;
        }

        public Builder price(float price) {
            this.price = price;
            return this;

        }

        @Override
        public String toString() {
            return "Info{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", pubDate=" + pubDate +
                    ", price=" + price +
                    '}';
        }
    }
}
