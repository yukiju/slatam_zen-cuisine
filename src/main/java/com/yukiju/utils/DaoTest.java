package com.yukiju.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.apache.log4j.Logger;

import com.yukiju.daos.BrandDao;
import com.yukiju.daos.CategoryDao;
import com.yukiju.daos.FoodTypeDao;
import com.yukiju.daos.ProductDao;
import com.yukiju.daos.RetailerDao;
import com.yukiju.daos.RoleDao;
import com.yukiju.daos.StorageDao;
import com.yukiju.daos.UserDao;
import com.yukiju.repos.Brand;
import com.yukiju.repos.Category;
import com.yukiju.repos.FoodType;
import com.yukiju.repos.Product;
import com.yukiju.repos.Retailer;
import com.yukiju.repos.Role;
import com.yukiju.repos.User;

public class DaoTest {
	/*
	 * Utility for testing Dao's methods note this is not unit testing but for
	 * testing methods run as a java app instead of spring app
	 */

	static Logger logger = Logger.getRootLogger();
	
	static void dummyFoodTypes() {
		FoodTypeDao ftDao = DaoUtil.getFoodTypeDao();
		
		if (ftDao.getAllFoodTypes().isEmpty()) {
			List<FoodType> foodTypes = new ArrayList<FoodType>();
			foodTypes.add(new FoodType(0, "Grains"));
			foodTypes.add(new FoodType(0, "Seeds"));
			foodTypes.add(new FoodType(0, "Vegetables"));
			foodTypes.add(new FoodType(0, "Fruits"));
			
			foodTypes.forEach(e -> {
				ftDao.addNewFoodType(e);
			});
			
		}
		
	}
	
	static void dummyProducts() {
		ProductDao pDao = DaoUtil.getProductDao();
		
		if (pDao.getAllProducts().isEmpty()) {
			RetailerDao retDao = DaoUtil.getRetailerDao();
			Retailer ret = retDao.selectByRetailer("econo");
			Product pro = new Product(0,"00070893800511",1
					, "Cheese Spread"
					, "Cheaper than cheeze wiz", "oz"
					, LocalDateTime.now(), LocalDateTime.now(), 60, 2.79, 0);
			pro.setRetailer(ret);
			pDao.addNewProduct(pro);
			/*
			RetailerDao retDao = DaoUtil.getRetailerDao();
			List<Product> products = new ArrayList<Product>();
			List<Retailer> retailers = new ArrayList<Retailer>();
			//retailers.add(new Retailer(0,"Econo"));
			retDao.addNewRetailer(new Retailer(0,"Econo"));
			products.add(new Product(0,"00070893800511",1
					, "Cheese Spread", "Cheese"
					, "Cheaper than cheeze wiz", "oz"
					, LocalDateTime.now(), LocalDateTime.now(), 60, 2.79, 0));
			products.forEach(e -> {
				pDao.addNewProduct(e);
			});*/
		}
	}
	
	static void productsCrud() {
		ProductDao pDao = DaoUtil.getProductDao();
		RetailerDao retDao = DaoUtil.getRetailerDao();
		Product pro = new Product(0,"00070893800511",1
				, "CddsSddsefffs"
				, "Cheaper than cheeze wiz", "oz"
				, LocalDateTime.now(), LocalDateTime.now(), 60, 2.79, 0);
		List<Retailer> retailers = new ArrayList<Retailer>();
		retailers.add(new Retailer(1, "Walmart"));
		retailers.add(new Retailer(2, "Costco"));
		pro.setRetailers(retailers);
		pDao.addNewProduct(pro);
		logger.info("inside main BEFORE sf close");
		logger.info("product");
		logger.info(pro);
		Retailer retailer = new Retailer();
		retailer = retDao.selectByRetailer("Econo");
		pDao.addRetailerToProduct(pro, retailer);
		FoodTypeDao ftDao = DaoUtil.getFoodTypeDao();
		pro.setFoodType(ftDao.selectByFoodType("Grains"));
		pDao.updateProduct(pro);
		CategoryDao cDao = DaoUtil.getCategoryDao();
		pro.setCategory(cDao.selectByCategory("Fruits"));
		pDao.updateProduct(pro);
		BrandDao bDao = DaoUtil.getBrandDao();
		StorageDao sDao = DaoUtil.getStorageDao();
		pro.setBrand(bDao.selectByBrand("Prima"));
		pro.setStoragePlace(sDao.selectByStorage("Pantry"));
		pDao.updateProduct(pro);
	}
	
	static void dummyRetailers() {
		RetailerDao retDao = DaoUtil.getRetailerDao();
		
		if (retDao.getAllRetailers().isEmpty()) {
			List<Retailer> retailers = new ArrayList<Retailer>();
			retailers.add(new Retailer(0,"Walmart"));
			retailers.add(new Retailer(0, "Costco"));
			retailers.add(new Retailer(0,"Econo"));
			
			retailers.forEach(e -> {
				retDao.addNewRetailer(e);
			});
		}
	}
	
	static void dummyBrands() {
		BrandDao bDao = DaoUtil.getBrandDao();
		
		if (bDao.getAllBrands().isEmpty()) {
			List<Brand> brands = new ArrayList<Brand>();
			brands.add(new Brand(0, "Prima"));
			brands.add(new Brand(0, "Top Ramen"));
			
			brands.forEach(e -> {
				bDao.addNewBrand(e);
			});
		}
	}
	
	static void dummyCategories() {
		CategoryDao cDao = DaoUtil.getCategoryDao();
		
		if (cDao.getAllCategories().isEmpty()) {
			List<Category> categories = new ArrayList<Category>();
			categories.add(new Category(0, "Fruits"));
			categories.add(new Category(0, "Vegetables"));
			categories.add(new Category(0, "Grains"));
			categories.add(new Category(0, "Nuts"));
			
			categories.forEach(e -> {
				cDao.addNewCategory(e);
			});
		}
	}

	static void dummyRoles() {
		RoleDao rDao = DaoUtil.getRoleDao();

		if (rDao.getAllRoles().isEmpty()) {
			List<Role> roles = new ArrayList<Role>();
			roles.add(new Role(0, "admin"));
			roles.add(new Role(0, "manager"));
			roles.add(new Role(0, "user"));

			roles.forEach(e -> {
				rDao.addNewRole(e);
			});
		}
		// System.out.println(rDao.selectByRole("user"));
	}

	public static void roleQueryTest() {
		RoleDao rDao = DaoUtil.getRoleDao();
		List<Role> roles = rDao.getAllRoles();
		System.out.println("\nGetting all roles.");

		for (Role r : roles) {
			System.out.println(r);
		}

		// selectByRole will return null if not exists
		// Optional returns object or null
		Role user = new Role(0, "manager32");
		Optional<Role> role = Optional.ofNullable(rDao.selectByRole(user.getRole()));
		if (!role.isPresent()) {
			rDao.addNewRole(user);
			logger.info("Role " + user.getRole() + " was created.");
		} else {
			logger.warn("Role " + user.getRole() + " already exists.");
		}

		// DaoUtil.closeSessionFactory();
	}

	public static void userQueryTest() {
		UserDao uDao = DaoUtil.getUserDao();
		RoleDao rDao = DaoUtil.getRoleDao();
		// Role role = new Role(0, "admin");
		// add logic from roleQueryTest() to line below
		Role admin = rDao.selectByRole("admin");
		User user = new User(0, "mreyes", "mreyes@email.com", "password", "Milton", "Reyes", null);
		if (user.getRole() == null) {
			user.setUsername("user");
			user.setEmail("user@email.com");
			user.setFirstName("new");
			user.setLastName("user");
		}
		
		User user4 = new User(0, "user2", "user2@email.com", "password", "user", "two", null);
		Optional<User> username4 = Optional.ofNullable(uDao.selectUserByUsername(user4.getUsername()));
		if (!username4.isPresent()) {
			uDao.addNewUser(user4);
		} else {
			logger.warn("Username " + user4.getUsername() + " already exists.");
		}

		Optional<User> username = Optional.ofNullable(uDao.selectUserByUsername(user.getUsername()));
		if (!username.isPresent()) {
			uDao.addNewUser(user);
		} else {
			logger.warn("Username " + user.getUsername() + " already exists.");
		}

		uDao.selectUserById(1).ifPresent((u) -> System.out.println(u));

		Optional<User> user2 = uDao.selectUserById(9);
		if (user2.isPresent()) {
			User user2Update = user2.get();
			user2Update.setFirstName("newuser234");
			uDao.updateUser(user2Update);
		}

		// System.out.println("\n\n\n\n\n\n\n\n");
		User user3;
		if (uDao.selectUserById(1).isPresent()) {
			user3 = uDao.selectUserById(1).get();
			System.out.println(user3);
			System.out.println(user3.getRole());
		}

		// System.out.println("\n\n\n\n\n\n\n\n");
		Set<User> users = uDao.getUsersByRoleId(1);
		// System.out.println(users);
		for (User u : users) {
			System.out.println(u);
		}

		// DaoUtil.closeSessionFactory();
	}

	public static void main(String[] args) {
		
		//dummyRoles();
		//userQueryTest();
		//dummyCategories();
		//dummyBrands();
		//dummyRetailers();
		productsCrud();
		//dummyProducts();
		//dummyFoodTypes();
		
		DaoUtil.closeSessionFactory();

	}

}
