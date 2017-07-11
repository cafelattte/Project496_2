package com.example.q.project496_2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class grade_function3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_grade_function3,container,false);

        TextView current_grade = (TextView)view.findViewById(R.id.textView5);
        TextView re_count = (TextView)view.findViewById(R.id.textView6);
        current_grade.setText("현재 학점 :");
        re_count.setText("재수강 카운트 :");

        return view;
    }
}
