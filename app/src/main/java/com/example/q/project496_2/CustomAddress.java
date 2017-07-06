package com.example.q.project496_2;

import android.graphics.drawable.Drawable;

/**
 * Created by q on 2017-07-06.
 */

public class CustomAddress {
    private Drawable image;
    private String name;
    private String number;

    public void setAdrsimage (Drawable adrsimage) {
        image = adrsimage;
    }
    public void setAdrsname (String adrsname) {
        name = adrsname;
    }
    public void setAdrsnumber (String adrsnumber) {
        number = adrsnumber;
    }
    public Drawable getAdrsimage () {
        return this.image;
    }
    public String getAdrsname () {
        return this.name;
    }
    public String getAdrsnumber () {
        return this.number;
    }
}
