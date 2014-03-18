package com.juvenxu.mvnbook.account.persist;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountPersistServiceTest {
	
	private AccountPersistService service;

	@Before
	public void setUp() throws Exception {
		File persistDataFile = new File("target/test-classes/persist-data.xml");
		if(persistDataFile.exists()){
			persistDataFile.delete();
		}
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
		service = (AccountPersistService) ctx.getBean("accountPersistService");
		
		Account account = new Account();
		account.setId("yiping");
		account.setName("Chen Yiping");
		account.setEmail("jc5230@gmail.com");
		account.setPassword("this_should_be_encrypted");
		account.setActivated(true);
		
		service.createAccount(account);
	}

	@Test
	public void test() throws Exception{
		Account account = service.readAccount("yiping");
		assertNotNull(account);
		assertEquals("yiping", account.getId());
		assertEquals("Chen Yiping", account.getName());
		assertEquals("jc5230@gmail.com", account.getEmail());
		assertEquals("this_should_be_encrypted", account.getPassword());
		assertTrue(account.isActivated());
	}

}
