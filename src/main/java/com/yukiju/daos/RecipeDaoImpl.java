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

import com.yukiju.repos.Recipe;
import com.yukiju.utils.DaoUtil;

public class RecipeDaoImpl implements RecipeDao {

	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;

	public RecipeDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = new ArrayList<Recipe>();
		try (Session session = sf.openSession()) {
			Query<Recipe> results = session.createQuery("from Recipe", Recipe.class);
			if (results.list().isEmpty()) {
				logger.warn("Recipes table is empty");
			}
			recipes = (ArrayList<Recipe>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to recipes table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return recipes;
	}

	@Override
	public void addNewRecipe(Recipe recipe) throws PersistentObjectException {
		if (!recipe.getRecipe().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(recipe);
				tx.commit();
				logger.info("Added new recipe: " + recipe.getRecipe());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist recipe " + recipe);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public Recipe selectByRecipe(String recipe) throws NoResultException {
		Recipe rec = new Recipe();
		if (!recipe.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Recipe rec where lower(rec.recipe) = :recipe";
				Query<Recipe> result = session.createQuery(hql, Recipe.class);
				result.setParameter("recipe", recipe.toLowerCase());
				rec = result.getSingleResult();
			} catch (NoResultException e) {
				rec = null;
				logger.warn("Recipe " + recipe + " does not exists.");
			}
		}
		return rec;
	}

	@Override
	public Optional<Recipe> getRecipe(int id) {
		try (Session session = sf.openSession()) {
			Recipe recipe = session.get(Recipe.class, id);
			if (recipe == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(recipe);
		}
	}

}
