package com.leslie.mysql.test;

import com.leslie.mysql.base.connection.impl.AsyncSQLConnector;
import com.leslie.mysql.base.connection.impl.MySQLConnector;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryTest {
	public static void main(MysqlxDatatypes.Scalar.String[] args) throws SQLException {

//		MySQLConnector mySQLConnector = new MySQLConnector.Builder().host("localhost").port("3306").user("root").pwd("980517").build();

		MySQLConnector mySQLConnector = MySQLConnector.createMySQLConnectorFromProperties("./asset/mysql.properties");

		AsyncSQLConnector connector = new AsyncSQLConnector(mySQLConnector);

		ResultSet results = connector.statement().executeQuery("select * from mybase.info;");

		System.out.println(results.toString());

//		ArrayList<Object> objects = new ArrayList<>();objects.stream().filter(new ).forEach();


		connector.query("select * from mybase.info;", (
				index,  field,  obj) -> {
					System.out.println(index + ":\t" + field + " = " + obj );
				}

		, "id", "name", "age", "regTime");
		
//		connector.disconnect();
	}
}
