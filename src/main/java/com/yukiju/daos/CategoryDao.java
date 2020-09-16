package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Category;

public interface CategoryDao {
	List<Category> getAllCategories();
	
	void addNewCategory(Category category) throws PersistentObjectException;
	
	Category selectByCategory(String category) throws NoResultException;
	
	Optional<Category> getCategroy(int id);

}
