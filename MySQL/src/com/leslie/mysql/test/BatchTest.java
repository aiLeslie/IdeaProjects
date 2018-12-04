package com.leslie.mysql.test;


import com.leslie.mysql.base.connection.impl.MySQLConnector;

import java.sql.Timestamp;
import java.util.Random;

public class BatchTest {
	public static void main(String[] args) throws Exception {

        MySQLConnector connector = new MySQLConnector.Builder().host("localhost").port("3306").user("root").pwd("980517").build();

		connector.connection().setAutoCommit(false);



		// 批量操作 插入多行数据
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO `mybase`.`info` (`name`, `age`, `desc`,`regTime` ) VALUES ('Leslie', '28', 'hello', ?);";
//		PreparedStatement preparedStatement = connector.connection().prepareStatement(sql);
        Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			timestamp.setTime(System.currentTimeMillis());
//			preparedStatement.setObject(1, timestamp);
//			preparedStatement.addBatch();


			Thread.sleep(10);
		}

//		preparedStatement.executeBatch();

		connector.connection().commit();

		connector.disconnect();
	}
}
