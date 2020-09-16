package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Retailer;


public interface RetailerDao {
	
	List<Retailer> getAllRetailers();
	
	void addNewRetailer(Retailer retailer) throws PersistentObjectException;
	
	Retailer selectByRetailer(String retailer) throws NoResultException;
	
	Optional<Retailer> getRetailer(int id);

}
