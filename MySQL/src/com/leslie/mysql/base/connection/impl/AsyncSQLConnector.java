package com.leslie.mysql.base.connection.impl;

import com.leslie.mysql.base.connection.SQLConnectable;
import com.leslie.mysql.base.connection.impl.MySQLConnector;
import com.leslie.mysql.base.statement.SQLStatement;

import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.*;

public class AsyncSQLConnector implements SQLConnectable, SQLStatement {
    private MySQLConnector mySQLConnector;
    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private ExecutorService service = Executors.newFixedThreadPool(10);
    private boolean isConnection = false;

    public AsyncSQLConnector(MySQLConnector connector) {

        mySQLConnector = connector;

        connect();
    }

    @Override
    public Connection connection() {

        return mySQLConnector.connection();
    }

    @Override
    public void connect() {
        isConnection = true;
        service.execute(coreRunnable);
    }

    @Override
    public void disconnect() {
        equeue(() -> {
            isConnection = false;
            mySQLConnector.disconnect();
        });

    }

    @Override
    public Statement statement() {
        return mySQLConnector.statement();
    }

    private void equeue(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean excutePreparedStatement(String sql, Object... obj) {
        equeue(() -> mySQLConnector.excutePreparedStatement(sql, obj));
        return true;
    }

    @Override
    public void closeStatement() {
        mySQLConnector.closeStatement();
    }

    @Override
    public void query(String sql, QueryConsumer action, String... field) {
        equeue(() -> mySQLConnector.query(sql, action, field));
    }

    private Runnable coreRunnable = () -> {
        try {
            while (isConnection) {

                Runnable task = tasks.take();
                service.execute(task);

            }

            if (!service.isShutdown()) service.shutdown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
}
