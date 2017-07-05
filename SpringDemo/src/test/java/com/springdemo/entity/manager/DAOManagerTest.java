/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.entity.manager;

import com.springdemo.entity.Company;
import com.springdemo.main.AppConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Buy
 */
public class DAOManagerTest {

    static DAOManager instance;
    private static ArrayList<Company> data;
    private static Company child22, child111, child11, parent, child1, child2, child3;

    public DAOManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        instance = context.getBean(DAOManager.class);
        parent = new Company("Main", 40000);
        child1 = new Company("Child 1", 1000, parent);
        child2 = new Company("Child 2", 3000, parent);
        child3 = new Company("Child 3", 1000, parent);
        child11 = new Company("Child 1 1", 1000, child1);
        child111 = new Company("Child 1 1 1", 1000, child11);
        child22 = new Company("Child 2 2", 3000, child2);
        data = new ArrayList<>();
        data.add(parent);
        data.add(child1);
        data.add(child2);
        data.add(child3);
        data.add(child11);
        data.add(child111);
        data.add(child22);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception{
//            for (Company company : data) 
//                instance.save(company);
    }

    @After
    public void tearDown() {
//        instance.clearDB();
    }

    /**
     * Test of save method, of class DAOManager.
     *
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testSave() throws Exception {
        System.out.println("save");

        instance.save(data.get(3));
        data.toArray();
        assertArrayEquals(data.toArray(), instance.getAll().toArray());
    }

    /**
     * Test of update method, of class DAOManager.
     */
//    @Ignore
    @Test
    public void testUpdate() {
        System.out.println("update");

        Company newData = new Company("Updated", 32400, child1);
        instance.update(child3, newData);

        assertEquals(3, instance.getChildsOf(data.get(1).getName()).size());
    }

    /**
     * Test of delete method, of class DAOManager.
     */
    @Ignore
    @Test
    public void testDelete() {
        System.out.println("delete");
        instance.delete(child2);

        assertTrue((data.size() - 1) == instance.getAll().size());
    }

    /**
     * Test of getRoots method, of class DAOManager.
     */
    @Ignore
    @Test
    public void testGetRoots() {
        System.out.println("getRoots");
        int expResult = 1;
        List<Company> result = instance.getRoots();
        assertEquals(expResult, result.size());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class DAOManager.
     */
//    @Ignore
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        for(Company com:instance.getAll())
            System.out.println(com.toString());
        
                
//        assertEquals(expResult.size(), result.size());

    }

    /**
     * Test of getByNameWithBuild method, of class DAOManager.
     */
//    @Ignore
    @Test
    public void testGetByNameWithBuild() {
        System.out.println("getByNameWithBuild");
        String companyName = "child 2";
        int expResult = 1;
        int result = instance.getByNameWithBuild(companyName).getChilds().size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByName method, of class DAOManager.
     */
//    @Ignore
    @Test
    public void testGetByName() {
        System.out.println("getByName");
        String companyName = data.get(3).getName();

        Company result = instance.getByName(companyName);
        assertEquals(companyName, result.getName());

    }

    /**
     * Test of getRootOf method, of class DAOManager.
     */
//    @Ignore
    @Test
    public void testGetRootOf() {
        System.out.println("getRootOf");
        String companyName = child2.getName();
        String expResult = parent.getName();
        String result = instance.getRootOf(companyName).getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getChildsOf method, of class DAOManager.
     */
    @Test
    public void testGetChildsOf() {
        System.out.println("getChildsOf");
        String rootName = child2.getName();

        ;
        assertEquals(1, instance.getChildsOf(rootName).size());
    }

    /**
     * Test of clearDB method, of class DAOManager.
     */
    @Test
    public void testClearDB() {
        System.out.println("clearDB");
        instance.clearDB();
        try {
            instance.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test of exist method, of class DAOManager.
     */
    @Test
    public void testExist() {
        System.out.println("exist");
        String companyName = data.get(4).getName();

        boolean expResult = true;
        boolean result = instance.exist(companyName);
        assertEquals(expResult, result);

    }

}
