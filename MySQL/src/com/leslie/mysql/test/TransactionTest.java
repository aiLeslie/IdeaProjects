package com.leslie.mysql.test;

import com.leslie.mysql.base.connection.impl.MySQLConnector;

import java.sql.SQLException;

public class TransactionTest {
	public static void main(String[] args) throws SQLException {
		System.out.println("连接数据库");

		MySQLConnector connector = new MySQLConnector.Builder().host("localhost").port("3306").user("root").pwd("980517").build();
		//  默认自动提交 jdbc
		connector.connection().setAutoCommit(false);
		
		// 批量操作 插入多行数据
		
		for (int i = 0; i < 1000; i++) {
			connector.statement().addBatch("INSERT INTO `mybase`.`info` (`name`, `age`, `desc`) VALUES ('Leslie', '28', 'hello');");
		}
		// 错误代码 (触发数据库回滚) connector.connection().rollback();
//		connector.statement().addBatch("INSERT INTO `mybase`.`info` (`name`, `age`, `desc`) VALUES ( '28', 'hello');");
		
		connector.statement().executeBatch();
		
		connector.connection().commit();
		
		connector.disconnect();
	}
}
