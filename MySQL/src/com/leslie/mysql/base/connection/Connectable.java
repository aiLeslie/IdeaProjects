package com.leslie.mysql.base.connection;

import java.sql.SQLException;

public interface Connectable {

    void connect() throws SQLException;

    void disconnect();
}
