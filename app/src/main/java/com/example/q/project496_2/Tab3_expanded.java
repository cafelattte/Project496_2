package com.example.q.project496_2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by q on 2017-07-10.
 */

public class Tab3_expanded extends AppCompatActivity {

    private ViewPager mViewPager;
    private String major;
    private String name;
    private String student_id;
    private EditText waitText;
    private Button btnTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_activity);

        Intent prev_intent = getIntent();
        major = prev_intent.getStringExtra("major");
        name = prev_intent.getStringExtra("name");
        student_id = prev_intent.getStringExtra("student_id");
        mViewPager = (ViewPager) findViewById(R.id.container2);
        setupViewPager(mViewPager);

        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);}
/*
        waitText = (EditText) findViewById(R.id.waitText);
        btnTest3 = (Button) findViewById(R.id.btnTest3);
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpMakeDB().execute("http://52.78.19.146:8080/start");
            }
        });*/
    /*
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
                    url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Content-Type","application/json");
                
                    connection.setDoOutput(true);

                    OutputStream os = connection.getOutputStream();
                    InputStream is = getApplicationContext().getResources().openRawResource(R.raw.currrent);

                    JSONArray job ;
                    parse ap = new parse();
                    job = ap.parser2(ap.parse("currrent.txt"));

                    os.write(job.toString().getBytes());
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
                if ("{result: 0}".equals(result)) {
                    Toast.makeText(Tab3_expanded.this, "Create DB Error", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!this.sendingDBcompleted) {
                    Toast.makeText(Tab3_expanded.this, "Sending connecting DB Error", Toast.LENGTH_SHORT).show();
                    return;
                }
            Toast.makeText(Tab3_expanded.this, "Complete create DB", Toast.LENGTH_SHORT).show();
            }
        }
    */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new grade_function1(), "학점계산");
        adapter.addFragment(new grade_function2(), "졸업");
        adapter.addFragment(new grade_function3(), "재수강");
        viewPager.setAdapter(adapter);
    }

    public String getMajor() {
        return major;
    }

    public String getStudentName() {
        return name;
    }

    public String getStudent_id() {
        return student_id;
    }
}
