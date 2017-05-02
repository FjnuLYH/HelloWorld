package com.example.android.notepad;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

/**
 * Created by Administrator on 2017/5/1.
 */

public class Search_By_Title extends Activity{
/**
 * 这是专门用来通过标题进行查找的类，模仿TitleEditor写
 * 对应布局文件：search_by_title.xml
 */

    //这里需要把Activity加入到AndroidManifest.xml中//还没加
    public static final String SEARCH_BY_TITLE_ACTION ="com.android.notepad.action.SEARCH_BY_TITLE";
    // 用来查询用的列名
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
    };
    //Title在查询中的列号=1
    private static final int COLUMN_INDEX_TITLE = 1;

    //准备读取数据载体
    private Cursor mCursor;
    // An EditText object for preserving the edited title.
    private EditText mText;

    // A URI object for the note whose title is being edited.
    private Uri mUri;


    /**
     * 新建一个
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("启动  Search_By_Title-onCreate！");
        // Set the View for this Activity object's UI.

        setContentView(R.layout.search_by_title);



        // Get the Intent that activated this Activity, and from it get the URI of the note whose
        // title we need to edit.
        //获取意图的数据中的Uri。
        mUri = NotePad.Notes.CONTENT_URI;


        /*
         * Using the URI passed in with the triggering Intent, gets the note.
         *
         * Note: This is being done on the UI thread. It will block the thread until the query
         * completes. In a sample app, going against a simple provider based on a local database,
         * the block will be momentary, but in a real app you should use
         * android.content.AsyncQueryHandler or android.os.AsyncTask.
         */
        //调用managedQuery()方法获得查询结果mCursor。\

        mCursor = managedQuery(
                mUri,        // The URI for the note that is to be retrieved.
                PROJECTION,  // The columns to retrieve
                null,        // No selection criteria are used, so no where columns are needed.
                null,        // No where columns are used, so no where values are needed.
                null         // No sort order is needed.
        );

        // Gets the View ID for the EditText box
        mText = (EditText) this.findViewById(R.id.SearchTitle);
    }


    /**
     * 当活动恢复时被执行。把光标移动到开头，把首行标题列的文字设置到编辑框中。
     * 这里注意！！此处一开始创建之后默认的标题，记得加上date日期
     *如果光标为空就什么也不做
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("启动  Search_By_Title-onResume！");
        // Verifies that the query made in onCreate() actually worked. If it worked, then the
        // Cursor object is not null. If it is *empty*, then mCursor.getCount() == 0.


        //这里是点击EditTitle时
        //把输入框中需要查找的数据
        String Title = mText.getText().toString();

        //查询条件语句
        String selection =  NotePad.Notes.COLUMN_NAME_TITLE + " like ? ";
        //查询条件语句的条件值
        String[]   selectionArgs = {"%" + Title + "%"};
        System.out.println("AA!!!==" + selectionArgs[0]);
        System.out.println("MURI==" + mUri);

        getContentResolver().query(
                mUri,    // The URI for the note to update.
                PROJECTION,  // The values map containing the columns to update and the values to use.
                selection,    // 查询条件 -where titile = ??
                selectionArgs,     // 查询条件的值  ??的值
                null       //排序
        );



        if (mCursor != null) {

            // The Cursor was just retrieved, so its index is set to one record *before* the first
            // record retrieved. This moves it to the first record.
            mCursor.moveToFirst();

            //这里是编辑标题时显示的，还没写入数据库
            // Displays the current title text in the EditText object.
            mText.setText(mCursor.getString(COLUMN_INDEX_TITLE));

        }
    }


    /**
     * 当活动恢复时执行。创建一个ContentValues 类实例values，
     * 把在编辑的文本作为标题文字加入到values中，最后更新内容解析者。
     */
    @Override
    protected void onPause(){
        super.onPause();
        System.out.println("启动  Search_By_Title-onPause！");
        // Verifies that the query made in onCreate() actually worked. If it worked, then the
        // Cursor object is not null. If it is *empty*, then mCursor.getCount() == 0.


        if (mCursor != null) {

            // Creates a values map for updating the provider.
            ContentValues values = new ContentValues();

            // In the values map, sets the title to the current contents of the edit box.
//这里是点击EditTitle时
            //把输入框中需要查找的数据
            String Title = mText.getText().toString();

            values.put(NotePad.Notes.COLUMN_NAME_TITLE,Title);



            /*
             * Updates the provider with the note's new title.
             *
             * Note: This is being done on the UI thread. It will block the thread until the
             * update completes. In a sample app, going against a simple provider based on a
             * local database, the block will be momentary, but in a real app you should use
             * android.content.AsyncQueryHandler or android.os.AsyncTask.
             */
            //query(Uri, String[], String, String[], String)

            //查询条件语句
            String selection =  NotePad.Notes.COLUMN_NAME_TITLE + " like ?";
            //查询条件语句的条件值
            String[]   selectionArgs = {"%" + Title + "%"};


            getContentResolver().query(
                    mUri,    // The URI for the note to update.
                    PROJECTION,  // The values map containing the columns to update and the values to use.
                    selection,    // 查询条件 -where titile = ??
                    selectionArgs,     // 查询条件的值  ??的值
                    null       //排序
            );

        }
    }

    //点击OK按钮时执行：推出本活动。注意：布局在按钮的属性设定了 android:onClick="onClickOk"
    // 所以才可以编写onClickOk()方法来执行按钮的活动，否则需要设置按钮监听器
    public void onClickOk(View v) {
        finish();
    }


}
