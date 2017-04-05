package com.example.administrator.launcheractivity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends LauncherActivity {

    String[] names = {"设置参数对象","查看","newActivty"};
    String[] descs = {"desc1","desc2"};

    Class<?>[] clazzs = {PreferenceActivityTest.class,
            ExpandableListActivityTest.class,
            newActivity.class};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /*
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for(int i=0;i<names.length;i++)
        {Map<String,Object> listitem = new HashMap<String,Object>();
            listitem.put("header",names[i]);
            listitem.put("desc",descs[i]);
            listItems.add(listitem);
        }
        SimpleAdapter simpleadapter = new SimpleAdapter(this,listItems,
                R.layout.simplelist_item,
                new String[]{"header","descs"},
                new int[]{R.id.header,R.id.descs});
        ListView list = (ListView)findViewById(R.id.mylist) ;
        SimpeAdapter*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);


        //设置显示Adapter
        setListAdapter(adapter);
    }


    @Override
    public Intent intentForPosition(int position){
        return new Intent( MainActivity.this , clazzs[position]);
    }

}
