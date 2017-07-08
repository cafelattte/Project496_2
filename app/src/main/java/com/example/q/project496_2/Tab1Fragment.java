package com.example.q.project496_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by q on 2017-07-06.
 */

public class Tab1Fragment extends Fragment{

    CustomAddressAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new CustomAddressAdapter();
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        ListView tab1_listview = (ListView) view.findViewById(R.id.tab1_listview);

        adapter.addAddress(R.drawable.pink, "Pink", "010-1234-5678");
        adapter.addAddress(R.drawable.peach, "Peach", "010-9012-3456");
        adapter.addAddress(R.drawable.green, "Green", "010-7890-1234");
        adapter.addAddress(R.drawable.gray, "Gray", "010-1234-5678");
        adapter.addAddress(R.drawable.purple, "Purple", "010-9012-3456");
        adapter.addAddress(R.drawable.red, "Red", "010-7890-1234");
        adapter.addAddress(R.drawable.skyblue, "Skyblue", "010-1234-5678");
        adapter.addAddress(R.drawable.yellow, "Yellow", "010-9012-3456");
        adapter.addAddress(R.drawable.yellow2, "Yellow2", "010-7890-1234");

        tab1_listview.setAdapter(adapter);

        return view;
    }
}