package com.daiict.internship.Sahara.UserDashboard;

import java.util.ArrayList;

public class AddItemDataClass {

    private String itemname;
    private String noofpersons;
    private String weight;
    private String spoilage;

    public AddItemDataClass(String itemname, String noofpersons, String weight, String spoilage) {
        this.itemname = itemname;
        this.noofpersons = noofpersons;
        this.weight = weight;
        this.spoilage = spoilage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getNoofpersons() {
        return noofpersons;
    }

    public void setNoofpersons(String noofpersons) {
        this.noofpersons = noofpersons;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpoilage() {
        return spoilage;
    }

    public void setSpoilage(String spoilage) {
        this.spoilage = spoilage;
    }
}
