package com.leslie.mysql.base.connection;


import com.leslie.mysql.base.connection.Connectable;

import java.sql.Connection;

public interface SQLConnectable extends Connectable {
    Connection connection();
}
