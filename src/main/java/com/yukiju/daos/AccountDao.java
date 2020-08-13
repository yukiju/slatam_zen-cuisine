package com.yukiju.daos;

import java.util.Optional;

import org.hibernate.PersistentObjectException;

import com.yukiju.repos.Account;

public interface AccountDao {

	void addNewAccount(Account account) throws PersistentObjectException;

	Optional<Account> getAccountById(int id);

	void updateAccount(Account account);

	Optional<Account> getAccountByUserId(int id);

}
