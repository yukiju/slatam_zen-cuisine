package com.yukiju.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yukiju.daos.UserDao;
import com.yukiju.repos.User;
import com.yukiju.utils.DaoUtil;

@Service
public class UserService {

	UserDao userDao = DaoUtil.getUserDao();

	public List<User> getAllUsers() {
		List<User> users = userDao.getAllUsers();
		return users;
	}

}
