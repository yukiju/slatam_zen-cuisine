package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Brand;

public interface BrandDao {
	List<Brand> getAllBrands();

	void addNewBrand(Brand brand) throws PersistentObjectException;

	Brand selectByBrand(String brand) throws NoResultException;

	Optional<Brand> getBrand(int id);

}
