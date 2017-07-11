package com.example.q.project496_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by q on 2017-07-06.
 */

public class Tab2Fragment extends Fragment{
    private static final String TAG = "tab2_Fragment";
    private static final int TAKE_PHOTO = 2;
    private static final int MY_PERMISSION = 1;

    private ArrayList<String> thumbsIDsList;
    private ArrayList<String> thumbsDatasList;
    myGridAdapter gridAdapter;

    private AccessToken mToken;

    JSONObject friends;

    private Button btnTEST;
    private GridView gridView;
    private TextView text;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container , false);

        mToken = AccessToken.getCurrentAccessToken();

        btnTEST = (Button)view.findViewById(R.id.btnTest2);
        gridView = (GridView)view.findViewById(R.id.grid_view);
        text = (TextView)view.findViewById(R.id.textView);


        thumbsIDsList=new ArrayList<String>();
        thumbsDatasList =new ArrayList<String>();

        getThumbInfo(thumbsIDsList,thumbsDatasList);
        gridAdapter = new myGridAdapter(getContext(),thumbsDatasList, thumbsIDsList);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTakePhotoAction();
            }
        });
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l_position){
                Intent intent = new Intent(getContext(),Image_activity.class);
                intent.putExtra("img",thumbsDatasList.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    public void doTakePhotoAction(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
    }

    private void getThumbInfo(ArrayList<String> thumbsIds, ArrayList<String> thumbsDatas){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION);
        } else {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor imageCursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
            if (imageCursor != null && imageCursor.moveToFirst()) {
                String title;
                String thumbsID;
                String thumbsImageID;
                String thumbsData;
                String data;
                String imgSize;

                int thumbsDatacol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int num = 0;
                do {
                    thumbsData = imageCursor.getString(thumbsDatacol);
                    num++;
                    if (thumbsData != null) {
                        thumbsDatas.add(thumbsData);
                    }
                } while (imageCursor.moveToNext());
            }
            imageCursor.close();
            return;
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case MY_PERMISSION:{
                if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION);}}
            case TAKE_PHOTO:{
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    doTakePhotoAction();
                }else{
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},TAKE_PHOTO);
                }
            }
        }
    }
}