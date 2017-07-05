/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.main;

import com.springdemo.entity.Company;
import com.springdemo.entity.manager.DAOManager;
import com.springdemo.nodes.CompanysManager;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Buy
 */
@RestController
public class Controller {
    @Autowired
    private CompanysManager manager;
    public static void main(String[] args) {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DAOManager dao = context.getBean(DAOManager.class);
        dao.clearDB();
        
        Company parent = new Company("Main", 40000);
        Company child1 = new Company("Child 1", 1000,parent);
        Company child2 = new Company("Child 2", 3000,parent);
        Company child3 = new Company("Child 3", 1000,parent);
        Company child11 = new Company("Child 1 1", 1000,child1);
        Company child111 = new Company("Child 1 1 1", 1000,child11);
        Company child22 = new Company("Child 2 2", 3000, child2);

        System.out.println("RootChild11 path is : "+child11.getPath());
        
        ArrayList<Company> data = new ArrayList<>();
        data.add(parent);
        data.add(child1);
        data.add(child2);
        data.add(child3);
        data.add(child11);
        data.add(child111);
        data.add(child22);
        for(Company company:data)
            try{dao.save(company);}catch(Exception e){System.out.println(e.getMessage());}
        
//        for(Company company:dao.getRoots())
//            System.out.println("Root: "+company.toString());
        
        System.out.println(dao.getChildsOf("Child 1").size());         
        System.out.println(dao.getChildsOf("Main").size());
        
        long start = System.currentTimeMillis();
        CompanysManager manager = new CompanysManager(dao);
        System.out.println("All elements in db :  "+dao.getAll().size());
        Company test = dao.getRootOf("Child 1 1");
//        manager.update(test);
        System.out.println(test.treeToString(System.lineSeparator()));
        System.out.println(System.currentTimeMillis()-start);
    }
}
