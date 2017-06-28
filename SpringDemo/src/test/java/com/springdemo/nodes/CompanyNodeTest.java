/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.BasisOfCompany;
import com.springdemo.entity.Company;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Buy
 */
public class CompanyNodeTest {

    private Company companyRoot,company1,company2,company3
            ,company4,company5,company6;
    private CompanyNode<Company> nodeMain, nodeChild1, nodeChild2,nodeChild3, 
            nodeChild4, nodeChild5,nodeChild6;
    
    
    public CompanyNodeTest() {
    }
    
  
    @Before
    public void setUp() {
        companyRoot = new Company("Company root", 1000);
        company1 = new Company("Company1",443);
        company2 = new Company("Company2",243);
        company3 = new Company("Company3",103);
        company4 = new Company("Company4",43);
        company5 = new Company("Company5",84);
        company6 = new Company("Company6", 500);
        
        nodeMain = new CompanyNode<>(companyRoot);
        nodeChild1 = new CompanyNode<>(company1);
        nodeChild2 = new CompanyNode<>(company2);
        nodeChild3 = new CompanyNode<>(company3);
        nodeChild4 = new CompanyNode<>(company4);
        nodeChild5 = new CompanyNode<>(company5);
        nodeChild6 = new CompanyNode<>(company6);

    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addChild method, of class CompanyNode.
     */
    @Test
    public void testAddChild() {
        System.out.println("addChild");
        nodeMain.addChild(nodeChild1);
        nodeChild1.addChild(nodeChild2);
        nodeChild3.setParent(nodeMain);
        System.out.println(nodeMain.getChildsList().size());
        assertTrue(nodeMain.getChildsList().size()==2);
        assertTrue(nodeChild1.getParent().equals(nodeChild3.getParent()));
    }

    /**
     * Test of setParentToCompany method, of class CompanyNode.
     */
//    @Ignore
    @org.junit.Test
    public void testSetParent() {
        System.out.println("setParentToCompany");
        nodeMain.setParent(nodeChild1);
        nodeChild1.addChild(nodeChild2);
        nodeChild3.setParent(nodeMain);
        assertTrue(nodeMain.getParent()==nodeChild1);
    }

    /**
     * Test of updateData method, of class CompanyNode.
     */
//    @Ignore
    @org.junit.Test
    public void testUpdateData() {
        System.out.println("updateData");
        nodeMain.setParent(nodeChild1);
        nodeChild1.addChild(nodeChild2);
        nodeChild3.setParent(nodeMain);
        System.out.println("updateData result : "+nodeMain.getTotalEarnings());
    }

    /**
     * Test of countChildsEarnings method, of class CompanyNode.
     */
//    @Ignore
    @org.junit.Test
    public void testCountTotalEarnings() {
        System.out.println("countChildsEarnings");
        nodeMain.addChild(nodeChild1);
        nodeChild1.addChild(nodeChild2);
        nodeChild3.setParent(nodeMain);
        int actual = nodeMain.getTotalEarnings();
        int expected = nodeChild1.getCompanyEarnings()
                +nodeChild2.getCompanyEarnings()
                +nodeChild3.getCompanyEarnings()
                +nodeMain.getCompanyEarnings();
        
        assertEquals(expected, actual);
        
    }
    
}
