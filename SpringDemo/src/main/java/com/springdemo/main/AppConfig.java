/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.main;

import com.springdemo.nodes.CompanysManager;
import com.mongodb.MongoClient;
import com.springdemo.entity.manager.BasicDAO;
import com.springdemo.entity.manager.DAOManager;
import com.springdemo.nodes.ManagerCRUD;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 *
 * @author Buy
 */
@Configuration
public class AppConfig {
    
//    @Bean
//    public MongoClientFactoryBean mongo(){
//        MongoClientFactoryBean factory = new MongoClient("localhost");
//        factory.setHost("localhost");
//        return factory;
//    }
    
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception{
        return new SimpleMongoDbFactory(new MongoClient("localhost"), "testCompany");
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongoDbFactory());
    }
    
    @Bean
    public BasicDAO dbService() throws Exception{
        return new DAOManager(mongoTemplate());
    }
    
    @Bean
    public ManagerCRUD nodeManager(){
        return new CompanysManager();
    }
    
}
