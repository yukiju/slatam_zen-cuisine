package com.yukiju.daos;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.User;

public interface UserDao {
	List<User> getAllUsers();

	User addNewUser(User user) throws PersistentObjectException;

	User selectUserByUsername(String username) throws NoResultException;

	User selectUserByEmail(String email) throws NoResultException;

	Optional<User> selectUserById(int id);

	void updateUser(User user);

	void deleteUser(User user);

	Set<User> getUsersByRoleId(int roleId);

}
