package com.example.administrator.launcheractivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class newActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.newlayout);

        ListView list = (ListView)findViewById(R.id.list);

        String[] arr={"111","222","333"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.newlayout,arr);
        list.setAdapter(adapter);
    }
}
