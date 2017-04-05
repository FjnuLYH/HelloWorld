package com.example.administrator.launcheractivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class PreferenceActivityTest extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //为该界面设置一个标题按钮
        if( hasHeaders() )
        {
            Button button = new Button(this);
            button.setText("设置操作");
            //为改按钮设置到界面上
            setListFooter(button);
        }
    }

    //加载界面布局文件
    @Override
    public void onBuildHeaders(List<Header> target ){
        //加载选项设置列表的布局文件
        loadHeadersFromResource(R.xml.preference_headers,target);
    }

    @Override
    public boolean isValidFragment(String fragmentName){
        return true;
    }

    public static class Prefs1Fragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);//此处引用Android资源文件preferences.xml
        }
    }

    public static class Prefs2Fragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.display_prefs);//此处引用布局文件display_prefs.xml
            //获取传入该Fragment的参数
            String website = getArguments().getString("website");
            Toast.makeText( getActivity(),"网站域名是："+website,Toast.LENGTH_LONG).show();
        }
    }


}
