package com.example.q.project496_2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class grade_function2 extends Fragment {
    JSONArray current= null;
    TextView uptext;
    TextView downtext;

    private class httpfunc extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            HttpURLConnection connection = null;
            String response = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Not usable internet address";
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                if (connection == null) {
                    return "Cannot connect";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Cannot connect by IO exception";
            }
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
                return "Wrong HTTP request method";
            }
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);

            try {
                int resCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == resCode) {
                    response = getTextFrom(connection.getInputStream());
                } else {
                    response = connection.getResponseCode() + "-" + connection.getResponseMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.disconnect();

            try {
                JSONArray array = new JSONArray(response);
                current = array;

            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }

        private String getTextFrom(InputStream in) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(in));

                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_grade_function2,container,false);
        new httpfunc().execute("http://52.78.19.146:8080/previous/all");

        Button btn1 = (Button) view.findViewById(R.id.btn1);
        Button btn2 = (Button) view.findViewById(R.id.btn2);
        Button btn3 = (Button) view.findViewById(R.id.btn3);
        Button btn4 = (Button) view.findViewById(R.id.btn4);
        uptext = (TextView) view.findViewById(R.id.uptext);
        downtext = (TextView) view.findViewById(R.id.downtext);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uptext.setText("필수");
                downtext.setText("선택");

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uptext.setText("필수");
                downtext.setText("선택");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uptext.setText("교양");
                downtext.setText("영어");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uptext.setText("운동");
                downtext.setText("리더십");
            }
        });

        return view;
    }

}