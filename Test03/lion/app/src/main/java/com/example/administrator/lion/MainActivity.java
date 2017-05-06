package com.example.administrator.lion;


import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




/*
    public void singleChoise(View source)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder( this)//设置对话框图标
                .setView(loginForm)//填充布局
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {//设置登录按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建一个·Toast信息
                        Toast toast = Toast.makeText(MainActivity.this,"Yes",
                                //设置显示的时间
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })//第一个按钮;登录 设置完成
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {//设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建一个·Toast信息
                        Toast toast = Toast.makeText(MainActivity.this,"CANCEL",
                                //设置显示的时间
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .create()
                .show();
    }*/


    public void customView(        ){
        //加载login界面布局文件!
        TableLayout loginForm = (TableLayout)getLayoutInflater().inflate(R.layout.login,null);

        new AlertDialog.Builder( this)//设置对话框图标
        //.setIcon( R.drawable)
        .setTitle("对话框标题")//设置对话框标题
        .setView(loginForm)//填充布局
        .setPositiveButton("登录", new DialogInterface.OnClickListener() {//设置登录按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //创建一个·Toast信息
                Toast toast = Toast.makeText(MainActivity.this,"你选择了登录",
                        //设置显示的时间
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        })//第一个按钮;登录 设置完成
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //创建一个·Toast信息
                Toast toast = Toast.makeText(MainActivity.this,"你选择了取消登录",
                        //设置显示的时间
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        })
        .create()
        .show();

    }


//则是一个公用的按钮事件处理，用于界面跳转
    public void buttonListener(View v) {
        //AlertDialog  dm = new AlertDialog(this);
        switch (v.getId()) {
            case R.id.simple_button_id:
                //dm.simpleDialog("最简单的对话框", msg);
                break;
            case R.id.list_button_id:
                //dm.listDialog("列表对话框", str);
                break;
            case R.id.singleChoice_button_id:
                //dm.singleChoiceDialog("单选对话框", str);
                break;
            case R.id.multiChoice_button_id:
                //dm.MultiChoiceDialog("多选对话框", str);
                break;
            case R.id.view_button_id:
                this.customView();
                

                break;
            default:
                break;
        }
    }
}
