package com.example.q.project496_2;


import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;

/**
 * Created by q on 2017-07-06.
 */

public class CustomAddress {
    private int image;
    private String name;
    private String number;
    private String Tag;
    private Uri image_2;
    private JSONObject Group = null;

    public void setTag(String tag){
        Tag = tag;
    }
    public String getTag(){
        return Tag;
    }

    public void setAdrsimage (int adrsimage) {
        this.image = adrsimage;
        this.image_2=null;
    }
    public void setGroup(String group_name) {
        JSONArray list= null;
        if (Group == null){
            list = new JSONArray();
        }else{
            try {
                list = Group.getJSONArray("group");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        list.put(group_name);
        try {
            Group.put("group", list);
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public Uri getUri(){
        return image_2;
    }
    public int getImageint(){
        return image;
    }
    public String getAdrsname () {
        return this.name;
    }
    public String getAdrsnumber () {
        return this.number;
    }
    public JSONObject getGroup(){
        return Group;
    }
}
