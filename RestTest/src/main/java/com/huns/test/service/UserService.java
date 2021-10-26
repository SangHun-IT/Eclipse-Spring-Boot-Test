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
		user.name = "������";
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
			throw new InvalidParameterException("�̸��� �Է��ϼ���.");
		
		if(user.uid.equals("") || user.uid == null)
			throw new InvalidParameterException("���̵� �Է��ϼ���.");
		
		if(user.pwd.equals("") || user.pwd == null)
			throw new InvalidParameterException("�н����带 �Է��ϼ���.");
		
		if(user.email.equals("") || user.email == null)
			throw new InvalidParameterException("�̸����� �Է��ϼ���.");
		
		if(this.userRepository.findUserByUid(user.uid) != null)
			throw new InvalidParameterException("�̹� ������� ���̵��Դϴ�.");
		
		return this.userRepository.insert(user);
	}
	
	public int delete(int id) throws InvalidParameterException {
		
		if(this.userRepository.findUserById(id) == null)
			throw new InvalidParameterException("�ش��ϴ� ����ڰ� �����ϴ�.");
		
		return this.userRepository.delete(id);
	}
	
	public int update(User user) throws InvalidParameterException {
		
		if(user.name.equals("") || user.name == null)
			throw new InvalidParameterException("�̸��� �Է��ϼ���.");
			
		if(user.pwd.equals("") || user.pwd == null)
			throw new InvalidParameterException("�н����带 �Է��ϼ���.");
		
		if(user.email.equals("") || user.email == null)
			throw new InvalidParameterException("�̸����� �Է��ϼ���.");
		
		return this.userRepository.update(user);
	}
	
	public User login(String uid, String pwd) throws InvalidParameterException {
		
		User user = this.getUserByUid(uid);
		
		if(user == null)
		{
			throw new InvalidParameterException("���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
		}
		
		if(!user.pwd.equals(pwd)) {
			throw new InvalidParameterException("���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
		}
		
		return user;
	}
}
