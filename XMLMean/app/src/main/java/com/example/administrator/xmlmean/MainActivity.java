package com.example.administrator.xmlmean;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText edit;
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit=(EditText) findViewById( R.id.test);

        registerForContextMenu(edit);//为文本框注册上下文菜单
    }

    //当点击事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //---------------字体大小的子菜单---------------
        MenuInflater inflator = new MenuInflater( this );

        //装填R.layout.context-上下文菜单的资源文件  此处context位置视情况 必须手动放在menu不然报错
        inflator.inflate( R.menu.activity_menu_main ,menu);


        //必要
        return super.onCreateOptionsMenu(menu);

    }


    //创建上下文菜单触发时的方法
    @Override
    public void onCreateContextMenu(ContextMenu menu , View source ,
                                    ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater= new MenuInflater( this );

        inflater.inflate( R.menu.context,menu);//装填菜单资源文件
        //设置菜单头
        menu.setHeaderTitle("请选择背景色");
    }


    @Override
    public boolean onContextItemSelected( MenuItem mi){
        //mi.setChecked(true);//勾选菜单
        switch( mi.getItemId() )
        {
            case R.id.red:
                mi.setChecked(true);
                edit.setBackgroundColor(Color.RED);
                break;
            case R.id.green:
                mi.setChecked(true);
                edit.setBackgroundColor(Color.GREEN);
                break;
            case R.id.blue:
                mi.setChecked(true);
                edit.setBackgroundColor(Color.BLUE);
                break;
        }
        return true;
    }

    @Override//菜单选项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi){
        if( mi.isChecked())
        {
            //勾选改菜单
            mi.setChecked(true);
        }

        //判断单击的是哪个选项
        switch (mi.getItemId())
        {
            //字体大小改变选项
            case R.id.font_10:
                edit.setTextSize( 10*2 );
                break;
            case R.id.font_12:
                edit.setTextSize( 13*2 );
                break;
            case R.id.font_14:
                edit.setTextSize( 14*2 );
                break;
            case R.id.font_16:
                edit.setTextSize( 16*2 );
                break;
            case R.id.font_18:
                edit.setTextSize( 18*2 );
                break;

            //字体颜色改变选项
            case R.id.red_font:
                edit.setTextColor(Color.RED);
                mi.setChecked(true);
                break;
            case R.id.green_font:
                edit.setTextColor(Color.BLUE);
                mi.setChecked(true);
                break;
            case R.id.blue_font:
                edit.setTextColor(Color.BLACK);//如果此处为black-失败
                mi.setChecked(true);
                break;

            case R.id.plain_item:
                Toast toast = Toast.makeText(MainActivity.this,"单击普通菜单选项",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }
}
