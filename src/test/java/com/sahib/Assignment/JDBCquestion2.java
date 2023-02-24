package com.sahib.Assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCquestion2 {

	public static void main(String[] args) throws SQLException {
		Connection connection;
		String url = "jdbc:mysql://localhost:3306/test";
		connection = DriverManager.getConnection(url, "root", "root");
		ResultSet resultSet;
		System.out.println("Connection Established");

		Statement statement = connection.createStatement();
		String query = "SELECT first_name, salary FROM employee WHERE salary < (SELECT MAX(salary) FROM employee) ORDER BY salary DESC LIMIT 1;";
		resultSet = statement.executeQuery(query);
		ResultSetMetaData resultMetaData = resultSet.getMetaData();
		int colCount = resultMetaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= colCount; i++) {
				System.out.println(resultSet.getString(i) + "");
			}
		}
		connection.close();

	}

}
