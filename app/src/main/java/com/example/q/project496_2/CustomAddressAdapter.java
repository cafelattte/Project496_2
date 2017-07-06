package com.example.q.project496_2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by q on 2017-07-06.
 */

public class CustomAddressAdapter extends BaseAdapter {
    private ArrayList<CustomAddress> customAddressList = new ArrayList<CustomAddress>();

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

        ImageView adrs_image = (ImageView) convertView.findViewById(R.id.adrs_image);
        TextView adrs_name = (TextView) convertView.findViewById(R.id.adrs_name);
        TextView adrs_number = (TextView) convertView.findViewById(R.id.adrs_number);

        CustomAddress customAddress = customAddressList.get(i);

        adrs_image.setImageDrawable(customAddress.getAdrsimage());

        adrs_name.setText(customAddress.getAdrsname());
        adrs_number.setText(customAddress.getAdrsnumber());

        return convertView;
    }

    public void addAddress(Drawable img, String name, String number) {
        CustomAddress address = new CustomAddress();

        address.setAdrsimage(img);
        address.setAdrsname(name);
        address.setAdrsnumber(number);

        customAddressList.add(address);
    }
}
