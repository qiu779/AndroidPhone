package com.example.myphone.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Cn2Spell {

    public static StringBuffer sb = new StringBuffer();

    public static String getPinYinHeadChar(String chines){
        if (chines == null || "".equals(chines)){
            return "";
        }
        sb.setLength(0);        //清空
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : chars) {
            if (c > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getPinYinFirstLetter(String str){
        if (str == null || "".equals(str)){
            return "";
        }
        sb.setLength(0);
        char c = str.charAt(0);
        String[] pinYinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinYinArray != null){
            sb.append(pinYinArray[0].charAt(0));
        }else {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getPinYin(String chines){
        if (chines == null || "".equals(chines)){
            return "";
        }
        sb.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
