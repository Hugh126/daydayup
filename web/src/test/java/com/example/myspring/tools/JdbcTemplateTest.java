package com.example.myspring.tools;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2021/2/13 0013
 */
public class JdbcTemplateTest {

	@Test
	public void test0() throws ClassNotFoundException, SQLException {
		Connection  connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/benchtest", "root", "123456");

		String sql = "select * from user";

		ps = connection.prepareStatement(sql);

		rs = ps.executeQuery();
		//遍历结果集
		if(rs!=null)
		{
			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int sno = rs.getInt("sno");
				TEntry user = new TEntry();
				user.setId(id);
				user.setName(name);
				user.setSno(sno);
				System.out.println(user.toString());
			}

		}
		rs.close();
		ps.close();
		connection.close();
		ps.close();

		System.exit(0);

	}


	@Test
	public void testObject() throws Exception {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test0");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");

		String sql = "select * from t_entry";
		JdbcTemplate jt = new JdbcTemplate(dataSource);//jt对Jdbc进行封装
		List<TEntry> list = jt.query(sql, new Rowmapper());

		list.stream().forEach(item -> System.out.println(item.toString()));
	}

	@Test
	public void name() {
		Stream.of(new Object[] {'a', 'c', 'e'}).forEach(System.out::print);
	}
}
