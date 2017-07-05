/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.entity.manager;

import com.springdemo.entity.Company;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Buy
 */
public interface BasicDAO {

    void save  (Company company)throws Exception;
    void delete(Company company);
    List<Company> getRoots();
    public Company getByName(String companyName);
    Company getByNameWithBuild(String name);
    void update(Company toUpdate, Company newData);
    boolean exist(String companyName);
    Collection<Company> getChildsOf(String companyName);
}
