package com.example.q.project496_2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by q on 2017-07-06.
 */

public class Tab1Fragment extends Fragment{

    CustomAddressAdapter adapter;

    String personId;
    JSONObject friends;
    JSONArray friends_array = null;
    private AccessToken mToken;
    GraphRequest request;
    GraphResponse lastResponse;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new CustomAddressAdapter();
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        mToken=AccessToken.getCurrentAccessToken();
        Bundle parameters = new Bundle();
        parameters.putString("fields","id, name,email,gender, picture,taggable_friends");//id, name, email, gender, picture of the user.
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
        //sendRequest();

        tab1_listview.setAdapter(adapter);
        return view;
    }
    GraphRequest.GraphJSONObjectCallback jsonObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            Log.d("TAG","페이스북 로그인 결과"+response.toString());
            setResponse(response);
            try{
                String email = object.getString("email");
                String name = object.getString("name");
                personId = object.getString("id");
                String gender = object.getString("gender");
                String image = object.getString("picture");
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
                if (!friends.isNull("paging")){
                    JSONObject paging = friends.getJSONObject("paging");
                    if (!paging.isNull("next")){
                        sendRequest();
                    }
                }

                Log.d("TAG","페이스북 이메일-> "+email);
                Log.d("TAG","페이스북 이름-> "+name);
                Log.d("TAG","페이스북 성별-> "+gender);
                Log.d("TAG","페이스북 이미지-> "+ image);

            }catch(Exception error){
                error.printStackTrace();
            }
        }
    };

    public void setResponse(GraphResponse response){
        lastResponse = response;
    }

    public void sendRequest(){
        if (lastResponse!= null){
            GraphRequest res = lastResponse.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
            res.setAccessToken(mToken);
            res.setCallback(graphCallback);
            res.executeAsync();}
    }

    GraphRequest.Callback graphCallback = new GraphRequest.Callback(){
            @Override
            public void onCompleted(GraphResponse response){
                try {
                    JSONObject object = response.getJSONObject();
                    String email = object.getString("email");
                    String name = object.getString("name");
                    personId = object.getString("id");
                    String gender = object.getString("gender");
                    String image = object.getString("picture");
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
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }};
}