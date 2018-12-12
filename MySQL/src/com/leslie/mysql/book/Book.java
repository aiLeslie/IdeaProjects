package com.leslie.mysql.book;

import java.sql.Date;
import java.util.Objects;

public class Book implements Nullable {
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

    @Override
    public boolean isNull() {
        return mBuilder.isNull();
    }

    public static class Builder implements Nullable {
        private int id;
        private String name;
        private String author;
        private String content;
        private Date pubDate;
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

        public Builder name(String name) {
            this.name = name;
            return this;
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

        public Date getPubDate() {
            return pubDate;
        }

        public Builder pubDate(Date pubDate) {
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
        public boolean isNull() {
            if (id == 0 || content == null || name == null) {
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            if (!isNull()) {
                return "Info{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", author='" + author + '\'' +
                        ", content='" + content + '\'' +
                        ", pubDate=" + pubDate +
                        ", price=" + price +
                        '}';
            } else {
                return "I am null object!";
            }


        }
    }
}
