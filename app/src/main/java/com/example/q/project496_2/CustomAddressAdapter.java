package com.example.q.project496_2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by q on 2017-07-06.
 */

public class CustomAddressAdapter extends BaseAdapter {
    private ArrayList<CustomAddress> customAddressList = new ArrayList<CustomAddress>();
    private ArrayList<CustomAddress> facebook = new ArrayList<CustomAddress>();
    private ArrayList<CustomAddress> contacts = new ArrayList<CustomAddress>();

    public CustomAddressAdapter() {
    }

    @Override
    public int getCount() {
        return customAddressList.size();
    }

    @Override
    public Object getItem(int i) {
        return customAddressList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int i = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customaddress, parent, false);
        }
        ImageView facebook_iv = (ImageView)convertView.findViewById(R.id.adrs_pbimg);
        ImageView contact = (ImageView)convertView.findViewById(R.id.adrs_fbimg);
        ImageView adrs_image = (ImageView) convertView.findViewById(R.id.adrs_image);
        TextView adrs_name = (TextView) convertView.findViewById(R.id.adrs_name);
        TextView adrs_number = (TextView) convertView.findViewById(R.id.adrs_number);
        CustomAddress ad = customAddressList.get(position);
        if (contacts.contains(ad)){
            contact.setVisibility(View.VISIBLE);
        }else{
            contact.setVisibility(View.INVISIBLE);
        }
        if (facebook.contains(ad)){
            facebook_iv.setVisibility(View.VISIBLE);
        }else{
            facebook_iv.setVisibility(View.INVISIBLE);
        }


        CustomAddress customAddress = customAddressList.get(i);

        Glide.with(getApplicationContext()).load(customAddress.getAdrsimage()).into(adrs_image);

        adrs_name.setText(customAddress.getAdrsname());
        adrs_number.setText(customAddress.getAdrsnumber());

        return convertView;
    }

    public void addAddress(int img, String name, String number, String tag) {
        CustomAddress address = new CustomAddress();

        address.setAdrsimage(img);
        address.setAdrsname(name);
        address.setAdrsnumber(number);
        address.setTag(tag);
        if (tag =="facebook"){
            facebook.add(address);
        }else if(tag =="contacts"){
            contacts.add(address);
        }
        customAddressList.add(address);
    }
    public void addAddress(Uri uri, String name, String number,String tag){
        CustomAddress address = new CustomAddress();

        address.setAdrsimage(uri);
        address.setAdrsname(name);
        address.setAdrsnumber(number);
        address.setTag(tag);
        if (tag =="facebook"){
            facebook.add(address);
        }else if(tag =="contacts"){
            contacts.add(address);
        }
        customAddressList.add(address);
    }

    public void check_Same(){
        Log.d("TAG","ENter is okay");
        for (int i =0 ; i<contacts.size(); i++){
            for (int j=facebook.size()-1; j>=0; j--){
                CustomAddress ad1 = contacts.get(i);
                CustomAddress ad2 = facebook.get(j);
                if (ad1.getAdrsname().trim().equals(ad2.getAdrsname().trim())){
                    ad1.setAdrsimage(ad2.getUri());
                    facebook.set(j,ad1);
                    facebook.remove(ad2);
                    customAddressList.remove(ad2);
                }
            }
        }
        notifyDataSetChanged();
    }

}
