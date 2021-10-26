package com.huns.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huns.test.exceptions.InvalidParameterException;
import com.huns.test.model.User;
import com.huns.test.service.UserService;

@RequestMapping("user")
@RestController
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService service)
	{
		this.userService = service;
	}
		
	@GetMapping("all")
	public Object userInfos()
	{
		return this.userService.getUserList();
	}
	
	@GetMapping("all/{name}")
	/* localhost:9090/user/user/PARAMETER  */
	public Object usersByName(@PathVariable("name") String name)
	{
		return this.userService.getUserList(name);
	}
	
	@GetMapping("userByUid")
	/* localhost:9090/user/user?uid=PARAMETER  */
	public Object userByUid(@RequestParam(name = "uid", required = true, defaultValue = "") String uid)
	{
		return this.userService.getUserByUid(uid);
	}
	
	@PostMapping("user")
	public ResponseEntity userAdd(@RequestBody User user)
	{
		try
		{
			return new ResponseEntity<>(this.userService.insert(user), HttpStatus.OK);
		}
		catch(InvalidParameterException ipx)
		{
			return new ResponseEntity<>(ipx.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity userDelete(@PathVariable("id") int id)
	{
		try {
			return new ResponseEntity<>(this.userService.delete(id), HttpStatus.OK);
		}
		catch(InvalidParameterException ipx)
		{
			return new ResponseEntity<>(ipx.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("user")
	public ResponseEntity userUpdate(@RequestBody User user)
	{
		try
		{
			return new ResponseEntity<>(this.userService.update(user), HttpStatus.OK);
		}
		catch(InvalidParameterException ipx)
		{
			return new ResponseEntity<>(ipx.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("authorization/{uid}/{pwd}")
	public ResponseEntity userLogin(@PathVariable("uid") String uid, @PathVariable("pwd") String pwd)
	{
		try
		{
			return new ResponseEntity<>(this.userService.login(uid, pwd), HttpStatus.OK);
		}
		catch(InvalidParameterException ipx) {
			return new ResponseEntity<>(ipx.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
