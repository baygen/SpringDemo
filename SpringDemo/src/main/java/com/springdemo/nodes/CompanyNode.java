/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.BasisOfCompany;
import com.springdemo.entity.Company;
import java.util.LinkedList;

/**
 *
 * @author Buy
 * @param <A>
 */
public class CompanyNode<A extends BasisOfCompany> {
    
    private final A company;
    private CompanyNode<A> parent;
    private final LinkedList<CompanyNode<A>> childs;
    private int totalEarnings;
    
    public CompanyNode(A company){
        this.company = company;
        this.parent = null;
        this.childs = new LinkedList<>();
    }
    
    public void addChild(CompanyNode<A> childCompany){
        
        if(!this.childs.contains(childCompany))
            childCompany.setParent(this);
    }

    public void setParent(CompanyNode<A> parent) {
        
        if(this.parent != null){
            this.parent.getChildsList().remove(this);
            updateTreeData(this.parent);
        }
        
        this.parent = parent;
        parent.getChildsList().add(this);
        updateTreeData(parent);
    }

    public LinkedList<CompanyNode<A>> getChildsList() {
        return childs;
    }

    public void updateTreeData(CompanyNode<A> companyToUpdate) {
        CompanyNode<A> root = companyToUpdate.getParent();
        while(root!=null){
            root=root.getParent();
            companyToUpdate=companyToUpdate.getParent();
        }
        companyToUpdate.countTotalEarnings();
    }

    public int countTotalEarnings() {
        
        int childsEarnings = 0;
        for(CompanyNode<A> child:this.getChildsList()){
            childsEarnings += child.countTotalEarnings();
        }
        
        this.totalEarnings = childsEarnings + this.getCompanyEarnings();
        return totalEarnings;
    }

    
    public int getCompanyEarnings(){
        return company.getEstimatedEarnings();
    }
    
    public int getTotalEarnings(){
        return totalEarnings;
    }
    
    public CompanyNode<A> getParent() {
        return this.parent;
    }
    
    public int getDepth(){
        int depth;
        if(getParent()==null)
            depth = 0;
        depth = getParent().getDepth()+1;
        return depth;
    }
    
}
