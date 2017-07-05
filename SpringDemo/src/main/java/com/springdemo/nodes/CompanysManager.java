/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.Company;
import com.springdemo.entity.manager.BasicDAO;
/**
 *
 * @author Buy
 */
public class CompanysManager implements ManagerCRUDExtended {

    private final BasicDAO dao;
    
    public CompanysManager (BasicDAO dao){
        this.dao = dao;
    }
    
    public Company simpleFind(String companyName){
        return dao.getByName(companyName);
    }
    
    @Override
    public String toStringTreeOf(Company company) {
        String name = company.getName();
        company = dao.getByNameWithBuild(name);
        return update(company).treeToString(System.lineSeparator());
    }

    @Override
    public void edit(String companyToEdit, String newName, int newEarnings, String newParentCompanyName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Company updateDataOf(Company company) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Company createAndSaveCompany(String name, int earnings) throws Exception{
        Company company = new Company(name, earnings);
        dao.save(company);
        return company;
    }

    @Override
    public Company createAndSaveCompany(String name, int earnings, String parentName)
        throws Exception{
        if(dao.exist(parentName)){
            throw new Exception("This company not exist");
        }else{
            Company company =  new Company(name, earnings, dao.getByName(parentName));
            dao.save(company);
            return company;
        }
    }

    @Override
    public void delete(String companyName) {
        String newPath;
        Company company = dao.getByName(companyName);
        for(Company child : dao.getChildsOf(companyName)){
            newPath = child.getPath().replace(companyName, "");
            dao.updateData(child, "path", newPath);
        }
        dao.delete(company);
    }
    
    @Override
    public void deleteTree(String rootNameToDelete) {
        for (Company company : dao.getChildsOf(rootNameToDelete)) {
            dao.delete(company);
        }
        dao.delete(dao.getByName(rootNameToDelete));
    }

    private Company update(Company company) {
        this.countAndSetTotalEarnings(company);
        return company;
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
    
}
