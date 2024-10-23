package com.s333329.mappe2;

public class Birthday {

    private long id;
    private String name;
    private String number;
    private String date;

    public Birthday(long id, String name, String number, String date) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.date = date;
    }
    public Birthday(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
