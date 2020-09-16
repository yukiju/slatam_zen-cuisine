package com.yukiju.utils;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.yukiju.daos.AccountDao;
import com.yukiju.daos.AccountDaoImpl;
import com.yukiju.daos.BrandDao;
import com.yukiju.daos.BrandDaoImpl;
import com.yukiju.daos.CategoryDao;
import com.yukiju.daos.CategoryDaoImpl;
import com.yukiju.daos.ProductDao;
import com.yukiju.daos.ProductDaoImpl;
import com.yukiju.daos.RecipeDao;
import com.yukiju.daos.RecipeDaoImpl;
import com.yukiju.daos.RetailerDao;
import com.yukiju.daos.RetailerDaoImpl;
import com.yukiju.daos.RoleDao;
import com.yukiju.daos.RoleDaoImpl;
import com.yukiju.daos.StorageDao;
import com.yukiju.daos.StorageDaoImpl;
import com.yukiju.daos.UserDao;
import com.yukiju.daos.UserDaoImpl;
import com.yukiju.repos.Account;
import com.yukiju.repos.Brand;
import com.yukiju.repos.Category;
import com.yukiju.repos.Product;
import com.yukiju.repos.Recipe;
import com.yukiju.repos.Retailer;
import com.yukiju.repos.Role;
import com.yukiju.repos.Storage;
import com.yukiju.repos.User;



public class DaoUtil {

	static Logger logger = Logger.getRootLogger();

	private static ServiceRegistry registry = null;
	private static Configuration configuration = null;
	private static SessionFactory sf = null;

	private static UserDaoImpl userDaoImpl = null;
	private static RoleDaoImpl roleDaoImpl = null;
	private static AccountDaoImpl accountDaoImpl = null;
	
	private static CategoryDaoImpl categoryDaoImpl = null;
	private static BrandDaoImpl brandDaoImpl = null;
	private static RetailerDaoImpl retailerDaoImpl = null;
	private static RecipeDaoImpl recipeDaoImpl = null;
	private static StorageDaoImpl storageDaoImpl = null;
	private static ProductDaoImpl productDaoImpl = null;
	
	public static CategoryDao getCategoryDao() {
		if (categoryDaoImpl == null) {
			categoryDaoImpl = new CategoryDaoImpl();
		}
		return categoryDaoImpl;
	}
	
	public static BrandDao getBrandDao() {
		if (brandDaoImpl == null) {
			brandDaoImpl = new BrandDaoImpl();
		}
		return brandDaoImpl;
	}
	
	public static RetailerDao getRetailerDao() {
		if (retailerDaoImpl == null) {
			retailerDaoImpl = new RetailerDaoImpl();
		}
		return retailerDaoImpl;
	}
	
	public static RecipeDao getRecipeDao() {
		if (recipeDaoImpl == null) {
			recipeDaoImpl = new RecipeDaoImpl();
		}
		return recipeDaoImpl;
	}
	
	public static StorageDao getStorageDao() {
		if (storageDaoImpl == null) {
			storageDaoImpl = new StorageDaoImpl();
		}
		return storageDaoImpl;
	}
	
	public static ProductDao getProductDao() {
		if (productDaoImpl == null) {
			productDaoImpl = new ProductDaoImpl();
		}
		return productDaoImpl;
	}
	
	public static UserDao getUserDao() {
		if (userDaoImpl == null) {
			userDaoImpl = new UserDaoImpl();
		}
		return userDaoImpl;
	}
	
	public static RoleDao getRoleDao() {
		if (roleDaoImpl == null) {
			roleDaoImpl = new RoleDaoImpl();
		}
		return roleDaoImpl;
	}
	
	public static AccountDao getAccountDao() {
		if (accountDaoImpl == null) {
			accountDaoImpl = new AccountDaoImpl();
		}
		return accountDaoImpl;
	}


	public static SessionFactory getSessionFactory() {
		if (sf == null || registry == null || configuration == null) {
			try {
				String jdbcUrl = String.format("jdbc:postgresql://%s:5432/postgres", System.getenv("YUKAJUCO_URL"));

				configuration = new Configuration().configure().setProperty("hibernate.connection.url", jdbcUrl)
						.setProperty("hibernate.connection.username", System.getenv("YUKAJUCO_U"))
						.setProperty("hibernate.connection.password", System.getenv("YUKAJUCO_P"))
						.addAnnotatedClass(User.class)
						.addAnnotatedClass(Role.class)
						.addAnnotatedClass(Account.class)
						.addAnnotatedClass(Category.class)
						.addAnnotatedClass(Brand.class)
						.addAnnotatedClass(Recipe.class)
						.addAnnotatedClass(Storage.class)
						.addAnnotatedClass(Product.class)
						.addAnnotatedClass(Retailer.class);

				registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

				sf = configuration.buildSessionFactory(registry);
				logger.info("New SessionFactory created.");
			} catch (HibernateException e) {
				logger.error("Error creating the session factory.");
				e.printStackTrace();
			}
		}
		return sf;
	}

	public static void closeSessionFactory() {
		if (sf != null) {
			sf.close();
			logger.info("Session Factory is now closed.");
		}
		if (sf == null) {
			logger.warn("Session Factory is null.");
		}
	}
}
