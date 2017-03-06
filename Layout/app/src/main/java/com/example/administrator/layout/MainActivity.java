package com.example.administrator.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private Button Butt_TAB=null;
    private Button Butt_Linear=null;
    private Button Butt_Real =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Butt_TAB= (Button)findViewById(R.id.button_Tab);
        Butt_TAB.setOnClickListener(new TABButtonListener());

        Butt_Linear = (Button)findViewById(R.id.button_Liner);
        Butt_Linear.setOnClickListener(new LINEArButtonListener());

        Butt_Real = (Button)findViewById(R.id.button_Rela);
        Butt_Real.setOnClickListener(new RelaButtonListener() );
    }

    class TABButtonListener implements View.OnClickListener{
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TableLayout.class);
            MainActivity.this.startActivity(intent);
        }
    }

    class LINEArButtonListener implements View.OnClickListener{
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LinearLayout.class);
            MainActivity.this.startActivity(intent);
        }
    }

    class RelaButtonListener implements View.OnClickListener{
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, RelativeLayout.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
