package com.example.administrator.activitytest;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.content.Intent;

public class MainActivity extends LauncherActivity {
    /*In-line preferences
            CheckBoxPreference
    Dialog-based preferences:
            EditTextPreference
            ListPreference
    Launch preferences
            PreferenceScreen: 跳转到另一个PreferenceScreen
            PreferenceScreen: 启动一个网页
    Preference attributes
            CheckBox: 父选项
            CheckBox: 子选项，当父选项勾选时呈现*/

    String[] names = {"CheckBoxPreference",
            "EditTextPreference","ListPreference",
            "Screen Preference","Internet Preference",
            "CheckBox_Parent","CheckBox_Child"};
    Class<?>[] clazzs = {
            CheckBoxPreference.class,
            null,null,
            ScreenPreference_newscreen.class,ScreenPreference_newintent.class,
            null,null,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);不能有这一句！
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);


        //设置显示Adapter
        setListAdapter(adapter);
    }

    @Override
    public Intent intentForPosition(int position){
        return new Intent( MainActivity.this , clazzs[position]);
    }

}
