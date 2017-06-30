/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springdemo.nodes;

import com.springdemo.entity.BasisOfCompany;
import java.util.LinkedList;

/**
 *
 * @author Buy
 */
public interface NodeCore<A extends BasisOfCompany> {

    void addChild(CompanyNode<A> childCompany);

    LinkedList<CompanyNode<A>> getChildsList();

    int getDepth();

    CompanyNode<A> getParent();

    void setParent(CompanyNode<A> parent);
    
}
