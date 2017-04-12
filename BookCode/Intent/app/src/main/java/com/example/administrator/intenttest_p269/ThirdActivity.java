package com.example.administrator.intenttest_p269;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Set;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ThirdActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.third);

        EditText show=(EditText)findViewById(R.id.show_3);
        
        //获取Activity信息
        String action = getIntent().getAction();
        show.setText("Third Action :" + action);

        //获取Catagory信息
        EditText cate=(EditText)findViewById(R.id.cate_3);
        Set<String> cates =getIntent().getCategories();
        cate.setText("Third Catagory: " + cates);

    }

}
