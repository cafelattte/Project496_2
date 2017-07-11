package com.example.q.project496_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by q on 2017-07-06.
 */

public class Tab3Fragment extends Fragment{
    private static final String TAG = "tab3_Fragment";
    private Button btnTEST;
    private EditText name;
    private EditText number;
    private Spinner spinner;
    private String major;
    private EditText waitText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container , false);
        btnTEST = (Button) view.findViewById(R.id.btnTest3);

        name = (EditText) view.findViewById(R.id.editText);
        number = (EditText)view.findViewById(R.id.editText2);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                major = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent){
                major = null;
            }
        });

        waitText = (EditText) view.findViewById(R.id.waitText);
        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tab3_expanded.class);
                String student_name = name.getText().toString();
                String student_number = number.getText().toString();
                intent.putExtra("name",student_name);
                intent.putExtra("student_id",student_number);
                intent.putExtra("major",major);
                new HttpMakeDB().execute("http://52.78.19.146:8080/start");
                startActivity(intent);
            }
        });
        return view;
    }
    private class HttpMakeDB extends AsyncTask<String, Void, String> {
        private boolean sendingDBcompleted = false;

        @Override
        protected void onPreExecute() {
            waitText.setText("making DB...");
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            HttpURLConnection connection = null;
            String res_msg = null;

            try {
//                JSONObject param = new JSONObject();
//                Reader in = getContext().getResources().openRawResource(R.raw.current);
                InputStream in = getContext().getResources().openRawResource(R.raw.current);
                JSONArray job;
                parse ap = new parse();
                job = ap.parser2(ap.parse1(in));
                String body = job.toString();
                System.out.println(body);

                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.flush();
                os.close();

                int resCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == resCode) {
                    res_msg = getTextFrom(connection.getInputStream());
                    if ("{result: 1}".equals(res_msg)) {
                        sendingDBcompleted = true;
                    } else if ("{result: 0".equals(res_msg)){
                        sendingDBcompleted = false;
                    }
                } else {
                    res_msg = connection.getResponseCode() + "-" + connection.getResponseMessage();
                    sendingDBcompleted = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.sendingDBcompleted = false;
            }

            if (connection != null) {
                connection.disconnect();
            }
            return res_msg;
        }

        private String getTextFrom(InputStream in) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(in, "euc-kr"));
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

        public class parse {
            public ArrayList<String[]> parse1(InputStream in) {
                ArrayList<String> list = new ArrayList<String>();
                try{
                    BufferedReader input_br = null;
                    input_br = new BufferedReader(new InputStreamReader(in, "euc-kr"));
                    while (true) {
                        String line = input_br.readLine();
                        if (line == null) break;
                        list.add(line);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }

                ArrayList<String[]> subjects_w_grade;
                subjects_w_grade=parser_w_grade(list);//이번학기를 제외한 성적들.[no, 학과, 교과목, 과목번호, 분반, 구분, 교과목명, 학점, au, 재수강, 성적, 영문교과목명]

                return subjects_w_grade;
            }

            public ArrayList<String[]> parser_w_grade(ArrayList<String> text) {
                ArrayList<String[]> result = new ArrayList<String[]>();
                for (int i=0; i<text.size();i++) {
                    StringTokenizer tokens = new StringTokenizer(text.get(i),"\t");
                    int count = tokens.countTokens();

                    String[] subject = new String[count];

                    for (int j=0; j< count ; j++) {
                        subject[j] = tokens.nextToken();}

                    result.add(subject);
                }
                return result;
            }

            public JSONArray parser2(ArrayList<String[]> text){
                JSONArray result = new JSONArray();
                for (int i =0; i< text.size(); i++) {
                    JSONObject object = new JSONObject();
                    int size = text.get(i).length;//11이면 분반 없음, 12면 분반 있음.
                    String[] list = text.get(i);
                    String Grades;
                    String Course_type;
                    String Course_title;
                    String Repeat;
                    String Course_no;
                    int AU;
                    int Credits;
                    if (size ==11) {//분반 없는 경우
                        Course_no = list[4];
                        Course_type = list[5];
                        Course_title = list[6];
                        Credits = Integer.parseInt(list[8]);
                        AU = Integer.parseInt(list[7]);
                        Grades = "null";
                        Repeat = list[10];
                    }
                    else {
                        Course_no = list[5];
                        Course_type = list[6];
                        Course_title = list[7];
                        Credits = Integer.parseInt(list[9]);
                        AU = Integer.parseInt(list[8]);
                        Grades = "null";
                        Repeat = list[11];
                    }
                    String Depart = list[2];
                    String Code = list[3];
                    try {
                        object.put("Course_no", Course_no);
                        object.put("Depart",Depart);
                        object.put("Code",Code);
                        object.put("Grades",Grades);
                        object.put("Course_type",Course_type);
                        object.put("Course_title",Course_title);
                        object.put("Repeat",Repeat);
                        object.put("Credits",Credits);
                        object.put("AU", AU);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    result.put(object);
                }
                return result;
            }
        }


        @Override
        public void onPostExecute(String result) {
            if ("{result: 0}".equals(result)) {
                Toast.makeText(getContext(), "Create DB Error", Toast.LENGTH_SHORT).show();
                return;
            } else if (!this.sendingDBcompleted) {
                Toast.makeText(getContext(), "Sending connecting DB Error", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "Complete create DB", Toast.LENGTH_SHORT).show();
        }
    }

}
