package com.example.administrator.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
public boolean onCreateOptionMenu(Menu menu) {
    getMenuInflater().inflate(R,menu);
    return true;
}*/
    public void clickHandler(View source) {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Hello Androd-" + new java.util.Date());
    }
}
