package com.opp.fangla.terznica.data.entities;

import com.opp.fangla.terznica.data.entities.Person;
import com.opp.fangla.terznica.data.entities.ProductCategory;
import com.opp.fangla.terznica.data.entities.Report;

import java.util.List;

public class AdminTask {

    private List<ProductCategory> productCategories;
    private List<Person> persons;
    private List<Report> reportList;


    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }
}
