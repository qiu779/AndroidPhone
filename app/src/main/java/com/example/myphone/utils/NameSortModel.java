package com.example.myphone.utils;

class NameSortModel {
    String name;
    String firstLetter;

    public NameSortModel() {
    }

    public NameSortModel(String name, String firstLetter) {
        this.name = name;
        this.firstLetter = firstLetter;
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
