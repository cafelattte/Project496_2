package com.example.q.project496_2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class grade_function1 extends Fragment {

    private String name;
    private String major;
    private String student_id;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_grade_function1,container,false);

        TextView info = (TextView)view.findViewById(R.id.textView2);
        info.setText("학과: "+major+" 이름: "+name +" 학번: "+student_id);

        ListView listView = (ListView)view.findViewById(R.id.ListView2);

        adapter = new listAdapter(getContext());
        listView.setAdapter(adapter);

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

    public listAdapter(Context context){
        mContext= context;
    }
    public int getCount(){
        return 0;
    }
    public Object getItem(int i){
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
        Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner2);
        
        return convertView;
    }

}


