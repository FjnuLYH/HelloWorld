package com.example.administrator.activitytest;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.ArrayAdapter;
import android.content.Intent;

public class MainActivity extends PreferenceActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main);

    }




}


/*

*/
