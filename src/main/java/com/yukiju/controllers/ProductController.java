package com.yukiju.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yukiju.repos.Product;
import com.yukiju.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/newproduct")
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.POST},
allowedHeaders = {"*"})
public class ProductController {
	
	static Logger logger = Logger.getRootLogger();
	
	@Autowired
	ProductService prodService;
	
	@PostMapping @ResponseStatus(HttpStatus.CREATED)
	public void newProduct(@RequestBody Product product) {
		logger.info("New Product");
		logger.info(product);
		prodService.addNewProduct(product);
		
	}
	

}
