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

import com.yukiju.repos.Storage;
import com.yukiju.utils.DaoUtil;

public class StorageDaoImpl implements StorageDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;
	
	public StorageDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Storage> getAllStorages() {
		List<Storage> storages = new ArrayList<Storage>();
		try (Session session = sf.openSession()) {
			Query<Storage> results = session.createQuery("from Storage", Storage.class);
			if (results.list().isEmpty()) {
				logger.warn("Storages table is empty");
			}
			storages = (ArrayList<Storage>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to storages table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return storages;
	}

	@Override
	public void addNewStorage(Storage storage) throws PersistentObjectException {
		if (!storage.getStorage().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(storage);
				tx.commit();
				logger.info("Added new storage: " + storage.getStorage());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist storage " + storage);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Storage selectByStorage(String storage) throws NoResultException {
		Storage s = new Storage();
		if (!storage.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Storage s where lower(s.storage) = :storage";
				Query<Storage> result = session.createQuery(hql, Storage.class);
				result.setParameter("storage", storage.toLowerCase());
				s = result.getSingleResult();
			} catch (NoResultException e) {
				s = null;
				logger.warn("Storage " + storage + " does not exists.");
			}
		}
		return s;
	}

	@Override
	public Optional<Storage> getStorage(int id) {
		try (Session session = sf.openSession()) {
			Storage storage = session.get(Storage.class, id);
			if (storage == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(storage);
		}
	}

}
