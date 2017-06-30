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
public class CompanyNode<A extends BasisOfCompany> implements NodeCore<A> {

    private final A company;
    private CompanyNode<A> parent;
    private final LinkedList<CompanyNode<A>> childs;
    private int totalEarnings;
    private int gapCounter = 0;
    private boolean isRootForToString = false;

    public CompanyNode(A company) {
        this.company = company;
        this.parent = null;
        this.childs = new LinkedList<>();
    }

    @Override
    public void addChild(CompanyNode<A> childCompany) {

        if (!this.childs.contains(childCompany)) {
            childCompany.setParent(this);
        }
    }

    @Override
    public void setParent(CompanyNode<A> parent) {

        if (this.parent != null) {
            this.parent.getChildsList().remove(this);
            this.parent.updateTreeData();
        }

        this.parent = parent;
        parent.getChildsList().add(this);
        parent.updateTreeData();
    }

    @Override
    public LinkedList<CompanyNode<A>> getChildsList() {
        return childs;
    }

    private void updateTreeData() {
        CompanyNode<A> root = parent;
        CompanyNode<A> temporary = this;
        while (root != null) {
            temporary = root;
            root = root.getParent();
        }
        temporary.countAndSetTotalEarnings();
    }

    private int countAndSetTotalEarnings() {

        int childsEarnings = 0;
        for (CompanyNode<A> child : this.getChildsList()) {
            childsEarnings += child.countAndSetTotalEarnings();
        }

        totalEarnings = childsEarnings + getCompanyEarnings();
        return totalEarnings;
    }

    private String getNodeTreeToString() {
        StringBuilder builder = new StringBuilder();

        if (getChildsList().isEmpty()) {
            builder.append(getGaps()).append(company.toString())
                    .append(System.lineSeparator());
        } else {
            builder.append(getGaps()).append(company.toString())
                    .append(" | ").append(String.valueOf(totalEarnings))
                    .append(System.lineSeparator());

            for (CompanyNode<A> child : childs) {
                builder.append(child.getNodeTreeToString());
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        isRootForToString = true;
        return getNodeTreeToString();
    }

    public int getCompanyEarnings() {
        return company.getEstimatedEarnings();
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    @Override
    public CompanyNode<A> getParent() {
        return parent;
    }

    @Override
    public int getDepth() {
        int depth;
        if (getParent() == null) {
            depth = 0;
        } else {
            depth = getParent().getDepth() + 1;
        }
        return depth;
    }

    private String getGaps() {
        StringBuilder gaps = new StringBuilder();
        if (isRootForToString) {
            gapCounter = this.getDepth();
            gaps.append("");
        } else {
            gapCounter = parent.gapCounter;
            for (int i = 0; i < (this.getDepth() - gapCounter); i++) {
                gaps.append("-");
            }
        }
        return gaps.toString();
    }

}
