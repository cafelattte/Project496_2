package com.example.q.project496_2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by q on 2017-07-06.
 */

public class Tab3Fragment extends Fragment{
    private static final String TAG = "tab3_Fragment";
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container , false);
        btnTEST = (Button) view.findViewById(R.id.btnTest3);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "BUTTON CLICK 3", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
