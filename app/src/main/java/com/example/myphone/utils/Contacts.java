package com.example.myphone.utils;

import java.io.Serializable;

public class Contacts implements Serializable {
    String phoneNumber;
    String phoneNumber2;
    String netName;
    String name;
    String email;
    String area;
    String birthDay;
    String address;
    String groupName;
    String blacklist;

    public Contacts() {
    }

    public Contacts(String phoneNumber, String phoneNumber2, String netName, String name, String email, String area, String birthDay, String address, String groupName, String blacklist) {
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.netName = netName;
        this.name = name;
        this.email = email;
        this.area = area;
        this.birthDay = birthDay;
        this.address = address;
        this.groupName = groupName;
        this.blacklist = blacklist;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }
}
