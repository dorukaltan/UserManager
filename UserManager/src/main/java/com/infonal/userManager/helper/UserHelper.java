package com.infonal.userManager.helper;


import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import com.infonal.userManager.model.User;

public class UserHelper {
	
	/*
	 * Makes insert and updates for user entity.
	 * If an exception occurs, it returns false as feedback.
	 */
	public static boolean saveUser(User user)
	{
		
		MongoOperations mongoOperation = MongoDBHelper.getMongoOperation();
		try
		{
			mongoOperation.save(user);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Returns all users in database
	 */
	public static List<User> getAllUsers()
	{
		MongoOperations mongoOperation = MongoDBHelper.getMongoOperation();
	    return mongoOperation.findAll(User.class);
	}
	
	/*
	 * Removes a user from database
	 * If an exception occurs, it returns false as feedback.
	 */
	public static boolean removeUser(User user)
	{
		MongoOperations mongoOperation = MongoDBHelper.getMongoOperation();
		try
		{
			mongoOperation.remove(user);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
