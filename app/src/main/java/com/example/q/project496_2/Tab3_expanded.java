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
        tab.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new grade_function1(), "학점계산");
        adapter.addFragment(new grade_function2(),"졸업");
        adapter.addFragment(new grade_function3(),"재수강");
        viewPager.setAdapter(adapter);
    }

    public String getMajor(){
        return major;
    }
    public String getStudentName(){
        return name;
    }
    public String getStudent_id(){
        return student_id;
    }

}
