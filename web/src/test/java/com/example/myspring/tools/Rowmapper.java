package com.example.myspring.tools;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class Rowmapper implements RowMapper<TEntry> {

	@Override
	public TEntry mapRow(ResultSet rs, int arg1) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int sno = rs.getInt("sno");
		TEntry user = new TEntry();
		user.setId(id);
		user.setName(name);
		user.setSno(sno);
		return user;
	}

}
