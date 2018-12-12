package com.leslie.mysql.book;


import com.leslie.mysql.base.connection.impl.MySQLConnector;
import com.leslie.mysql.base.statement.SQLStatement;

import java.lang.reflect.Field;
import java.sql.SQLException;


public class Library implements IBookManage {
    private static Library mLibrary;
    private MySQLConnector mySQLConnector;
    private final Class BOOK_TYPE = Book.Builder.class;

    private static void init() {
        mLibrary = new Library();
        mLibrary.mySQLConnector = new MySQLConnector.Builder().host("localhost").port("3306").user("root").pwd("980517").build();
        try {
            // 选择合适的数据库
            mLibrary.mySQLConnector.statement().execute("use mybase;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 与图书馆系统进行交互操作
     *
     * @return 图书馆的实例
     */
    public static Library resume() {
        if (mLibrary == null) {
            init();
        }

        return mLibrary;
    }

    @Override
    public Book findBookById(int id) {
        final Book.Builder builder = new Book.Builder();
        mySQLConnector.query(String.format("SELECT *FROM `book`WHERE book.id = %d;", id), new SQLStatement.QueryConsumer() {
            @Override
            public void accept(int index, String field, Object obj) {
                setBookInfo(builder, field, obj);
            }
        }, "id", "name", "author", "price", "content", "pubDate");

        if (builder.isNull()) {
            return null;
        } else {
            return builder.build();
        }


    }

    @Override
    public Book deleteBookById(int id) {
        Book delBook = findBookById(id);

        mySQLConnector.excutePreparedStatement("DELETE FROM `book` WHERE id = ?;", id);

        return delBook;

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

    @Override
    public boolean addBook(Book book) {
        if (book.isNull()) {
            return false;
        }

        return mySQLConnector.excutePreparedStatement("INSERT INTO `book`(`id`,`author`,`content`,`pubDate`,`price`,`name`)VALUES({id ?},{author ?},{content ?},{pubDate ?},{price ?},{name ?});",
                book.info().getId(),
                book.info().getAuthor(),
                book.info().getContent(),
                book.info().getPubDate(),
                book.info().getPrice(),
                book.info().getName());
    }

    @Override
    public boolean upadateBook(Book book) {
        Book old = findBookById(book.info().getId());

        deleteBookById(old.info().getId());

        addBook(book);

        return true;
    }


    public void exit() {
        mySQLConnector.disconnect();
        mLibrary = null;
    }


}
