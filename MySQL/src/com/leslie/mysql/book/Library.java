package com.leslie.mysql.book;


import com.leslie.mysql.base.connection.impl.MySQLConnector;
import com.leslie.mysql.base.statement.SQLStatement;

import java.lang.reflect.Field;


public class Library {
    private static Library mLibrary;
    private MySQLConnector mySQLConnector;
    private final Class BOOK_TYPE = Book.Builder.class;

    public static Library resume() {
        if (mLibrary == null) {
            mLibrary = new Library();
            mLibrary.mySQLConnector = new MySQLConnector.Builder().host("localhost").port("3306").user("root").pwd("980517").build();
        }

        return mLibrary;
    }

    public Book findBookById(int id) {
        final Book.Builder builder = new Book.Builder();
        mySQLConnector.query(String.format("SELECT *FROM `mybase`.`book`WHERE book.id = %d;", id), new SQLStatement.QueryConsumer() {
            @Override
            public void accept(int index, String field, Object obj) {
                setBookInfo(builder, field, obj);
            }
        }, "id", "name", "author", "price", "content","pubDate" );

        return builder.build();
    }

    private void setBookInfo(Book.Builder builder, String field, Object obj) {
        try {
            Field attr = BOOK_TYPE.getDeclaredField(field);
            attr.setAccessible(true);
            attr.set(builder, obj);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void addBook(Book book) {
        mySQLConnector.excutePreparedStatement("INSERT INTO `mybase`.`book`(`id`,`author`,`content`,`pubDate`,`price`,`name`)VALUES({id ?},{author ?},{content ?},{pubDate ?},{price ?},{name ?);",
                book.info().getId(),
                book.info().getAuthor(),
                book.info().getContent(),
                book.info().getPubDate(),
                book.info().getPrice(),
                book.info().getName());
    }


    public void exit() {
        mySQLConnector.disconnect();
        mLibrary = null;
    }


}
