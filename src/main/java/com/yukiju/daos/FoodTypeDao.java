package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.FoodType;

public interface FoodTypeDao {
	
	List<FoodType> getAllFoodTypes();

	void addNewFoodType(FoodType foodType) throws PersistentObjectException;
	
	void updateFoodType(FoodType foodType);

	FoodType selectByFoodType(String foodType) throws NoResultException;

	Optional<FoodType> getFoodType(int id);

}
