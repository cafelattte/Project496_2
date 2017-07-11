package com.example.q.project496_2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class grade_function1 extends Fragment {

    private String name;
    private String major;
    private String student_id;
    private double ex_grade;
    private int credits;
    TextView expect;
    ListView listView;
    JSONArray current;
    listAdapter adapter;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!= null && getActivity() instanceof Tab3_expanded){
            name = ((Tab3_expanded)getActivity()).getStudentName();
            major = ((Tab3_expanded)getActivity()).getMajor();
            student_id = ((Tab3_expanded)getActivity()).getStudent_id();
        }
    }
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_grade_function1,container,false);
        new httpfunc().execute("http://52.78.19.146:8080/lectures/all");

        TextView info = (TextView)view.findViewById(R.id.textView2);
        info.setText("학과: "+major+" 이름: "+name +" 학번: "+student_id);
        adapter = new listAdapter(getContext(),current);
        expect = (TextView)view.findViewById(R.id.textView8);

        listView = (ListView)view.findViewById(R.id.ListView2);

        listView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setting();
    }
    @Override
    public void onPause(){
        super.onPause();
        setting();
    }
    public void setting(){
        if (adapter== null) return;
        credits = adapter.getCredits();
        ex_grade = adapter.average();
        expect.setText("학점 수강 : "+Integer.toString(credits)+"예상 학점 : "+Double.toString(ex_grade));
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
    int credits;
    int length;

    public listAdapter(Context context,JSONArray subject_list){
        mContext= context;
        json = subject_list;
        if( json==null){
            length = 0;
        }else{
            length= json.length();
        }
        grades = new String[length];
        for (int i =0; i<length ; i++){
            grades[i]="A+";
        }
        credits = 0;
    }
    public int getCount(){
        return length;
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
                notifyDataSetChanged();
                new grade_function1().setting();
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
            credits.setText(Integer.toString(cred));
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


