package com.example.myphone.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;

public class MobileNumberUtils {
    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();
    private static final String LANGUAGE = "CN";

    //获取号码运营商
    public static String getNetName(String phoneNumber, int countryCode){ //countryCode 在这里没用到，直接设置成中国
        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
        if (phoneNumber == null || "".equals(phoneNumber)){
            return "";
        }
        try {
            number = phoneNumberUtil.parse(phoneNumber, LANGUAGE);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        //获取的数据为英文，要显示要转换为中文
        String carrierEn = carrierMapper.getNameForNumber(number, Locale.ENGLISH);
        String carrierZh = "";
        switch (carrierEn){
            case "China Mobile":
                carrierZh = "移动";
                break;
            case "China Unicom":
                carrierZh = "联通";
                break;
            case "China Telecom":
                carrierZh = "电信";
                break;
            default:
                carrierZh = "";
        }

        return carrierZh;
    }

    //获取归属地
    public static String getArea(String phoneNumber){
        if (phoneNumber == null || "".equals(phoneNumber)){
            return "";
        }
        Phonenumber.PhoneNumber number =null;
        try {
            number = phoneNumberUtil.parse(phoneNumber, LANGUAGE);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        String area = geocoder.getDescriptionForNumber(number, Locale.CHINA);
        return area;
    }

}
