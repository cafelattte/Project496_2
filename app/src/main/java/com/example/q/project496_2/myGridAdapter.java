package com.example.q.project496_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by q on 2017-07-06.
 */

public class myGridAdapter extends BaseAdapter{
    ArrayList<String> thumbsDataList = new ArrayList<String>();
    ArrayList<String> thumbsIdList = new ArrayList<String>();
    Context mContext;

    public myGridAdapter(Context context, ArrayList<String> thumbsData, ArrayList<String> thumbsId){
        mContext = context;
        thumbsDataList = thumbsData;
        thumbsIdList = thumbsId;
    }

    public int getCount(){
        if (thumbsDataList == null){
            return 0;
        }

        return thumbsDataList.size();
    }

    public Object getItem(int position){
        return thumbsDataList.get(position);
    }

    public long getItemId(int position){
        return (long)position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView= inflater.inflate(R.layout.grid_image,parent, false);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
        Glide.with(mContext).load(thumbsDataList.get(position)).into(iv);
        return convertView;
    }


}
