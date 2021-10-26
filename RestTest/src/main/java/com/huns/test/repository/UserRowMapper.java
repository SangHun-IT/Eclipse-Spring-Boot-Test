package com.huns.test.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.huns.test.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		User user = new User();
		
		user.id = rs.getInt("id");
		user.name = rs.getString("name");
		user.uid = rs.getString("uid");
		user.pwd = rs.getString("pwd");
		user.email = rs.getString("email");
		
		return user;
	}

	
}
