package com.leslie.mysql.book;

public interface IBookManage {
    /**
     * 根据id查找图书
     * @param id
     * @return id相符书的实例
     */
    Book findBookById(int id);

    /**
     * 根据id删除图书
     * @param id
     * @return 被删除的图书
     */
    Book deleteBookById(int id);

    /**
     * 添加书籍
     * @param book
     */
    boolean addBook(Book book);

    /**
     * g书籍
     * @param book
     */
    boolean upadateBook(Book book);



}
