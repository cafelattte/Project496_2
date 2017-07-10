package com.example.q.project496_2;


import android.net.Uri;

/**
 * Created by q on 2017-07-06.
 */

public class CustomAddress {
    private int image;
    private String name;
    private String number;
    private Uri image_2;

    public void setAdrsimage (int adrsimage) {
        this.image = adrsimage;
        this.image_2=null;
    }
    public void setAdrsimage(Uri image){
        this.image_2=image;
        this.image=-1;
    }
    public void setAdrsname (String adrsname) {
        name = adrsname;
    }
    public void setAdrsnumber (String adrsnumber) {
        number = adrsnumber;
    }
    public Object getAdrsimage () {
        if (image==-1){
            return image_2;
        }return image;
    }
    public String getAdrsname () {
        return this.name;
    }
    public String getAdrsnumber () {
        return this.number;
    }
}
