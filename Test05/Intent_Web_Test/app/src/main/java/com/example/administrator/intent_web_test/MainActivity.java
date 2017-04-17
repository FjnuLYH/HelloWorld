package com.example.administrator.intent_web_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public final static String CRAZYIT_ACTION="org.crazyit.intent.action.CRAZYIT_ACTION";
    public final static String CRAZYIT_CATAGORY="org.crazyit.intent.category.CRAZYIT_CATAGORY";
    public final static String Browser_ACTION="org.crazyit.intent.action.BROWSER_ACTION";
    public final static String Browser_CATAGORY="org.crazyit.intent.category.BROWSER_CATAGORY";


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
                intent.setAction(MainActivity.CRAZYIT_ACTION);//打开
                intent.addCategory(MainActivity.CRAZYIT_CATAGORY);

                startActivity(intent);
            }
        });


        Button button_newweb = (Button)findViewById(R.id.button_web);
        button_newweb.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //创建Intent对象
                Intent intent = new Intent();
                //设置一个Action属性
                intent.setAction(MainActivity.CRAZYIT_ACTION);
                intent.addCategory(MainActivity.CRAZYIT_CATAGORY);

                EditText edittext =(EditText)findViewById(R.id.Edit_URL);
                String url = edittext.getText().toString();
                Bundle data = new Bundle();//新建Bundle
                data.putSerializable("url",url);//将URL数据放入Bundle

                intent.putExtras(data);//Bundle放入intent

                startActivity(intent);
            }
        });



    }





}
