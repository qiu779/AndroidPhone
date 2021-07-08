package com.example.myphone.mode;

import java.io.Serializable;

public class Calls implements Serializable,Comparable<Calls> {
    int id;
    String name;            //用户名（陌生人电话则无值）
    String phoneNumber;     //手机号
    Long date;              //通话日期
    int type;               //通话类型
    String area;            //归属地
    String netName;        //电话类型（电话不为联系人时）  暂时先作为运营商
    String callDuration;    //通话时长
    int contact_id;

    public Calls(){
        id = 0;
        name = "";
        phoneNumber = "";
        date = Long.valueOf(0);
        type = 0;
        area = "";
        netName = "";
        callDuration = "";
        contact_id = 0;
    }

    public Calls(int id, String name, String phoneNumber, Long date, int type, String area, String netName, String callDuration, int contact_id) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.type = type;
        this.area = area;
        this.netName = netName;
        this.callDuration = callDuration;
        this.contact_id = contact_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
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

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }


    @Override
    public int compareTo(Calls calls) {
        int temp = calls.date.compareTo(this.date);
//        if (temp > 0)
//            return -1;
//        else if (temp == 0)
//            return 0;
//        else
//            return 1;
        return temp;
    }
}
