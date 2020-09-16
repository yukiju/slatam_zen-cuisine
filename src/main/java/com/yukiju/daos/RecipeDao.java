package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Recipe;

public interface RecipeDao {

	List<Recipe> getAllRecipes();

	void addNewRecipe(Recipe recipe) throws PersistentObjectException;

	Recipe selectByRecipe(String recipe) throws NoResultException;

	Optional<Recipe> getRecipe(int id);

}
