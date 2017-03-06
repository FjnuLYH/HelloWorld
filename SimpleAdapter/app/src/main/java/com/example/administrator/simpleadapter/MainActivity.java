package com.example.administrator.simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String[] names = new String[]
            { "虎头" ,"龙玉" ,"李清照" ,"李白" };
    private  String[] desce = new String[]
            {"可爱的小孩" ,"一个擅长音乐的女孩" ,"一个擅长文学的女性" ,"浪漫主义诗人"};
    private int[] imageIDs = new int[]
            { R.drawable}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
