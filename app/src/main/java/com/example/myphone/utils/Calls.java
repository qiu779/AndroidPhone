package com.example.myphone.utils;

import java.io.Serializable;
import java.util.Date;

public class Calls implements Serializable {
    String name;            //用户名（陌生人电话则无值）
    String phoneNumber;     //手机号
    Long date;              //通话日期
    int type;               //通话类型
    String area;            //归属地
    String category;        //电话类型（电话不为联系人时）
    String callDuration;    //通话时长

    public Calls(){

    }

    public Calls(String name, String phoneNumber, Long date, int type, String area, String category, String callDuration) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.type = type;
        this.area = area;
        this.category = category;
        this.callDuration = callDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
}
