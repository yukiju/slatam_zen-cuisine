package com.yukiju.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.yukiju.repos.Role;
import com.yukiju.utils.DaoUtil;

public class RoleDaoImpl implements RoleDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;

	public RoleDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<Role>();
		try (Session session = sf.openSession()) {
			Query<Role> results = session.createQuery("from Role", Role.class);
			if (results.list().isEmpty()) {
				logger.warn("Roles table is empty");
			}
			roles = (ArrayList<Role>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to roles table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public void addNewRole(Role role) throws PersistentObjectException {
		if (!role.getRole().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				// persist is the JPA standard vs hibernate save
				// persist requires the object to have id 0
				// PersistentObjectException
				session.persist(role);
				tx.commit();
				logger.info("Added new role: " + role.getRole());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist role " + role);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Role selectByRole(String role) throws NoResultException {
		Role r = new Role();
		if (!role.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Role r where lower(r.role) = :role";
				Query<Role> result = session.createQuery(hql, Role.class);
				result.setParameter("role", role.toLowerCase());
				r = result.getSingleResult();
			} catch (NoResultException e) {
				r = null;
				logger.warn("Role " + role + " does not exists.");
			}
		}
		return r;
	}

	@Override
	public Optional<Role> getRole(int id) {
		try (Session session = sf.openSession()) {
			Role role = session.get(Role.class, id);
			if (role == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(role);
		}
	}

}
