package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Product;


public interface ProductDao {
	
	List<Product> getAllProducts();

	void addNewProduct(Product product) throws PersistentObjectException;

	Product selectByUPC(String upc) throws NoResultException;

	Optional<Product> getProduct(int id);

}
