package com.yukiju.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yukiju.repos.User;
import com.yukiju.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.POST},
allowedHeaders = {"Content-Type"})
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

}
