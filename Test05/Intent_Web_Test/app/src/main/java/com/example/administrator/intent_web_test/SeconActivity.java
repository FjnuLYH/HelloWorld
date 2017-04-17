package com.example.administrator.intent_web_test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Set;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SeconActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second);

        EditText show=(EditText)findViewById(R.id.show_2);

        //获取Activity信息
        String action = getIntent().getAction();
        show.setText("Second Action :" + action);

        //获取Catagory信息
        EditText cate=(EditText)findViewById(R.id.cate_2);
        Set<String> cates =getIntent().getCategories();
        cate.setText("Second Catagory: " + cates);

    }
}
