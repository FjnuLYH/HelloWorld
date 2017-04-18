package com.example.administrator.sharedpreferences_p391;

import android.app.Activity;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;//这个包是正确的，另个一包也有SimpleDateFormat但是不对
import java.util.Date;

public class MainActivity extends Activity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取只能被本程序读取的SharedPreferences对象-MODE_PRIVATE
        preferences = getSharedPreferences("key01",MODE_PRIVATE);
        editor=preferences.edit();

        Button But_write =(Button)findViewById(R.id.But_Write);
        Button But_read =(Button)findViewById(R.id.But_Read);


        But_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取字符串
                String time=preferences.getString("time",null);
                //读取int
                int randNum=preferences.getInt("random",0);

                String result =  (time==null ? "还没数据" : "写入时间:"+time+"\n上次的随机数:"+randNum) ;

                //Toast提示
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        });

        But_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //因为SharedPreferences不支持Date类型数据 需要借用SimpleDateFormat转化成String
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日"
                + "hh:mm:ss");
                //存入时间

                editor.putString( "time",simpleDateFormat.format( new Date() ) );



                editor.putInt("random",(int)(Math.random()*100 ) );
                editor.commit();

            }
        });


    }
}
