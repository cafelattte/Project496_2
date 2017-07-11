package com.example.q.project496_2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class grade_function3 extends Fragment {

    private int count =0;
    private int total_count =3;
    private JSONArray prev = null;

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
                count = array.length();

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
    private class httpfunc2 extends AsyncTask<String, Void, String> {
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
                prev = array;

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
        View view = inflater.inflate(R.layout.fragment_grade_function3,container,false);
        new httpfunc().execute("http://52.78.19.146:8080/previous/count");
        new httpfunc2().execute("http://52.78.19.146:8080/previous/all");

        TextView current_grade = (TextView)view.findViewById(R.id.textView5);
        TextView re_count = (TextView)view.findViewById(R.id.textView6);
        ListView listView = (ListView)view.findViewById(R.id.list);

        double grade = average_grade();

        current_grade.setText("현재 학점 :"+Double.toString(grade));

        re_count.setText("재수강 카운트 :"+ Integer.toString(total_count-count));

        return view;
    }
    public double average_grade(){
        if (prev!= null){
            int len = prev.length();
            double temp =0;
            int overall_credit = 0;
            for (int i =0 ;i< len ; i++){
                try {
                    JSONObject ob = (JSONObject) prev.get(i);
                    String letter= ob.getString("Grades");
                    int credit = ob.getInt("Credits");
                    if (letter.equals("S") || letter.equals("W")) continue;
                    temp += credit *grade(letter);
                    overall_credit += credit;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return temp/overall_credit;
        }
        return 0;
    }
    private double grade(String Grade){
        double my_grade = 0;
        switch(Grade){
            case "A+": my_grade= 4.3;break;
            case "A0":my_grade = 4.0;break;
            case "A-":my_grade = 3.7;break;
            case "B+":my_grade = 3.3;break;
            case "B0":my_grade = 3.0;break;
            case "B-":my_grade = 2.7;break;
            case "C+":my_grade = 2.3;break;
            case "C0":my_grade = 2.0;break;
            case "C-":my_grade = 1.7;break;
            case "D+":my_grade = 1.3;break;
            case "D0":my_grade = 1.0;break;
            case "D-":my_grade = 0.7;break;
            default: my_grade=0;break;
        }
        return my_grade;
    }
}

