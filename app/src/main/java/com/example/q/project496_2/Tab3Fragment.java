package com.example.q.project496_2;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                startActivity(intent);
                new HttpMakeDB().execute("http://52.78.19.146:8080/start");
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
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestProperty("Content-Type","application/json");

                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                InputStream is = getContext().getResources().openRawResource(R.raw.currrent);

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
