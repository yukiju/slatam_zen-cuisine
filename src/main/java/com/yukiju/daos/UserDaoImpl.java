package com.yukiju.daos;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.yukiju.repos.Account;
import com.yukiju.repos.Role;
import com.yukiju.repos.User;
import com.yukiju.utils.DaoUtil;



public class UserDaoImpl implements UserDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;

	public UserDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try (Session session = sf.openSession()) {
			String hql = "from User";
			Query<User> result = session.createQuery(hql, User.class);
			if (result.list().isEmpty()) {
				logger.warn("Users table is empty");
			}
			users = (ArrayList<User>) result.list();
		} catch (Exception e) {
			logger.error("Couldn't connect to users table");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User addNewUser(User user) throws PersistentObjectException {
		// TODO: Optional provide logic for hashing password
		// Client side should handle hashing, if included hashing in server
		// side is only for double hashing extra security
		if (!user.getUsername().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
			// Password hashing
			PasswordEncoder scrypt = new SCryptPasswordEncoder();
			CharSequence plainTextPass = (CharSequence) user.getPassword();
			String hashedPass = scrypt.encode(plainTextPass);
			// logger.info("HASHED PASSWORD");
			// logger.info(hashedPass);
			user.setPassword(hashedPass);
			plainTextPass = null;
			/*
			 * Moved this outside of the below persist user session because was throwing
			 * error on hibernate.connection.pool_size greater than 1. Also changed the
			 * pool_size in hibernate.cfg to 100 to prevent this
			 * 
			 * When adding a new user if a Role wasn't provided then assign a basic role of
			 * user
			 */
			if (user.getRole() == null) {
				RoleDao rDao = DaoUtil.getRoleDao();
				user.setRole(rDao.selectByRole("user"));
			}
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(user);
				tx.commit();
				logger.info("Added new user: " + user.getUsername());
				/*
				 * Now that the user is persisted we can start a new transaction and create a
				 * new account for the user.
				 */
				logger.info("Is transaction active: " + tx.isActive());
				tx.begin();
				logger.info("Is transaction active: " + tx.isActive());
				User u = selectUserByUsername(user.getUsername());
				logger.info("Retrieved persisted user: " + u);
				tx.commit();
				AccountDao aDao = DaoUtil.getAccountDao();
				Account newAccount = new Account(0, LocalDateTime.now(), u);
				//Account newAccount = new Account(0, new Date(System.currentTimeMillis()), 0, 0.0D, 0, 0, 0.0D, u, null);
				logger.info(newAccount);
				aDao.addNewAccount(newAccount);
				logger.info("Persisted new account for user: "
						+ u);/*
								 * logger.info("Is transaction active: "+tx.isActive()); Optional<Account> a =
								 * aDao.getAccountByUserId(u.getId()); if (a.isPresent()) {
								 * u.setAccount(a.get()); logger.info("New user with account: "+u);
								 * session.merge(u); }
								 */
				user = u;
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist user " + user);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
		return user;

	}

	@Override
	public User selectUserByUsername(String username) throws NoResultException {
		User u = new User();
		if (!username.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from User u where u.username = :username";
				Query<User> result = session.createQuery(hql, User.class);
				result.setParameter("username", username);
				u = result.getSingleResult();
			} catch (NoResultException e) {
				u = null;
				logger.warn("User " + username + " does not exist.");
			}
		}

		return u;
	}

	@Override
	public User selectUserByEmail(String email) throws NoResultException {
		User u = new User();
		if (!email.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from User u where u.email = :email";
				Query<User> result = session.createQuery(hql, User.class);
				result.setParameter("email", email);
				u = result.getSingleResult();
			} catch (NoResultException e) {
				u = null;
				logger.warn("User " + email + " does not exist.");
			}
		}
		return u;
	}

	@Override
	public Optional<User> selectUserById(int id) {
		try (Session session = sf.openSession()) {
			return Optional.ofNullable(session.get(User.class, id));
		}
	}

	@Override
	public void updateUser(User user) {
		Optional<User> sameUser = selectUserById(user.getId());
		if (sameUser.isPresent()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				user = (User) session.merge(user);
				tx.commit();
				logger.info("Updated user: "+user.getUsername());
			}
		} else {
			logger.warn(user + " not found.");
		}
	}

	@Override
	public void deleteUser(User user) {
		Optional<User> sameUser = selectUserById(user.getId());
		if (sameUser.isPresent()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.delete(user);
				tx.commit();
				logger.warn(user+" deleted");
				user = null;
			}
		} else {
			logger.warn(user + " not found.");
		}
	}

	@Override
	public Set<User> getUsersByRoleId(int roleId) {
		Set<User> users = new HashSet<User>();
		try (Session session = sf.openSession()) {
			Role role = session.find(Role.class, roleId);
			if (role == null)
				return null;
			users = role.getUsers();
			Hibernate.initialize(users);
		} catch (NoResultException e) {
			users = null;
			logger.warn("User does not exist.");
		}
		return users;
	}

}
