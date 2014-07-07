package com.infonal.userManager.helper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.infonal.userManager.config.SpringMongoConfig;

public class MongoDBHelper {
	public static MongoOperations getMongoOperation() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	    return (MongoOperations)ctx.getBean("mongoTemplate");
	}
}
