package com.example.q.project496_2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class grade_function1 extends Fragment {

    private String name;
    private String major;
    private String student_id;
    listAdapter adapter;
    JSONArray current;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!= null && getActivity() instanceof Tab3_expanded){
            name = ((Tab3_expanded)getActivity()).getStudentName();
            major = ((Tab3_expanded)getActivity()).getMajor();
            student_id = ((Tab3_expanded)getActivity()).getStudent_id();
        }
    }

    public String getRespond(String params) {
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null ;
        try {
            url = new URL(params);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Not usable internet address";
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);


            int resCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resCode) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\r\n");
                }
                return reader.toString();
            } else {
                return connection.getResponseCode() + "-" + connection.getResponseMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connection != null) {
            connection.disconnect();
        }
        return reader.toString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_grade_function1,container,false);
        String res = getRespond("http://52.78.19.146:8080/lectures/all");

        try {
            current = new JSONArray(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        TextView info = (TextView)view.findViewById(R.id.textView2);
        info.setText("학과: "+major+" 이름: "+name +" 학번: "+student_id);

        TextView expect = (TextView)view.findViewById(R.id.textView8);

        ListView listView = (ListView)view.findViewById(R.id.ListView2);

        adapter = new listAdapter(getContext(),current);
        listView.setAdapter(adapter);
        double ex_grade= adapter.average();
        int credits = adapter.getCredits();
        expect.setText("학점 수강 : "+credits+"예상 학점 : "+ex_grade);

        return view;
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
class listAdapter extends BaseAdapter {
    private Context mContext;
    private JSONArray json;
    private String[] grades;
    int length;
    int credits;

    public listAdapter(Context context,JSONArray subject_list){
        mContext= context;
        json = subject_list;
        length =0;
        if (json== null){
            length =0;
        }else{
            length = json.length();
        }
        grades = new String[length];
        credits = 0;
    }
    public int getCount(){
        if (json ==null){
            return 0;
        }
        return json.length();
    }
    public int getCredits(){
        return credits;
    }
    public Object getItem(int i){
        try {
            return json.get(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int i){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView== null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grade_list, parent, false);
        }
        TextView subject_name = (TextView)convertView.findViewById(R.id.textView3);
        TextView credits =(TextView) convertView.findViewById(R.id.textView4);
        final Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner2);
        grades[position] = (String)spinner.getSelectedItem();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String str = (String)spinner.getSelectedItem();
                grades[position]= str;
            }
            public void onNothingSelected(AdapterView<?> arg0){
                Log.d("Nothing","Selected");
            }
        });
        try {
            JSONObject temp = (JSONObject) json.get(position);
            String sn = temp.getString("Course_title");
            int cred = temp.getInt("Credits");
            subject_name.setText(sn);
            credits.setText(cred);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    public double average(){
        double average= 0;
        int credits = 0;
        double grade = 0;
        for (int i =0; i<length; i++){
            try {
                JSONObject item = (JSONObject) json.get(i);
                grade = grade(grades[i]);
                average += grade*item.getInt("Credits");
                credits += item.getInt("Credits");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.credits = credits;
        return average/credits;
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


