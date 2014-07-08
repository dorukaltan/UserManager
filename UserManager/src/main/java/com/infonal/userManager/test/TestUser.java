package com.infonal.userManager.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.infonal.userManager.helper.UserHelper;
import com.infonal.userManager.model.User;

public class TestUser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

//	@Test
//	public void test() {
//		
//		fail("Not yet implemented");
//	}
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setName("test1");
		user.setLastname("test1");
		if(UserHelper.saveUser(user))
		{
			System.out.println("user created successfully: " + user.getId());
			if(!UserHelper.removeUser(user))
				System.out.println(user.getId() + " user could not be removed.");;
		}
		else
			fail("User could not be created");
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setName("test1");
		user.setLastname("test1");
		if(UserHelper.saveUser(user))
		{
			user.setName("test2");
			if(UserHelper.saveUser(user))
				System.out.println("user updated successfully");
			else
				fail("User could not be updated");
			if(!UserHelper.removeUser(user))
				System.out.println(user.getId() + " user could not be removed.");
		}
		else
			fail("User could not be created in order to test update");
	}
	
	@Test
	public void testGetUser() {
		User user = new User();
		user.setName("test1");
		user.setLastname("test1");
		if(UserHelper.saveUser(user))
		{
			User user2 = UserHelper.getUser(user.getId());
			if(user2 == null || !user2.getName().equals(user.getName()))
				fail("getUser error");
			else
				System.out.println("userGet succeeded");

			if(!UserHelper.removeUser(user))
				System.out.println(user.getId() + " user could not be removed.");
		}
		else
			fail("User could not be created in order to test select");
	}
	
	@Test
	public void testRemoveUser() {
		User user = new User();
		user.setName("test1");
		user.setLastname("test1");
		if(UserHelper.saveUser(user))
			if(UserHelper.removeUser(user))
				System.out.println("User removed successfully");
			else
				fail("User could not be removed");
		else
			fail("User could not be created");
		
	}
	

}
