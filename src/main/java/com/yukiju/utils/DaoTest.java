package com.yukiju.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

import com.yukiju.daos.RoleDao;
import com.yukiju.daos.UserDao;
import com.yukiju.repos.Role;
import com.yukiju.repos.User;

public class DaoTest {
	/*
	 * Utility for testing Dao's methods note this is not unit testing but for
	 * testing methods run as a java app instead of spring app
	 */

	static Logger logger = Logger.getRootLogger();

	static void dummyRoles() {
		RoleDao rDao = DaoUtil.getRoleDao();

		if (rDao.getAllRoles().isEmpty()) {
			List<Role> roles = new ArrayList<Role>();
			roles.add(new Role(0, "admin"));
			roles.add(new Role(0, "manager"));
			roles.add(new Role(0, "user"));

			roles.forEach(e -> {
				rDao.addNewRole(e);
			});
		}
		// System.out.println(rDao.selectByRole("user"));
	}

	public static void roleQueryTest() {
		RoleDao rDao = DaoUtil.getRoleDao();
		List<Role> roles = rDao.getAllRoles();
		System.out.println("\nGetting all roles.");

		for (Role r : roles) {
			System.out.println(r);
		}

		// selectByRole will return null if not exists
		// Optional returns object or null
		Role user = new Role(0, "manager32");
		Optional<Role> role = Optional.ofNullable(rDao.selectByRole(user.getRole()));
		if (!role.isPresent()) {
			rDao.addNewRole(user);
			logger.info("Role " + user.getRole() + " was created.");
		} else {
			logger.warn("Role " + user.getRole() + " already exists.");
		}

		DaoUtil.closeSessionFactory();
	}

	public static void userQueryTest() {
		UserDao uDao = DaoUtil.getUserDao();
		RoleDao rDao = DaoUtil.getRoleDao();
		// Role role = new Role(0, "admin");
		// add logic from roleQueryTest() to line below
		Role admin = rDao.selectByRole("admin");
		User user = new User(0, "mreyes", "mreyes@email.com", "password", "Milton", "Reyes", null);
		if (user.getRole() == null) {
			user.setUsername("user");
			user.setEmail("user@email.com");
			user.setFirstName("new");
			user.setLastName("user");
		}
		User user4 = new User(0, "user2", "user2@email.com", "password", "user", "two", null);
		uDao.addNewUser(user4);

		Optional<User> username = Optional.ofNullable(uDao.selectUserByUsername(user.getUsername()));
		if (!username.isPresent()) {
			uDao.addNewUser(user);
		} else {
			logger.warn("Username " + user.getUsername() + " already exists.");
		}

		uDao.selectUserById(1).ifPresent((u) -> System.out.println(u));

		Optional<User> user2 = uDao.selectUserById(9);
		if (user2.isPresent()) {
			User user2Update = user2.get();
			user2Update.setFirstName("newuser234");
			uDao.updateUser(user2Update);
		}

		// System.out.println("\n\n\n\n\n\n\n\n");
		User user3;
		if (uDao.selectUserById(1).isPresent()) {
			user3 = uDao.selectUserById(1).get();
			System.out.println(user3);
			System.out.println(user3.getRole());
		}

		// System.out.println("\n\n\n\n\n\n\n\n");
		Set<User> users = uDao.getUsersByRoleId(1);
		// System.out.println(users);
		for (User u : users) {
			System.out.println(u);
		}

		DaoUtil.closeSessionFactory();
	}

	public static void main(String[] args) {
		dummyRoles();
		userQueryTest();

	}

}
