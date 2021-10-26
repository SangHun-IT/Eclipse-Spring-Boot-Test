package com.huns.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huns.test.exceptions.InvalidParameterException;
import com.huns.test.model.User;
import com.huns.test.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository repository)
	{
		this.userRepository = repository;
	}
	
	
	public User getUser()
	{
		User user = new User();
		
		user.id = 0;
		user.uid = "admin";
		user.pwd = "1234";
		user.name = "관리자";
		user.email = "admin@system.com";
		
		return user;
	}
	
	public List<User> getUserList()
	{
		return this.userRepository.findUser();
	}
	
	public List<User> getUserList(String name)
	{
		return this.userRepository.findUserByName(name);
	}
	
	public User getUserByUid(String uid) {
		return this.userRepository.findUserByUid(uid);
	}
	
	public User insert(User user) throws InvalidParameterException
	{
		if(user.name.equals("") || user.name == null)
			throw new InvalidParameterException("이름을 입력하세요.");
		
		if(user.uid.equals("") || user.uid == null)
			throw new InvalidParameterException("아이디를 입력하세요.");
		
		if(user.pwd.equals("") || user.pwd == null)
			throw new InvalidParameterException("패스워드를 입력하세요.");
		
		if(user.email.equals("") || user.email == null)
			throw new InvalidParameterException("이메일을 입력하세요.");
		
		if(this.userRepository.findUserByUid(user.uid) != null)
			throw new InvalidParameterException("이미 사용중인 아이디입니다.");
		
		return this.userRepository.insert(user);
	}
	
	public int delete(int id) throws InvalidParameterException {
		
		if(this.userRepository.findUserById(id) == null)
			throw new InvalidParameterException("해당하는 사용자가 없습니다.");
		
		return this.userRepository.delete(id);
	}
	
	public int update(User user) throws InvalidParameterException {
		
		if(user.name.equals("") || user.name == null)
			throw new InvalidParameterException("이름을 입력하세요.");
			
		if(user.pwd.equals("") || user.pwd == null)
			throw new InvalidParameterException("패스워드를 입력하세요.");
		
		if(user.email.equals("") || user.email == null)
			throw new InvalidParameterException("이메일을 입력하세요.");
		
		return this.userRepository.update(user);
	}
	
	public User login(String uid, String pwd) throws InvalidParameterException {
		
		User user = this.getUserByUid(uid);
		
		if(user == null)
		{
			throw new InvalidParameterException("아이디나 비밀번호가 틀렸습니다.");
		}
		
		if(!user.pwd.equals(pwd)) {
			throw new InvalidParameterException("아이디나 비밀번호가 틀렸습니다.");
		}
		
		return user;
	}
}
