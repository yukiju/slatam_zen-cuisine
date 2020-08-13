package com.yukiju.services;

import org.springframework.stereotype.Service;

import com.yukiju.daos.UserDao;
import com.yukiju.exceptions.HttpStatusException;
import com.yukiju.repos.User;
import com.yukiju.utils.DaoUtil;

@Service
public class RegisterService {

	private UserDao uDao = null;

	public RegisterService() {
		super();
		this.uDao = DaoUtil.getUserDao();
	}

	public RegisterService(UserDao uDao) {
		super();
		this.uDao = uDao;
	}

	public User addNewUser(User user) {
		// Email validation
		if (user.getEmail() == null) {
			throw new HttpStatusException(422);
		}

		if (user.getEmail().length() < 4 || user.getEmail().length() > 255) {
			throw new HttpStatusException(422);
		}

		// Validate email format (RegExp)

		if (user.getUsername() == null) {
			throw new HttpStatusException(422);
		}

		if (user.getUsername().length() < 4 || user.getUsername().length() > 30) {
			throw new HttpStatusException(422);
		}

		return uDao.addNewUser(user);
	}

}
