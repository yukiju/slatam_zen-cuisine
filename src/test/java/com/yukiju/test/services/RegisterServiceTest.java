package com.yukiju.test.services;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.yukiju.daos.UserDao;
import com.yukiju.daos.UserDaoImpl;
import com.yukiju.exceptions.HttpStatusException;
import com.yukiju.repos.User;
import com.yukiju.services.RegisterService;

public class RegisterServiceTest {
	
	UserDao uDao = Mockito.mock(UserDaoImpl.class);
	
	RegisterService regService = new RegisterService(uDao);
	
	
	// This will run once before any @Test
	/*@BeforeClass
	public void classSetup() {
		
	}*/
	
	// This will run before each @Test
	@Before
	public void setup() {
		uDao = Mockito.mock(UserDaoImpl.class);
	}
	
	@Test
	public void insertUserTest() {
		User user = new User(0
				,"mreyes"
				,"mreyes@email.com"
				,"password"
				,"Milton"
				,"Reyes"
				,null);
		
		// stub method on userDao
		Mockito.when(regService.addNewUser(user)).thenReturn(user);
		
		User result = regService.addNewUser(user);
		
		assertEquals("Method should cleanly finish and return user from the uDao.addNewUser return",user, result);
	}
	
	@Test(expected=HttpStatusException.class)
	public void noEmailTest() {
		/*
		 * Test whether a user didn't include an email
		 * 
		 * We provide a null email to test if the service addNewUser throws an exception
		 */
		User user = new User(0
				,"mreyes"
				//,"mreyes@email.com" // including an email makes the test fail
				,null
				,"password"
				,"Milton"
				,"Reyes"
				,null);
		
		// stub method on userDao
		Mockito.when(regService.addNewUser(user)).thenReturn(user);
		
		//User result = uDao.addNewUser(user);
		
		// Null email should throw and exception, we should not reach this point
		Assert.fail();
	}
	
	// Run after each @Test
	/*@After
	public void postTest() {
		
	}
	
	// Runs once after all @Tests
	@AfterClass
	public void postTeardown() {
		
	}*/

}
