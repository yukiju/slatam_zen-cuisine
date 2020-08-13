package com.yukiju.utils;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.yukiju.daos.AccountDao;
import com.yukiju.daos.AccountDaoImpl;
import com.yukiju.daos.RoleDao;
import com.yukiju.daos.RoleDaoImpl;
import com.yukiju.daos.UserDao;
import com.yukiju.daos.UserDaoImpl;
import com.yukiju.repos.Account;
import com.yukiju.repos.Role;
import com.yukiju.repos.User;



public class DaoUtil {

	static Logger logger = Logger.getRootLogger();

	private static ServiceRegistry registry = null;
	private static Configuration configuration = null;
	private static SessionFactory sf = null;

	private static UserDaoImpl userDaoImpl = null;
	private static RoleDaoImpl roleDaoImpl = null;
	private static AccountDaoImpl accountDaoImpl = null;
	
	
	public static UserDao getUserDao() {
		if (userDaoImpl == null) {
			userDaoImpl = new UserDaoImpl();
		}
		return userDaoImpl;
	}
	
	public static RoleDao getRoleDao() {
		if (roleDaoImpl == null) {
			roleDaoImpl = new RoleDaoImpl();
		}
		return roleDaoImpl;
	}
	
	public static AccountDao getAccountDao() {
		if (accountDaoImpl == null) {
			accountDaoImpl = new AccountDaoImpl();
		}
		return accountDaoImpl;
	}


	public static SessionFactory getSessionFactory() {
		if (sf == null || registry == null || configuration == null) {
			try {
				String jdbcUrl = String.format("jdbc:postgresql://%s:5432/postgres", System.getenv("YUKAJUCO_URL"));

				configuration = new Configuration().configure().setProperty("hibernate.connection.url", jdbcUrl)
						.setProperty("hibernate.connection.username", System.getenv("YUKAJUCO_U"))
						.setProperty("hibernate.connection.password", System.getenv("YUKAJUCO_P"))
						.addAnnotatedClass(User.class)
						.addAnnotatedClass(Role.class)
						.addAnnotatedClass(Account.class);

				registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

				sf = configuration.buildSessionFactory(registry);
				logger.info("New SessionFactory created.");
			} catch (HibernateException e) {
				logger.error("Error creating the session factory.");
				e.printStackTrace();
			}
		}
		return sf;
	}

	public static void closeSessionFactory() {
		if (sf != null) {
			sf.close();
			logger.info("Session Factory is now closed.");
		}
		if (sf == null) {
			logger.warn("Session Factory is null.");
		}
	}
}
