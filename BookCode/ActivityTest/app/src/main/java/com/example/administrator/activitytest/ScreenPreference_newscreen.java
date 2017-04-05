package com.example.administrator.activitytest;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Administrator on 2017/3/30.
 */
//这里必须继承PreferenceActivity
public class ScreenPreference_newscreen extends PreferenceActivity {


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_screen_new_screen);
    }


}
