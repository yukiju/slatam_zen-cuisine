package com.yukiju.daos;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.yukiju.repos.Category;
import com.yukiju.utils.DaoUtil;


public class CategoryDaoImpl implements CategoryDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;
	
	public CategoryDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		try (Session session = sf.openSession()) {
			Query<Category> results = session.createQuery("from Category", Category.class);
			if (results.list().isEmpty()) {
				logger.warn("Categories table is empty");
			}
			categories = (ArrayList<Category>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to categories table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public void addNewCategory(Category category) throws PersistentObjectException {
		if (!category.getCategory().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(category);
				tx.commit();
				logger.info("Added new category: " + category.getCategory());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist category " + category);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Category selectByCategory(String category) throws NoResultException {
		Category c = new Category();
		if (!category.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Category c where lower(c.category) = :category";
				Query<Category> result = session.createQuery(hql, Category.class);
				result.setParameter("category", category.toLowerCase());
				c = result.getSingleResult();
			} catch (NoResultException e) {
				c = null;
				logger.warn("Category " + category + " does not exists.");
			}
		}
		return c;
	}

	@Override
	public Optional<Category> getCategroy(int id) {
		try (Session session = sf.openSession()) {
			Category category = session.get(Category.class, id);
			if (category == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(category);
		}
	}

}
