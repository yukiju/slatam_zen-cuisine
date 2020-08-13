package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Role;

public interface RoleDao {
	List<Role> getAllRoles();

	void addNewRole(Role role) throws PersistentObjectException;

	Role selectByRole(String role) throws NoResultException;

	Optional<Role> getRole(int id);

}
