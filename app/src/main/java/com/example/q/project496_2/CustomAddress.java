package com.example.q.project496_2;


/**
 * Created by q on 2017-07-06.
 */

public class CustomAddress {
    private int image;
    private String name;
    private String number;

    public void setAdrsimage (int adrsimage) {
        image = adrsimage;
    }
    public void setAdrsname (String adrsname) {
        name = adrsname;
    }
    public void setAdrsnumber (String adrsnumber) {
        number = adrsnumber;
    }
    public int getAdrsimage () {
        return this.image;
    }
    public String getAdrsname () {
        return this.name;
    }
    public String getAdrsnumber () {
        return this.number;
    }
}
