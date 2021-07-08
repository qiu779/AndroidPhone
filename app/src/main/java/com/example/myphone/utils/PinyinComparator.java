package com.example.myphone.utils;

import com.example.myphone.mode.NameSortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<NameSortModel> {

    @Override
    public int compare(NameSortModel n1, NameSortModel n2) {
        if (n1.getFirstLetter().equals("@") || n2.getFirstLetter().equals("#")){
            return -1;
        }else if (n1.getFirstLetter().equals("#") || n2.getFirstLetter().equals("@")){
            return 1;
        }else {
            return n1.getFirstLetter().compareTo(n2.getFirstLetter());
        }
    }
}
