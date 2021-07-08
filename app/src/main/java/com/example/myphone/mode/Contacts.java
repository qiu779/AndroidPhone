package com.example.myphone.mode;

import java.io.Serializable;

public class Contacts implements Serializable {
    int id;
    String phoneNumber;
    String phoneNumber2;
    String netName;
    String area;
    String netName2;
    String area2;
    String name;
    String email;
    String birthDay;
    String address;
    String groupName;
    String more;
    int blacklist;

    public Contacts() {
    }

    public Contacts(int id, String phoneNumber, String phoneNumber2, String netName, String area, String netName2, String area2, String name, String email, String birthDay, String address, String groupName, String more, int blacklist) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.netName = netName;
        this.area = area;
        this.netName2 = netName2;
        this.area2 = area2;
        this.name = name;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.groupName = groupName;
        this.more = more;
        this.blacklist = blacklist;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public int getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(int blacklist) {
        this.blacklist = blacklist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getNetName2() {
        return netName2;
    }

    public void setNetName2(String netName2) {
        this.netName2 = netName2;
    }

    public String getArea2() {
        return area2;
    }

    public void setArea2(String area2) {
        this.area2 = area2;
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

}
