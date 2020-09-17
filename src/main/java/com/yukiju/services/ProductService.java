package com.yukiju.services;

import org.springframework.stereotype.Service;

import com.yukiju.daos.UserDao;
import com.yukiju.exceptions.HttpStatusException;
import com.yukiju.repos.Product;
import com.yukiju.utils.DaoUtil;
import com.yukiju.daos.ProductDao;


@Service
public class ProductService {
	
	private ProductDao pDao = null;
	
	public ProductService() {
		super();
		this.pDao = DaoUtil.getProductDao();
	}
	
	public ProductService(ProductDao pDao) {
		super();
		this.pDao = pDao;
	}
	
	public Product addNewProduct(Product product) {
		
		if (product.getProduct().isEmpty()) {
			throw new HttpStatusException(422);
		}
		
		return pDao.addNewProduct(product);
	}

}
