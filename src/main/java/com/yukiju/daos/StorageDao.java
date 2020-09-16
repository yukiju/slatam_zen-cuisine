package com.yukiju.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Storage;

public interface StorageDao {

	List<Storage> getAllStorages();

	void addNewStorage(Storage storage) throws PersistentObjectException;

	Storage selectByStorage(String storage) throws NoResultException;

	Optional<Storage> getStorage(int id);

}
