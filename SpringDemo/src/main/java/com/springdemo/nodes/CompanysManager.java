/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.Company;
import com.springdemo.entity.manager.BasicDAO;
import com.springdemo.entity.manager.DAOManager;

/**
 *
 * @author Buy
 */
public class CompanysManager implements ManagerCRUDExtended {

    private BasicDAO dao;
    
    public CompanysManager(){
        
    }
    public CompanysManager (BasicDAO dao){
        this.dao = dao;
    }
        

    private int countAndSetTotalEarnings(Company company) {
        int childsEarnings = 0;
        if(!company.getChilds().isEmpty()){
            for (Company child : company.getChilds()) {
                childsEarnings += countAndSetTotalEarnings(child);
            }
        company.appendChildsEarnings(childsEarnings);
        return company.getTotalEarnings();
        }
        return company.getEarnings();
    }
    
    @Override
    public String toStringTreeOf(Company company) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(String companyToEdit, String newName, int newEarnings, String newParentCompanyName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTree(String rootNameToDelete) {
        
    }

    @Override
    public Company getWholeTreeOf(String companyName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Company updateDataOf(Company company) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Company createCompany(String name, int earnings) {
        if(dao.exist(name)){
        throw new IllegalArgumentException("This name is exist in DataBase");
    }
        return new Company(name, earnings);
    }

    @Override
    public Company createCompany(String name, int earnings, String parentName) {
        if(!dao.exist(parentName))
            throw new IllegalArgumentException("This company not exist");
        return new Company(name, earnings, dao.getByName(parentName));
    }

    @Override
    public void delete(String companyName) {
        
        Company company = dao.getByName(companyName);
        dao.delete(company);
    }

    public Company update(Company company) {
        this.countAndSetTotalEarnings(company);
        return company;
    }
    
}
