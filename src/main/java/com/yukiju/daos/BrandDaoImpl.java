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

import com.yukiju.repos.Brand;
import com.yukiju.utils.DaoUtil;

public class BrandDaoImpl implements BrandDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;
	
	public BrandDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Brand> getAllBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		try (Session session = sf.openSession()) {
			Query<Brand> results = session.createQuery("from Brand", Brand.class);
			if (results.list().isEmpty()) {
				logger.warn("Brands table is empty");
			}
			brands = (ArrayList<Brand>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to brands table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return brands;
	}

	@Override
	public void addNewBrand(Brand brand) throws PersistentObjectException {
		if (!brand.getBrand().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(brand);
				tx.commit();
				logger.info("Added new brand: " + brand.getBrand());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist brand " + brand);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Brand selectByBrand(String brand) throws NoResultException {
		Brand b = new Brand();
		if (!brand.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Brand b where lower(b.brand) = :brand";
				Query<Brand> result = session.createQuery(hql, Brand.class);
				result.setParameter("brand", brand.toLowerCase());
				b = result.getSingleResult();
			} catch (NoResultException e) {
				b = null;
				logger.warn("Brand " + brand + " does not exists.");
			}
		}
		return b;
	}

	@Override
	public Optional<Brand> getBrand(int id) {
		try (Session session = sf.openSession()) {
			Brand brand = session.get(Brand.class, id);
			if (brand == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(brand);
		}
	}

}
