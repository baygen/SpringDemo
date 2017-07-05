/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.entity.manager;

import com.springdemo.entity.Company;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 *
 * @author Buy
 */
public class DAOManager implements BasicDAO {

    private final MongoOperations template;
    
    public DAOManager(final MongoOperations temlate) {
        this.template = temlate;
    }
    
    @Override
    public void save(Company company) throws Exception{
        if(!exist(company.getName())){
            template.save(company);
        }
        throw new Exception("Already exist");
    }
    
    @Override
    public void update(Company toUpdate, Company newData) {
        
        Query query = new Query(Criteria.where("_id").is(toUpdate.getId()));
        template.updateFirst(query, Update.update("earnings", newData.getEarnings()), Company.class);
        template.updateFirst(query, Update.update("path", newData.getPath()), Company.class);
        template.updateFirst(query, Update.update("name", newData.getName()), Company.class);
    }
    
    @Override
    public void delete(Company company) {
        template.remove(company);
    }

    @Override
    public List<Company> getRoots() {
        Query getRoots = new Query(Criteria.where("path").is(null));
        return template.find(getRoots, Company.class);
    }
    
    public Collection<Company> getAll(){
        return template.findAll(Company.class);
    }
    
    @Override
    public Company getByNameWithBuild(String companyName) {
        Company root = getByName(companyName);
        Collection<Company> rootTree = getChildsOf(companyName);
        return build(root, rootTree);
    }
        
    @Override
    public Company getByName(String companyName) {
        Query findByName = new Query(Criteria.where("name").is(companyName));
        Company comp = template.findOne(findByName, Company.class);
        System.out.println(comp.getName());
        return comp;
    }
    
    public Company getRootOf(String companyName) {
        String currentPath = getByName(companyName).getPath();
        String rootCompanyName = currentPath.substring(
                0, currentPath.indexOf(Company.PATH_SEPARATOR));
        Company root = getByName(rootCompanyName);
        Collection<Company> rootTree = getChildsOf(rootCompanyName);
        return build(root, rootTree);
    }
    
    @Override
    public Collection<Company> getChildsOf(String rootName){
        Query getChildsQuery = new Query(
                Criteria.where("path").regex(rootName + "[.]"));
        return template.find(getChildsQuery, Company.class);
    }
    
    
    
    private Company build(final Company root, final Collection<Company> rootTree) {
        final Map< String, Company> map = new HashMap<>();
        for (final Company document : rootTree) {
            map.put(document.getPath(), document);
        }
        
        for (final Company company : rootTree) {
//            map.put(company.getPath(), company);
            
            final String parentPath = company.getPath().substring(
                    0, company.getPath().lastIndexOf(Company.PATH_SEPARATOR));
            if (parentPath.equals(root.getPath())) {
                root.getChilds().add(company);
            } else {
                final Company parent = map.get(parentPath);
                if (parent != null) {
                    parent.getChilds().add(company);
                }
            }
        }
        return root;
    }

    public void clearDB(){
        template.dropCollection("testCompany");
    }

    @Override
    public boolean exist(String companyName) {
        Query query = new Query(Criteria.where("name").is(companyName));
        return template.exists(query, Company.class);
    }
    
}