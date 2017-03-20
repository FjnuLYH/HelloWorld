package com.example.administrator.xmlmean;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //定义变量-字体大小
    final int FONT_10=0x111;
    final int FONT_12=0x112;
    final int FONT_14=0x113;
    final int FONT_16=0x114;
    final int FONT_18=0x115;
    //定义变量-普通菜单项 的标示
    final int PLAIN_ITEM = 0x11b;
    //定义变量-字体颜色 的标示
    final int FONT_RED=0x116;
    final int PONT_BLUE=0x117;
    final int PONT_GREEN=0x118;
    private EditText edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.txt);////////////////////////
    }

    //当用户单机MENU触发改方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //---------------字体大小的子菜单---------------
        SubMenu fontMenu = menu.addSubMenu("字体大小");
        //设置菜单图标
        //fontMenu.setIcon(R.drawable.);
        //设置菜单头的图标
        //fontMenu.setHeaderIcon(R.drawable);

        //设置菜单头标题(内容)
        fontMenu.setHeaderTitle("选择字体大小");
        fontMenu.add(0,FONT_10,0,"10号字体");
        fontMenu.add(0,FONT_12,0,"12号字体");
        fontMenu.add(0,FONT_14,0,"14号字体");
        fontMenu.add(0,FONT_16,0,"16号字体");
        fontMenu.add(0,FONT_18,0,"18号字体");

        //---------------普通菜单项---------------
        menu.add(0,PLAIN_ITEM,0,"普通菜单项");

        //---------------字体颜色的子菜单--------------
        SubMenu colorMenu = menu.addSubMenu("字体颜色");

        //菜单头图标
        //colorMenu.setIcon(R.drawable.);
        //设置菜单头图标
        //colorMenu.setHeaderTitle(R.drawable.);

        //设置菜单头标题
        colorMenu.setHeaderTitle("选择字体颜色");
        colorMenu.add(0,FONT_RED,0,"红色");
        colorMenu.add(0,PONT_BLUE,0,"蓝色");
        colorMenu.add(0,PONT_GREEN,0,"绿色");

        //必要
        
        return super.onCreateOptionsMenu(menu);

    }


    @Override//菜单选项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi){
        //判断单击的是哪个选项
        switch (mi.getItemId())
        {
            //字体大小改变选项
            case FONT_10:
                edit.setTextSize( 10*2 );
                break;
            case FONT_12:
                edit.setTextSize( 13*2 );
                break;
            case FONT_14:
                edit.setTextSize( 14*2 );
                break;
            case FONT_16:
                edit.setTextSize( 16*2 );
                break;
            case FONT_18:
                edit.setTextSize( 18*2 );
                break;

            //字体颜色改变选项
            case FONT_RED:
                edit.setTextColor(Color.RED);
                break;
            case PONT_BLUE:
                edit.setTextColor(Color.BLUE);
                break;
            case PONT_GREEN:
                edit.setTextColor(Color.BLACK);//如果此处为black-失败
                break;

            case PLAIN_ITEM:
                Toast toast = Toast.makeText(MainActivity.this,"单击普通菜单选项",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }
}
