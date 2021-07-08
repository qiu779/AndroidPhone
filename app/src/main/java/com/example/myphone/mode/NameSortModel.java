package com.example.myphone.mode;

public class NameSortModel {
    int id;
    String name;
    String phonenumber;
    String phonenumber2;
    String firstLetter;

    public NameSortModel() {
    }

    public NameSortModel(int id, String name, String phonenumber, String phonenumber2, String firstLetter) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.phonenumber2 = phonenumber2;
        this.firstLetter = firstLetter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
