package com.yukiju.daos;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yukiju.repos.Account;
import com.yukiju.utils.DaoUtil;

public class AccountDaoImpl implements AccountDao {

	static Logger logger = Logger.getRootLogger();

	SessionFactory sf = null;

	public AccountDaoImpl() {
		super();
		sf = DaoUtil.getSessionFactory();
		logger.info("Opening new " + this.getClass().toString() + " SessionFactory");
	}

	@Override
	public void addNewAccount(Account account) throws PersistentObjectException {
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(account);
			tx.commit();
		}

	}

	@Override
	public Optional<Account> getAccountById(int id) {
		try (Session session = sf.openSession()) {
			return Optional.ofNullable(session.get(Account.class, id));
		}
	}

	@Override
	public void updateAccount(Account account) {
		Optional<Account> sameAccount = getAccountById(account.getId());
		if (sameAccount.isPresent()) {
			try (Session session = sf.openSession()) {
				Transaction tx = session.beginTransaction();
				account = (Account) session.merge(account);
				tx.commit();
			}
		}
	}

	@Override
	public Optional<Account> getAccountByUserId(int id) {
		try (Session session = sf.openSession()) {
			return Optional.ofNullable(session.get(Account.class, id));
		}
	}

}
