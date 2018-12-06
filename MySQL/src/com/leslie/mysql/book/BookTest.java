package com.leslie.mysql.book;

public class BookTest {
    public static void main(String[] args) {
        Library library = Library.resume();

//        Book book = new Book.Builder()
//                .id(10)
//                .author("unkown")
//                .content("123")
//                .price(100).build();
//
//        library.addBook(book);

        Book bookById = library.findBookById(1);

        System.out.println(bookById.toString());

        library.exit();
    }
}
