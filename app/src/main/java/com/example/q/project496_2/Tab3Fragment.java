package com.example.q.project496_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tab3_expanded.class);
                String student_name = name.getText().toString();
                String student_number = number.getText().toString();
                intent.putExtra("name",student_name);
                intent.putExtra("student_id",student_number);
                intent.putExtra("major",major);
                startActivity(intent);
            }
        });
        return view;
    }


}
