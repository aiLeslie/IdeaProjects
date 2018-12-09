package com.leslie.mysql.book;

public interface IBookManage {
    /**
     * ����id����ͼ��
     * @param id
     * @return id������ʵ��
     */
    Book findBookById(int id);

    /**
     * ����idɾ��ͼ��
     * @param id
     * @return ��ɾ����ͼ��
     */
    Book deleteBookById(int id);

    /**
     * ����鼮
     * @param book
     */
    boolean addBook(Book book);

    /**
     * g�鼮
     * @param book
     */
    boolean upadateBook(Book book);



}
