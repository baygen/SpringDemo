/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.Company;

/**
 *
 * @author Buy
 */
public interface ManagerCRUDExtended extends ManagerCRUD{
    public void edit(String companyToEdit, String newName, int newEarnings, String newParentCompanyName);
    public void deleteTree(String rootNameToDelete);
    public Company getWholeTreeOf(String companyName);
    public String toStringTreeOf(Company company);
    public Company updateDataOf(Company company);
}
