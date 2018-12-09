package com.leslie.mysql.book;

import java.sql.Date;

public class BookTest {
    public static void main(String[] args) {
        Library library = Library.resume();

//        Book book = new Book.Builder()
//                .name("guolin")
//                .pubDate(new Date(System.currentTimeMillis()))
//                .id(10)
//                .author("unkown")
//                .content("123")
//                .price(100).build();
//
//        library.addBook(book);

        Book book = library.findBookById(1);

        System.out.println(book.toString());

        book.info().content("55");

        library.upadateBook(book);

         book = library.findBookById(1);

        System.out.println(book.toString());

        library.exit();
    }
}
