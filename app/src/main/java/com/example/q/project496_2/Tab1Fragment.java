package com.example.q.project496_2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by q on 2017-07-06.
 */

public class Tab1Fragment extends Fragment{

    CustomAddressAdapter adapter;
    LoginButton loginButton;
    CallbackManager callbackManager;
    JSONObject friends;
    JSONArray friends_array = null;
    private AccessToken mToken;
    GraphRequest request;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new CustomAddressAdapter();
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        mToken=AccessToken.getCurrentAccessToken();

        if (mToken ==null){
            loginButton = (LoginButton)view.findViewById(R.id.login_button);
            loginButton.setReadPermissions("email","public_profile","user_friends");
            loginButton.setFragment(this);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mToken = loginResult.getAccessToken();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getContext(),"User sign in canceled",Toast.LENGTH_LONG).show();
                    Log.d("TAG","Canceled");
                }

                @Override
                public void onError(FacebookException error) {
                    error.printStackTrace();
                }
            });
        }
        Bundle parameters = new Bundle();
        parameters.putString("fields","taggable_friends");
        parameters.putInt("limit",5000);
        request = GraphRequest.newMeRequest(mToken,jsonObjectCallback);
        request.setParameters(parameters);
        request.executeAsync();

        ListView tab1_listview = (ListView) view.findViewById(R.id.tab1_listview);

        adapter.addAddress(R.drawable.pink, "Pink", "010-1234-5678");
        adapter.addAddress(R.drawable.peach, "Peach", "010-9012-3456");
        adapter.addAddress(R.drawable.green, "Green", "010-7890-1234");
        adapter.addAddress(R.drawable.gray, "Gray", "010-1234-5678");
        adapter.addAddress(R.drawable.purple, "Purple", "010-9012-3456");
        adapter.addAddress(R.drawable.red, "Red", "010-7890-1234");
        adapter.addAddress(R.drawable.skyblue, "Skyblue", "010-1234-5678");
        adapter.addAddress(R.drawable.yellow, "Yellow", "010-9012-3456");
        adapter.addAddress(R.drawable.yellow2, "Yellow2", "010-7890-1234");

        tab1_listview.setAdapter(adapter);
        return view;
    }
    GraphRequest.GraphJSONObjectCallback jsonObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            Log.d("TAG","페이스북 로그인 결과"+response.toString());
            try{
                friends = object.getJSONObject("taggable_friends");
                friends_array = friends.getJSONArray("data");
                for (int i = 0; i < friends_array.length(); i++) {
                    JSONObject person = (JSONObject) friends_array.get(i);
                    String names = person.getString("name");
                    String number = "";
                    JSONObject picture = person.getJSONObject("picture");
                    picture = picture.getJSONObject("data");
                    Uri url = Uri.parse(picture.getString("url"));
                    adapter.addAddress(url, names, number);
                }

            }catch(Exception error){
                error.printStackTrace();
            }
        }
    };
    GraphRequest.Callback graphCallback = new GraphRequest.Callback(){
            @Override
            public void onCompleted(GraphResponse response){
                Log.d("TAG","페이스북 로그인 결과"+response.toString());
                JSONObject object = response.getJSONObject();
                try{
                    friends = object.getJSONObject("taggable_friends");
                    friends_array = friends.getJSONArray("data");
                    for (int i = 0; i < friends_array.length(); i++) {
                        JSONObject person = (JSONObject) friends_array.get(i);
                        String names = person.getString("name");
                        String number = "";
                        JSONObject picture = person.getJSONObject("picture");
                        picture = picture.getJSONObject("data");
                        Uri url = Uri.parse(picture.getString("url"));
                        adapter.addAddress(url, names, number);
                    }

                }catch(Exception error){
                    error.printStackTrace();
                }
            }};
}