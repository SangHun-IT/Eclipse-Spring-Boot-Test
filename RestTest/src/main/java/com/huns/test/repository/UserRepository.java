package com.huns.test.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.huns.test.model.User;

@Repository
public class UserRepository {
	
		
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final UserRowMapper userRowMapper;
		
	public UserRepository(NamedParameterJdbcTemplate template)
	{
		this.namedParameterJdbcTemplate = template;
		this.userRowMapper = new UserRowMapper();
	}
	
	public User findUserById(int id) {
		
		String sql = "select * From user where id = :id";
		
		return this.namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), this.userRowMapper);
	}
	
	public List<User> findUser() {
	
		String sql = "select * from user";		
		
		return this.namedParameterJdbcTemplate.query(sql, EmptySqlParameterSource.INSTANCE, this.userRowMapper);
	}
	
	public List<User> findUserByName(String name){
		String sql = "select * from user where name like Concat('%', :name, '%')";
		
		return this.namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("name", name), this.userRowMapper);
	}
	
	public User findUserByUid(String uid) {
		String sql = "select * from user where uid = :uid";
		
		return this.namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("uid", uid), this.userRowMapper);
	}
	
	public User insert(User user)
	{
		String sql = "Insert into user (name, uid, pwd, email) values (:name, :uid, :pwd, :email)";
		
		SqlParameterSource parameterSource = new MapSqlParameterSource("name", user.name)
																						.addValue("uid", user.uid)
																						.addValue("pwd", user.pwd)
																						.addValue("email", user.email);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);
		
		user.id = keyHolder.getKey().intValue();
		
		return user;
	}
	
	public int delete(int id) {
		String sql = "Delete From user where id=:id";
		
		SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
		
		int affectRows = this.namedParameterJdbcTemplate.update(sql, parameterSource);
		
		return affectRows;
	}
	
	public int update(User user)
	{
		String sql = "update user set name=:name, pwd=:pwd, email=:email where id=:id";
		
		SqlParameterSource parameterSource = new MapSqlParameterSource("name", user.name)
																						.addValue("pwd", user.pwd)
																						.addValue("email", user.email)
																						.addValue("id", user.id);
		
		
		int affectRows = this.namedParameterJdbcTemplate.update(sql, parameterSource);
		
		return affectRows;
	} 
}
