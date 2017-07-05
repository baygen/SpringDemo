/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.Company;
import java.util.List;

/**
 *
 * @author Buy
 */
public interface ManagerCRUD  {
    
    public Company createAndSaveCompany(String name, int earnings) throws Exception;
    public Company createAndSaveCompany(String name, int earnings, String parentName)throws Exception;
    
    public void delete(String companyName);
    
}
