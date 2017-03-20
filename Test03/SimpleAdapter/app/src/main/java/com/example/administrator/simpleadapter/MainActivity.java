package com.example.administrator.simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] names = new String[]
            { "cat" ,"dog" ,"elephant" ,"lion" ,"monkey","tiger"};
    private int[] imageIDs = new int[]
            { R.drawable.cat, R.drawable.dog, R.drawable.elephant,
                    R.drawable.lion, R.drawable.monkey, R.drawable.tiger };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建一个List集合,集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("header", imageIDs[i]);//把
            listItem.put("personName", names[i]);//


            listItems.add(listItem);
        }

        //创建一个SimpleAdapter
        String[] Key = new String[]{"personName", "header"};
        int[] ID = new int[]{R.id.name, R.id.header};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simple_item,
                new String[]{"personName", "header"},
                new int[]{R.id.name, R.id.header}//决定填充哪些组件(选项)
        );

        ListView list = (ListView) findViewById(R.id.mylist);
        //为ListView设置adapter
        list.setAdapter(simpleAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//第项被点击时的事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //public void onClick( View source){
                    //创建一个·Toast信息   //设置显示的时间
                    Toast toast = Toast.makeText(MainActivity.this,names[position],

                    Toast.LENGTH_SHORT);

                    toast.show();
                //}
            }


            public void onNothingSelected( AdapterView<?> partent ){

            }
        });


    }



    }
