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

import com.yukiju.repos.FoodType;
import com.yukiju.utils.DaoUtil;

public class FoodTypeDaoImpl implements FoodTypeDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;
	
	public FoodTypeDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<FoodType> getAllFoodTypes() {
		List<FoodType> foodTypes = new ArrayList<FoodType>();
		try (Session session = sf.openSession()) {
			Query<FoodType> results = session.createQuery("from FoodType", FoodType.class);
			if (results.list().isEmpty()) {
				logger.warn("FoodTypes table is empty");
			}
			foodTypes = (ArrayList<FoodType>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to foodTypes table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return foodTypes;
	}

	@Override
	public void addNewFoodType(FoodType foodType) throws PersistentObjectException {
		if (!foodType.getFoodType().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(foodType);
				tx.commit();
				logger.info("Added new foodType: " + foodType.getFoodType());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist foodType " + foodType);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public FoodType selectByFoodType(String foodType) throws NoResultException {
		FoodType ft = new FoodType();
		if (!foodType.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from FoodType ft where lower(ft.foodType) = :foodType";
				Query<FoodType> result = session.createQuery(hql, FoodType.class);
				result.setParameter("foodType", foodType.toLowerCase());
				ft = result.getSingleResult();
			} catch (NoResultException e) {
				ft = null;
				logger.warn("FoodType " + foodType + " does not exists.");
			}
		}
		return ft;
	}

	@Override
	public Optional<FoodType> getFoodType(int id) {
		try (Session session = sf.openSession()) {
			FoodType foodType = session.get(FoodType.class, id);
			if (foodType == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(foodType);
		}
	}

	@Override
	public void updateFoodType(FoodType foodType) {

		
	}

}
