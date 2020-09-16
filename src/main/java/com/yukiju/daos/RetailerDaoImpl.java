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

import com.yukiju.repos.Retailer;
import com.yukiju.utils.DaoUtil;

public class RetailerDaoImpl implements RetailerDao {

	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;

	public RetailerDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Retailer> getAllRetailers() {
		List<Retailer> retailers = new ArrayList<Retailer>();
		try (Session session = sf.openSession()) {
			Query<Retailer> results = session.createQuery("from Retailer", Retailer.class);
			if (results.list().isEmpty()) {
				logger.warn("Retailers table is empty");
			}
			retailers = (ArrayList<Retailer>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to retailers table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return retailers;
	}

	@Override
	public void addNewRetailer(Retailer retailer) throws PersistentObjectException {
		if (!retailer.getRetailer().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(retailer);
				tx.commit();
				logger.info("Added new retailer: " + retailer.getRetailer());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist retailer " + retailer);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Retailer selectByRetailer(String retailer) throws NoResultException {
		Retailer ret = new Retailer();
		if (!retailer.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Retailer ret where lower(ret.retailer) = :retailer";
				Query<Retailer> result = session.createQuery(hql, Retailer.class);
				result.setParameter("retailer", retailer.toLowerCase());
				ret = result.getSingleResult();
			} catch (NoResultException e) {
				ret = null;
				logger.warn("Retailer " + retailer + " does not exists.");
			}
		}
		return ret;
	}

	@Override
	public Optional<Retailer> getRetailer(int id) {
		try (Session session = sf.openSession()) {
			Retailer retailer = session.get(Retailer.class, id);
			if (retailer == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(retailer);
		}
	}

}
