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

import com.yukiju.repos.Product;
import com.yukiju.repos.Retailer;
import com.yukiju.utils.DaoUtil;

public class ProductDaoImpl implements ProductDao {
	
	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;
	
	public ProductDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();
		try (Session session = sf.openSession()) {
			Query<Product> results = session.createQuery("from Product", Product.class);
			if (results.list().isEmpty()) {
				logger.warn("Products table is empty");
			}
			products = (ArrayList<Product>) results.list();
		} catch (Exception e) {
			logger.error("Could't connect to products table.");
			logger.error(e.toString());
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void addNewProduct(Product product) throws PersistentObjectException {
		if (!product.getProduct().isEmpty()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				session.persist(product);
				tx.commit();
				logger.info("Added new product: " + product.getProduct());
			} catch (PersistentObjectException e) {
				logger.error("Trying to persist product " + product);
				logger.error("Check wether object Id is set to 0.");
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addRetailerToProduct(Product product, Retailer retailer) {
		Optional<Product> sameProduct = getProduct(product.getId());
		if (sameProduct.isPresent()) {
			List<Retailer> retailers = new ArrayList<Retailer>();
			Boolean theSame = null;
			retailers = product.getRetailers();
			if (!retailers.isEmpty()) {
				for (Retailer ret : retailers) {
					theSame = retailer.getRetailer().equalsIgnoreCase(ret.getRetailer());
				}
				if (theSame == false) {
					retailers.add(retailer);
					product.setRetailers(retailers);
					logger.info("RETAILER IS NOT THE SAME");
					try (Session session = sf.openSession()) {
						Transaction tx = session.beginTransaction();
						product = (Product) session.merge(product);
						tx.commit();
						logger.info("Updated product: "+ product.getProduct());
					}
				}
			}						
		} else {
			logger.warn(product + " not found.");
		}
	}
	
	@Override
	public void updateProduct(Product product) {
		Optional<Product> sameProduct = getProduct(product.getId());
		if (sameProduct.isPresent()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				product = (Product) session.merge(product);
				tx.commit();
				logger.info("Updated product: "+ product.getProduct());
			}
		} else {
			logger.warn(product + " not found.");
		}
	}

	@Override
	public Product selectByUPC(String upc) throws NoResultException {
		Product b = new Product();
		if (!upc.isEmpty()) {
			try (Session session = sf.openSession()) {
				String hql = "from Product b where lower(b.product) = :product";
				Query<Product> result = session.createQuery(hql, Product.class);
				result.setParameter("product", upc.toLowerCase());
				b = result.getSingleResult();
			} catch (NoResultException e) {
				b = null;
				logger.warn("Product " + upc + " does not exists.");
			}
		}
		return b;
	}

	@Override
	public Optional<Product> getProduct(int id) {
		try (Session session = sf.openSession()) {
			Product product = session.get(Product.class, id);
			if (product == null) {
				logger.warn("There's no role with id: " + id);
				return Optional.empty();
			}
			return Optional.of(product);
		}
	}

}
