package com.example.senatorojonla.ubhs.Model;


import java.io.Serializable;

/**
 * Created by PROG. TOBI on 12-Feb-18.
 */

public class HospitalModel {
    String busName, busCategory, busModel, busPrice, busReg, id;

    public String getBusName() {
        return busName;
    }

    public String getId() {
        return id;
    }

    public String getBusReg() {
        return busReg;
    }

    public void setBusReg(String busReg){
        this.busReg=busReg;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusCategory() {
        return busCategory;
    }

    public void setBusCategory(String busCategory) {
        this.busCategory = busCategory;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public String getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(String busPrice) {
        this.busPrice = busPrice;
    }

    public HospitalModel(String busname, String buscat, String busmodel, String busprice, String regno, String id){
        this.busName = busname;
        this.busCategory = buscat;
        this.busModel = busmodel;
        this.busPrice = busprice;
        this.busReg= regno;
        this.id = id;

    }
}
