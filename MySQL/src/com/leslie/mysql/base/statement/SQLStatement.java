package com.leslie.mysql.base.statement;

import java.sql.Statement;


public interface SQLStatement {
    Statement statement();

    boolean excutePreparedStatement(String sql, Object... obj);

    void closeStatement();



    void query(String sql,QueryConsumer action, String... field);

    interface QueryConsumer {
        void accept(int index, String field, Object obj);
    }

}
