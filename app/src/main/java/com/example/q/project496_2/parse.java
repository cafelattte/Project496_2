package com.example.q.project496_2;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by q on 2017-07-11.
 */

public class parse {
    public parse(){

    }
    public ArrayList<String[]> parse1(InputStream in) {
        ArrayList<String> list = new ArrayList<String>();
        try{
            BufferedReader input_br = null;
            input_br = new BufferedReader(new InputStreamReader(in));
            while (true) {
                String line = input_br.readLine();
                if (line==null)break;
                list.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        ArrayList<String> subjects = new ArrayList<String>();
        ArrayList<String[]> subjects_w_grade;

        JSONArray bef_Sem = null;//그전 학기의 과목들
        JSONArray now_Sem = null;// 현재 학기의 과목들
/*
        BufferedReader bf  = null;
        try {
            bf = new BufferedReader(new FileReader(grade));//이때까지의 성적들을 보여줌
            String line;
            while((line = bf.readLine())!= null) {
                list.add(line);
            }
            bf.close();
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
*/
       /* BufferedReader bf2 = null;
        try {
            bf2 = new BufferedReader(new FileReader(this_semester));
            String line;
            while((line=bf2.readLine())!= null) {
                subjects.add(line);
            }
            bf2.close();
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }*/

        //int num_sems = 4;//학기수 . 현재 수강신청한 학기까지 포함.

        subjects_w_grade=parser_w_grade(list);//이번학기를 제외한 성적들.[no, 학과, 교과목, 과목번호, 분반, 구분, 교과목명, 학점, au, 재수강, 성적, 영문교과목명]
        //subs_wo_grade= parser_w_grade(subjects);//[No, 신청_구분, 학과, 과목번호, 분반(없을 수 있음), 과목번호, 구분, 교과목, 학점, au, 교수님, 재수강]

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

    public JSONArray parser(ArrayList<String[]> text){//이전 학기용
        ArrayList<Integer> ho = new ArrayList<Integer>();
        for (int i =0; i<text.size(); i++) {
            if (text.get(i).length==1) {
                ho.add(i);
            }
        }
        for (int i=ho.size()-1;i>=0; i--) {
            int index = ho.get(i);
            text.remove(index+1);
            text.remove(index);
        }

        JSONArray result = new JSONArray();
        for (int i =0; i< text.size(); i++) {
            JSONObject object = new JSONObject();
            int size = text.get(i).length;//11이면 분반 없음, 12면 분반 있음.
            String[] list = text.get(i);
            String Grades;
            String Course_type;
            String Course_title;
            String Repeat;
            int AU;
            int Credits;
            if (size ==11) {
                Course_type = list[4];
                Course_title = list[5];
                Credits = Integer.parseInt(list[6]);
                AU = Integer.parseInt(list[7]);
                Grades = list[9];
                Repeat = list[8];
            }
            else {
                Course_type = list[5];
                Course_title = list[6];
                Credits = Integer.parseInt(list[7]);
                AU = Integer.parseInt(list[8]);
                Grades = list[10];
                Repeat = list[9];
            }
            String Depart = list[1];
            String Code = list[2];
            String Course_no = list[3];
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
