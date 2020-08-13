package com.yukiju.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yukiju.repos.User;


@RestController
@RequestMapping("/login")
public class LoginController {
	
	@PostMapping
	public void login(User user) {
		
	}

}
