package com.example.administrator.intenttest_p269;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public final static String CRAZYIT_ACTION="org.crazyit.intent.action.CRAZYIT_ACTION";
    public final static String CRAZYIT_CATAGORY="org.crazyit.intent.category.CRAZYIT_CATAGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_1 = (Button)findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent();
                //设置一个Action属性
                intent.setAction(MainActivity.CRAZYIT_ACTION);//此处有两个Activity可以选
                intent.addCategory(MainActivity.CRAZYIT_CATAGORY);

                startActivity(intent);
            }
        });
    }
}
