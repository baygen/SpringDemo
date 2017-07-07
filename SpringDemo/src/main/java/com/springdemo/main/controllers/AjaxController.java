package com.springdemo.main.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.springdemo.main.model.Views;
import com.springdemo.main.model.AjaxResponseBody;
import com.springdemo.main.model.SearchCriteria;
import com.springdemo.manager.CompanysManager;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AjaxController {

    @Autowired
    private CompanysManager manager;
    AjaxResponseBody result;

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/create")
    public AjaxResponseBody createCompany(@RequestBody SearchCriteria input){
        result = new AjaxResponseBody();
        try{
        checkInputAndCreateCompany(input);
        result.setMsg(manager.getAllCompanyToString());
        }catch(Exception e){
            result.setCode(e.getMessage());
            result.setMsg(manager.getAllCompanyToString());
        }
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/edit")
    public AjaxResponseBody edit(@RequestBody SearchCriteria input) throws Exception {
        result = new AjaxResponseBody();
        String companyToEdit = input.getCompanyName();
        String newParentname = input.getParentName();
        String newName = input.getNewName();
        int newearns = input.getEarnings();
        
        System.out.println(input);
                
        if(!companyToEdit.equals("")){
            if(!newParentname.equals(""))
                manager.editParent(companyToEdit, newParentname);
            if(newearns!=0)
                manager.editEarnings(companyToEdit, newearns);
            if(!(newName.trim().equals("")))
                manager.rename(companyToEdit, newName);
        result.setCode("Ok!");
        }else{
             result.setCode("Bad input data!");
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete")
    public AjaxResponseBody deleteOne(@RequestBody SearchCriteria input) throws Exception {
        result = new AjaxResponseBody();
        if (!input.getCompanyName().trim().equals("")) {
            manager.delete(input.getCompanyName());
            result.setCode("Ok!");
            result.setMsg(manager.getAllCompanyToString());
        }else{
            result.setCode("Wrong company name");
            result.setMsg(manager.getAllCompanyToString());
        }
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete/tree")
    public AjaxResponseBody deleteTree(@RequestBody SearchCriteria input) throws Exception {
        if (!input.getCompanyName().equals("")){
            manager.deleteTree(input.getCompanyName());
            result.setCode("Deleted!");
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }
    
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/all")
    public AjaxResponseBody getAllCompany(@RequestBody SearchCriteria input){
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }

    private void checkInputAndCreateCompany(SearchCriteria input) throws Exception {
        String name = input.getCompanyName();
        int earns = input.getEarnings();
        String parentName = input.getParentName();

        if (!name.equals("") && !parentName.equals("")) {
            manager.createAndSaveCompany(name, earns, parentName);
        } else if (!name.trim().equals("")) {
            manager.createAndSaveCompany(name, earns);
        }
    }

}
