/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.notepad;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

/**
 * This Activity allows the user to edit a note's title. It displays a floating window
 * containing an EditText.
 *
 * NOTE: Notice that the provider operations in this Activity are taking place on the UI thread.
 * This is not a good practice. It is only done here to make the code more readable. A real
 * application should use the {@link android.content.AsyncQueryHandler}
 * or {@link android.os.AsyncTask} object to perform operations asynchronously on a separate thread.
 */
public class TitleEditor extends Activity {

    /**
     * This is a special intent action that means "edit the title of a note".
     */
    public static final String EDIT_TITLE_ACTION = "com.android.notepad.action.EDIT_TITLE";

    // Creates a projection that returns the note ID and the note contents.
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//2
    };

    // The position of the title column in a Cursor returned by the provider.
    private static final int COLUMN_INDEX_TITLE = 1;
    private static final int COLUMN_INDEX_MODIFICATION_DATE = 2;

    // A Cursor object that will contain the results of querying the provider for a note.
    private Cursor mCursor;

    // An EditText object for preserving the edited title.
    private EditText mText;

    // A URI object for the note whose title is being edited.
    private Uri mUri;

    /**
     * This method is called by Android when the Activity is first started. From the incoming
     * Intent, it determines what kind of editing is desired, and then does it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("启动  TitleEditor-onCreate！");
        // Set the View for this Activity object's UI.

        setContentView(R.layout.title_editor);

        // Get the Intent that activated this Activity, and from it get the URI of the note whose
        // title we need to edit.
        //获取意图的数据中的Uri。
        mUri = getIntent().getData();

        /*
         * Using the URI passed in with the triggering Intent, gets the note.
         *
         * Note: This is being done on the UI thread. It will block the thread until the query
         * completes. In a sample app, going against a simple provider based on a local database,
         * the block will be momentary, but in a real app you should use
         * android.content.AsyncQueryHandler or android.os.AsyncTask.
         */
        //调用managedQuery()方法获得查询结果mCursor。
        mCursor = managedQuery(
            mUri,        // The URI for the note that is to be retrieved.
            PROJECTION,  // The columns to retrieve
            null,        // No selection criteria are used, so no where columns are needed.
            null,        // No where columns are used, so no where values are needed.
            null         // No sort order is needed.
        );

        // Gets the View ID for the EditText box
        mText = (EditText) this.findViewById(R.id.title);
    }

    /**
     * This method is called when the Activity is about to come to the foreground. This happens
     * when the Activity comes to the top of the task stack, OR when it is first starting.
     *
     * Displays the current title for the selected note.
     */
    //当活动恢复时被执行。把光标移动到开头，把首行标题列的文字设置到编辑框中。
    // 这里注意！！此处一开始创建之后默认的标题，记得加上date日期
    //如果光标为空就什么也不做
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("启动  TitleEditor-onResume！");
        // Verifies that the query made in onCreate() actually worked. If it worked, then the
        // Cursor object is not null. If it is *empty*, then mCursor.getCount() == 0.
        if (mCursor != null) {

            // The Cursor was just retrieved, so its index is set to one record *before* the first
            // record retrieved. This moves it to the first record.
            mCursor.moveToFirst();

            //这里是编辑标题时显示的，还没写入数据库
            // Displays the current title text in the EditText object.
            mText.setText(mCursor.getString(COLUMN_INDEX_TITLE)  );

        }
    }

    /**
     * This method is called when the Activity loses focus.
     *
     * For Activity objects that edit information, onPause() may be the one place where changes are
     * saved. The Android application model is predicated on the idea that "save" and "exit" aren't
     * required actions. When users navigate away from an Activity, they shouldn't have to go back
     * to it to complete their work. The act of going away should save everything and leave the
     * Activity in a state where Android can destroy it if necessary.
     *
     * Updates the note with the text currently in the text box.
     */
    //当活动恢复时执行。创建一个ContentValues 类实例values，
    // 把在编辑的文本作为标题文字加入到values中，最后更新内容解析者。
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("启动  TitleEditor-onPause！");
        // Verifies that the query made in onCreate() actually worked. If it worked, then the
        // Cursor object is not null. If it is *empty*, then mCursor.getCount() == 0.


        if (mCursor != null) {

            // Creates a values map for updating the provider.
            ContentValues values = new ContentValues();

            // In the values map, sets the title to the current contents of the edit box.
//这里是点击EditTitle时，将编辑后的标志，有本代码加上时间，记录数据库

            values.put(NotePad.Notes.COLUMN_NAME_TITLE,mText.getText().toString());



            /*
             * Updates the provider with the note's new title.
             *
             * Note: This is being done on the UI thread. It will block the thread until the
             * update completes. In a sample app, going against a simple provider based on a
             * local database, the block will be momentary, but in a real app you should use
             * android.content.AsyncQueryHandler or android.os.AsyncTask.
             */
            getContentResolver().update(
                    mUri,    // The URI for the note to update.
                    values,  // The values map containing the columns to update and the values to use.
                    null,    // No selection criteria is used, so no "where" columns are needed.
                    null     // No "where" columns are used, so no "where" values are needed.
            );

        }


    }

    //点击OK按钮时执行：推出本活动。注意：布局在按钮的属性设定了 android:onClick="onClickOk"
    // 所以才可以编写onClickOk()方法来执行按钮的活动，否则需要设置按钮监听器
    public void onClickOk(View v) {
        finish();
    }
}
