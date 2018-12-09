package com.leslie.mysql.base.connection.impl;

import com.leslie.mysql.base.connection.SQLConnectable;
import com.leslie.mysql.base.statement.SQLStatement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

public class MySQLConnector implements SQLConnectable, SQLStatement {
    private final Builder mBuilder;
    private Connection mConnection;
    private Statement mStatement;
    private HashMap<String, PreparedStatement> preparedStatemens = new HashMap<>();

    public static MySQLConnector createMySQLConnectorFromProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            return new MySQLConnector.Builder().host(properties.getProperty("host")).port(properties.getProperty("port")).user(properties.getProperty("user")).pwd(properties.getProperty("pwd")).build();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            properties.clear();
        }
        return null;

    }

    public MySQLConnector(Builder builder) {
        mBuilder = builder;

        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Statement statement() {
        return mStatement;
    }

    @Override
    public Connection connection() {
        return mConnection;
    }

    @Override
    public void connect() throws SQLException {
        System.out.println(">>> 连接数据库");
        mConnection = prepareDriver(mBuilder);
        mStatement = mConnection.createStatement();

        System.out.println("host: " + mBuilder.host);
        System.out.println("port: " + mBuilder.port);
        System.out.println("<<< 成功获取数据库连接");
    }

    @Override
    public void disconnect() {
        closeStatement();

        try {
            if (mStatement != null && !mStatement.isClosed()) {
                mStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (mConnection != null && !mConnection.isClosed()) {
                mConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(">>> 断开数据库连接");
    }


    private Connection prepareDriver(Builder builder) {
        Connection connection = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取数据库的连接对象 (参数 主机路由地址 端口号 用户名 密码)
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s?serverTimezone=UTC", builder.host, builder.port), builder.user, builder.pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public boolean excutePreparedStatement(String sql, Object... objs) {
        PreparedStatement preparedStatement = null;
        try {

            if (preparedStatemens.containsKey(sql)) {
                preparedStatement = preparedStatemens.get(sql);

            } else {
                preparedStatement = mConnection.prepareStatement(sql);
                preparedStatemens.put(sql, preparedStatement);
            }


            int length = objs.length;

            for (int i = 0; i < length; i++) {
                preparedStatement.setObject(i + 1, objs[i]);
            }

            if (preparedStatement.isClosed()) {
                return false;
            } else {
                preparedStatement.execute();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void closeStatement() {
        preparedStatemens.entrySet().forEach(new Consumer<Map.Entry<String, PreparedStatement>>() {
            @Override
            public void accept(Map.Entry<String, PreparedStatement> entry) {
                try {
                    entry.getValue().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        preparedStatemens.clear();
        preparedStatemens = null;
    }

    @Override
    public void query(String sql, QueryConsumer action, String... field) {
        ResultSet resultSet = null;
        try {
            if (mStatement.isClosed()) return;

            resultSet = mStatement.executeQuery(sql);

            int index = 0;
            while (!resultSet.isClosed() && resultSet.next()) {
                index++;
                for (String f : field) {
                    if (!resultSet.isClosed()) {
                        action.accept(index, f, resultSet.getObject(f));
                    } else {
                        return;
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Builder {
        private String host;
        private String port;
        private String user;
        private String pwd;


        public String getHost() {
            return host;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public String getPort() {
            return port;
        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public String getUser() {
            return user;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public String getPwd() {
            return pwd;
        }

        public Builder pwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public MySQLConnector build() {
            return new MySQLConnector(this);
        }


    }
}
