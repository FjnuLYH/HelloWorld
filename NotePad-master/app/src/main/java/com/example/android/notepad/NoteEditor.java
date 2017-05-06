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
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

/**
 * This Activity handles "editing" a note, where editing is responding to
 * {@link Intent#ACTION_VIEW} (request to view data), edit a note
 * {@link Intent#ACTION_EDIT}, create a note {@link Intent#ACTION_INSERT}, or
 * create a new note from the current contents of the clipboard {@link Intent#ACTION_PASTE}.
 *
 * NOTE: Notice that the provider operations in this Activity are taking place on the UI thread.
 * This is not a good practice. It is only done here to make the code more readable. A real
 * application should use the {@link android.content.AsyncQueryHandler}
 * or {@link android.os.AsyncTask} object to perform operations asynchronously on a separate thread.
 */
public class NoteEditor extends Activity {
    // For logging and debugging purposes
    private static final String TAG = "NoteEditor";

    /*
     * Creates a projection that returns the note ID and the note contents.
     */
    private static final String[] PROJECTION =
        new String[] {
            NotePad.Notes._ID,
            NotePad.Notes.COLUMN_NAME_TITLE,
            NotePad.Notes.COLUMN_NAME_NOTE
    };

    // A label for the saved state of the activity
    private static final String ORIGINAL_CONTENT = "origContent";

    // This Activity can be started by more than one action. Each action is represented
    // as a "state" constant
    private static final int STATE_EDIT = 0;
    private static final int STATE_INSERT = 1;
    private static final int STATE_SEARCH = 2;

    // Global mutable variables
    private int mState;
    private Uri mUri;
    private Cursor mCursor;
    private EditText mText;
    private String mOriginalContent;

    /**
     * Defines a custom EditText View that draws lines between each line of text that is displayed.
     （一） 定义了内嵌类LinedEditText
     该类的功能是在便笺背景是打横线，模拟纸质便笺的外观。
     该类继承自EditText，它先建立两个字段矩形mRect 和画笔 mPaint，
     然后在构造函数中给这两个字段赋值，主要部分就是定义了一个方法onDraw用来在便笺是画横线。
     */
    public static class LinedEditText extends EditText {
        private Rect mRect;
        private Paint mPaint;

        // This constructor is used by LayoutInflater
        public LinedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);

            // Creates a Rect and a Paint object, and sets the style and color of the Paint object.
            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0x800000FF);
        }
        /**
         * 方法getLineCount()得到文本的行数，方法getLineBounds()得到指定行的边界，
         * 这两个调用的依据是该类继承自EditText，他们是EditText类的方法。
         *
        */
        /**
         * This is called to draw the LinedEditText object
         * @param canvas The canvas on which the background is drawn.
         */
        @Override//方法onDraw用来在便笺是画横线。
        protected void onDraw(Canvas canvas) {

            // Gets the number of lines of text in the View.
            int count = getLineCount();

            // Gets the global Rect and Paint objects
            Rect r = mRect;
            Paint paint = mPaint;

            /*
             * Draws one line in the rectangle for every line of text in the EditText
             */
            for (int i = 0; i < count; i++) {

                // Gets the baseline coordinates for the current line of text
                int baseline = getLineBounds(i, r);

                /*
                 * Draws a line in the background from the left of the rectangle to the right,
                 * at a vertical position one dip below the baseline, using the "paint" object
                 * for details.
                 */
                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            }

            // Finishes up by calling the parent method
            super.onDraw(canvas);
        }
    }

    /**
     * This method is called by Android when the Activity is first started. From the incoming
     * Intent, it determines what kind of editing is desired, and then does it.
     * 先获取了意图intent和它的活动action。
     * 第一个嵌套的if语句，如果活动是编辑，那么设置mState 和从intent的数据中得到并设置mUri的值，
     如果活动是插入或编辑，那么mState 设置为STATE_INSERT，得到一个内容提供者并从intent中得到数据，返回一个Uri赋值给mUr。
     下一层 if，if (mUri == null)，就是从intent.getData()中没有获得可插入的内容，那么就在Log中记录插入失败并退出onCreate()。
     下一句 setResult语句表示mUri != null时就设置要返回的结果意图，它就是前述的内容提供者。
     setResult语句后面的else表示，如果得到的活动不是插入或粘贴，就那直接退出onCreate()。
     通过了这个嵌套的if以后，这个mUr就是有效的了，利用它查询返回一个光标：
     mCursor = managedQuery()
     本方法的解读见NotesList代码解读。
     如果意图活动是粘贴，就执行粘贴活动 performPaste().
     最后，设置内容视图，获取编辑框视图，原数据保存到mOriginalContent 中。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("启动  NoteEditor-onCreate！");

        /*
         * Creates an Intent to use when the Activity object's result is sent back to the
         * caller.
         */
        final Intent intent = getIntent();

        /*
         *  Sets up for the edit, based on the action specified for the incoming Intent.
         */

        // Gets the action that triggered the intent filter for this Activity
        final String action = intent.getAction();

        // For an edit action:
        if (Intent.ACTION_EDIT.equals(action)) {
            System.out.println("路线001");

            // Sets the Activity state to EDIT, and gets the URI for the data to be edited.
            mState = STATE_EDIT;
            mUri = intent.getData();
            System.out.println("从intent.getData()中得到的mUri=" + mUri);

            // For an insert or paste action:
        } else if (Intent.ACTION_INSERT.equals(action)
                || Intent.ACTION_PASTE.equals(action)
                ) {
            System.out.println("路线002");

            // Sets the Activity state to INSERT, gets the general note URI, and inserts an
            // empty record in the provider
            mState = STATE_INSERT;
            mUri = getContentResolver().insert(intent.getData(), null);
            System.out.println("路线002——muri "+ mUri);
            /*
             * If the attempt to insert the new note fails, shuts down this Activity. The
             * originating Activity receives back RESULT_CANCELED if it requested a result.
             * Logs that the insert failed.
             */
            if (mUri == null) {

                // Writes the log identifier, a message, and the URI that failed.
                Log.e(TAG, "Failed to insert new note into " + getIntent().getData());

                // Closes the activity.
                finish();
                return;
            }

            // Since the new entry was created, this sets the result to be returned
            // set the result to be returned.
            setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));

        // If the action was other than EDIT or INSERT:
        }
        else{

                // Logs an error that the action was not understood, finishes the Activity, and
                // returns RESULT_CANCELED to an originating Activity.
                Log.e(TAG, "Unknown action, exiting");
                finish();
                return;
            }


        /*
         * Using the URI passed in with the triggering Intent, gets the note or notes in
         * the provider.
         * Note: This is being done on the UI thread. It will block the thread until the query
         * completes. In a sample app, going against a simple provider based on a local database,
         * the block will be momentary, but in a real app you should use
         * android.content.AsyncQueryHandler or android.os.AsyncTask.
         */
        System.out.println("测试——managedQuery——muri "+ mUri);
        mCursor = managedQuery(
            mUri,         // The URI that gets multiple notes from the provider.
            PROJECTION,   // A projection that returns the note ID and note content for each note.
            null,         // No "where" clause selection criteria.
            null,         // No "where" clause selection values.
            null          // Use the default sort order (modification date, descending)
        );

        // For a paste, initializes the data from clipboard.
        // (Must be done after mCursor is initialized.)
        if (Intent.ACTION_PASTE.equals(action)) {
            // Does the paste
            System.out.println("NoteEditor--点击黏贴");
            performPaste();
            // Switches the state to EDIT so the title can be modified.
            mState = STATE_EDIT;
        }

        //查询







        // Sets the layout for this Activity. See res/layout/note_editor.xml
        setContentView(R.layout.note_editor);

        // Gets a handle to the EditText in the the layout.
        mText = (EditText) findViewById(R.id.note);

        /*
         * If this Activity had stopped previously, its state was written the ORIGINAL_CONTENT
         * location in the saved Instance state. This gets the state.
         */
        if (savedInstanceState != null) {
            mOriginalContent = savedInstanceState.getString(ORIGINAL_CONTENT);
        }
    }

    /**
     * This method is called when the Activity is about to come to the foreground. This happens
     * when the Activity comes to the top of the task stack, OR when it is first starting.
     *
     * Moves to the first note in the list, sets an appropriate title for the action chosen by
     * the user, puts the note contents into the TextView, and saves the original text as a
     * backup.
     * 该方法在应用程序停止或暂停后再恢复时被调用。
     * 一开始查询，并把光标移动到查询结果的开始处。
     * 如果状态mState为编辑，就获取标题列的索引-整数，再用它返回标题字符串。
     * 然后得到资源，通过资源字符串并格式化得到text，用它设置为标题setTitle(text)，
     * 这个方法属于类TextView。否则，如果状态mState为添加，
     * 从资源R.string.title_create中获取文本作为标题。
     获取note列的索引-整数，再用它返回note的内容字符串。
     mText.setTextKeepState(note)设置编辑框为保持状态。
     如果原数据为空，就把note设置为原数据，否则显示错误信息。
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("启动  NoteEditor-onResume！");

        /*
         * mCursor is initialized, since onCreate() always precedes onResume for any running
         * process. This tests that it's not null, since it should always contain data.
         */
        if (mCursor != null) {
            // Requery in case something changed while paused (such as the title)
            mCursor.requery();

            /* Moves to the first record. Always call moveToFirst() before accessing data in
             * a Cursor for the first time. The semantics of using a Cursor are that when it is
             * created, its internal index is pointing to a "place" immediately before the first
             * record.
             */
            mCursor.moveToFirst();

            // Modifies the window title for the Activity according to the current Activity state.
            if (mState == STATE_EDIT) {
                // Set the title of the Activity to include the note title
                int colTitleIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE);
                String title = mCursor.getString(colTitleIndex);

                System.out.println( "onResume  "+ title);

                Resources res = getResources();
                String text = String.format(res.getString(R.string.title_edit), title);
                setTitle(text);
            // Sets the title to "create" for inserts
            } else if (mState == STATE_INSERT) {
                setTitle(getText(R.string.title_create));
            }else if (mState == STATE_SEARCH){

                System.out.println("NoteEditor-onResume！ if (mState == STATE_SEARCH)");
            }

            /*
             * onResume() may have been called after the Activity lost focus (was paused).
             * The user was either editing or creating a note when the Activity paused.
             * The Activity should re-display the text that had been retrieved previously, but
             * it should not move the cursor. This helps the user to continue editing or entering.
             */

            // Gets the note text from the Cursor and puts it in the TextView, but doesn't change
            // the text cursor's position.
            int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
            String note = mCursor.getString(colNoteIndex);
            mText.setTextKeepState(note);

            // Stores the original note text, to allow the user to revert changes.
            if (mOriginalContent == null) {
                mOriginalContent = note;
            }

        /*
         * Something is wrong. The Cursor should always contain data. Report an error in the
         * note.
         */
        } else {
            setTitle(getText(R.string.error_title));
            mText.setText(getText(R.string.error_message));
        }
    }

    /**
     * This method is called when an Activity loses focus during its normal operation, and is then
     * later on killed. The Activity has a chance to save its state so that the system can restore
     * it.
     *
     * Notice that this method isn't a normal part of the Activity lifecycle. It won't be called
     * if the user simply navigates away from the Activity.
     * 暂停方法，如果mCursor为空，得到文本框的内容text，如果是“完成”并且长度为0，
     * 设置返回结果为“撤消”并删除note。否则：如果状态是编辑就什么也不做，
     * 如果是插入，就用得到的文本text来更新便笺，并设置状态为“编辑”。
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("启动  NoteEditor-onSaveInstanceState！");
        // Save away the original text, so we still have it if the activity
        // needs to be killed while paused.
        outState.putString(ORIGINAL_CONTENT, mOriginalContent);
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
     * If the user hasn't done anything, then this deletes or clears out the note, otherwise it
     * writes the user's work to the provider.
     * 暂停方法，如果mCursor为空，得到文本框的内容text，如果是“完成”并且长度为0，
     * 设置返回结果为“撤消”并删除note。否则：如果状态是编辑就什么也不做，
     * 如果是插入，就用得到的文本text来更新便笺，并设置状态为“编辑”。
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("启动  NoteEditor-onPause！");
        /*
         * Tests to see that the query operation didn't fail (see onCreate()). The Cursor object
         * will exist, even if no records were returned, unless the query failed because of some
         * exception or error.
         *
         */
        if (mCursor != null) {

            // Get the current note text.
            String text = mText.getText().toString();
            int length = text.length();

            /*
             * If the Activity is in the midst of finishing and there is no text in the current
             * note, returns a result of CANCELED to the caller, and deletes the note. This is done
             * even if the note was being edited, the assumption being that the user wanted to
             * "clear out" (delete) the note.
             */
            if (isFinishing() && (length == 0)) {
                setResult(RESULT_CANCELED);
                deleteNote();

                /*
                 * Writes the edits to the provider. The note has been edited if an existing note was
                 * retrieved into the editor *or* if a new note was inserted. In the latter case,
                 * onCreate() inserted a new empty note into the provider, and it is this new note
                 * that is being edited.
                 */
            } else if (mState == STATE_EDIT) {
                // Creates a map to contain the new values for the columns
                System.out.println("STATE_EDIT这里调用updateNote");
                updateNote(text, null);//这里在Note编辑完后，点击右上角保存时调用updateNote
            } else if (mState == STATE_INSERT) {
                System.out.println("STATE_INSERT这里调用updateNote");
                updateNote(text, text);
                mState = STATE_EDIT;
          }
        }
    }

    /**
     * This method is called when the user clicks the device's Menu button the first time for
     * this Activity. Android passes in a Menu object that is populated with items.
     *
     * Builds the menus for editing and inserting, and adds in alternative actions that
     * registered themselves to handle the MIME types for this application.
     *
     * 首先获取一个菜单填充器并填充好菜单，然后当状态mState == STATE_EDIT时，
     * 生成一个Intent并设定其类别是可替换的（CATEGORY_ALTERNATIVE），
     * 然后给选项菜单也加入CATEGORY_ALTERNATIVE，addIntentOptions的第四个参数是某组件，
     * 它由ComponentName的构造函数生成，第六个参数是intent。
     *
     * @param menu A Menu object to which items should be added.
     * @return True to display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("启动  NoteEditor-onCreateOptionsMenu！");
        // Inflate menu from XML resource
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_options_menu, menu);

        // Only add extra menu items for a saved note
        if (mState == STATE_EDIT) {
            System.out.println("!!!!!!!mState == STATE_EDIT");
            // Append to the
            // menu items for any other activities that can do stuff with it
            // as well.  This does a query on the system for any activities that
            // implement the ALTERNATIVE_ACTION for our data, adding a menu item
            // for each one that is found.
            Intent intent = new Intent(null, mUri);
            intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
            menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
                    new ComponentName(this, NoteEditor.class), null, intent, 0, null);
        }

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 根据便笺note的内容是否已改变来准备菜单。准备工作成功就返回真。
     先通过光标（查询的结果）获取note列的索引，继而获取note列内容字符串。
     又获取文本视图mText的当前内容。如果两者相等就设置菜单项menu_revert为可用，否则为不可用。
     * @param menu
     * @return
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("启动  NoteEditor-onPrepareOptionsMenu！");
        // Check if note has changed and enable/disable the revert option
        int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
        String savedNote = mCursor.getString(colNoteIndex);
        String currentNote = mText.getText().toString();
        if (savedNote.equals(currentNote)) {
            menu.findItem(R.id.menu_revert).setVisible(false);
        } else {
            menu.findItem(R.id.menu_revert).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * This method is called when a menu item is selected. Android passes in the selected item.
     * The switch statement in this method calls the appropriate method to perform the action the
     * user chose.
     *使用switch语句，根据所选项item.getItemId()来分流，也三种情况：保存、删除和恢复revert。
     * 如果是保存，就从视图在获取内容来更新便笺，即updateNote.如果是删除就自己删除。
     * 如果是恢复，就执行cancelNote()方法撤消编辑。
     * @param item The selected MenuItem
     * @return True to indicate that the item was processed, and no further work is necessary. False
     * to proceed to further processing as indicated in the MenuItem object.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("启动  NoteEditor-onOptionsItemSelected！");
        // Handle all of the possible menu actions.
        switch (item.getItemId()) {
        case R.id.menu_save:

            String text = mText.getText().toString();
            System.out.println("menu_save这里调用updateNote");
            updateNote(text, null);
            finish();
            break;
        case R.id.menu_delete:
            deleteNote();
            finish();
            break;
        case R.id.menu_revert:
            cancelNote();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

//BEGIN_INCLUDE(paste)
    /**
     * 粘贴操作
     * 本方法实现剪切板操作。先从系统获得剪切板服务和ContentResolver，再获得剪切板的初始内容clip。
     * 如果clip !=null，把text和title设置为空，从剪切板中获取首项命名为item，
     * 从item中获取它的uri。如果uri != null并且项的内容与光标处取得的类型一致，
     * 通过PROJECTION查询返回光标orig，如果orig != null，把光标移到首项，
     * 获取便笺和标题列的整数索引，再由此获取便笺的内容和标题。把内容text强制转化为文本，
     * 最后用它来更新便笺。
     * A helper method that replaces the note's data with the contents of the clipboard.
     */
    private final void performPaste() {
        System.out.println("启动  NoteEditor-performPaste！");
        // Gets a handle to the Clipboard Manager
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        // Gets a content resolver instance
        ContentResolver cr = getContentResolver();

        // Gets the clipboard data from the clipboard
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null) {

            String text=null;
            String title=null;

            // Gets the first item from the clipboard data
            ClipData.Item item = clip.getItemAt(0);

            // Tries to get the item's contents as a URI pointing to a note
            Uri uri = item.getUri();

            // Tests to see that the item actually is an URI, and that the URI
            // is a content URI pointing to a provider whose MIME type is the same
            // as the MIME type supported by the Note pad provider.
            if (uri != null && NotePad.Notes.CONTENT_ITEM_TYPE.equals(cr.getType(uri))) {

                // The clipboard holds a reference to data with a note MIME type. This copies it.
                Cursor orig = cr.query(
                        uri,            // URI for the content provider
                        PROJECTION,     // Get the columns referred to in the projection
                        null,           // No selection variables
                        null,           // No selection variables, so no criteria are needed
                        null            // Use the default sort order
                );

                // If the Cursor is not null, and it contains at least one record
                // (moveToFirst() returns true), then this gets the note data from it.
                if (orig != null) {
                    if (orig.moveToFirst()) {
                        int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
                        int colTitleIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE);
                        text = orig.getString(colNoteIndex);
                        title = orig.getString(colTitleIndex);
                    }

                    // Closes the cursor.
                    orig.close();
                }
            }

            // If the contents of the clipboard wasn't a reference to a note, then
            // this converts whatever it is to text.
            if (text == null) {
                text = item.coerceToText(this).toString();
            }

            // Updates the current note with the retrieved title and text.
            System.out.println("performPaste这里调用updateNote");
            updateNote(text, title);
        }
    }
//END_INCLUDE(paste)

    /**
     * Replaces the current note contents with the text and title provided as arguments.
     * @param text The new note contents to use.
     * @param title The new note title to use
     * 本操作用于更新便笺的内容，同时也赋值给标题。
    开始先定义一个ContentValues类的 values，它用来更新便笺的内容。
     这是Map类的子类，把系统时间作为note的内容加入到中values。
    如果是在插入状态，就开始执行下面的工作。
    如果标题为空，就得到插入的文本title的长度，以文本的字符串作为标题，超过 31个字符就截取31个。
    后面一个if语句把title 其长度超过30时找到最后一个空格，截取空格以前的作为标题。
    有了标题以后，就把标题title 加进values。
    如果标题title 不为空，就直接把title加进values。
    下一步，把便笺内容text加进到values。
最后，用getContentResolver()获取内容解析者并更新它。
   update()的前两个参数是Uri和用来更新的ContentValues。

    注意：菜单中有一个“编辑标题”的项，引用时就会自动把上述截取的文本作为标题供你编辑。
    如果与你的意思完全不相配，就删除后自行输入标题好了。
     */
    private final void updateNote(String text, String title) {
        System.out.println("启动  NoteEditor-updateNote！");
//新建一个NOte，默认标题=内容第一行+日期
        // Sets up a map to contain values to be updated in the provider.
        ContentValues values = new ContentValues();
        Long now = GetTime.Get_Now_Time_Long();


        //获取当前时间

        // If the action is to insert a new note, this creates an initial title for it.
        if (mState == STATE_INSERT) {

            // If no title was provided as an argument, create one from the note text.
            if (title == null) {

            }
            // In the values map, sets the value of the title
            values.put(NotePad.Notes.COLUMN_NAME_TITLE, title);
        } else if (title != null) {
            // In the values map, sets the value of the title
            values.put(NotePad.Notes.COLUMN_NAME_TITLE, title);
        }



        // This puts the desired notes text into the map.
        values.put(NotePad.Notes.COLUMN_NAME_NOTE, text);
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,now);
        /*
         * Updates the provider with the new values in the map. The ListView is updated
         * automatically. The provider sets this up by setting the notification URI for
         * query Cursor objects to the incoming URI. The content resolver is thus
         * automatically notified when the Cursor for the URI changes, and the UI is
         * updated.
         * Note: This is being done on the UI thread. It will block the thread until the
         * update completes. In a sample app, going against a simple provider based on a
         * local database, the block will be momentary, but in a real app you should use
         * android.content.AsyncQueryHandler or android.os.AsyncTask.
         */
        getContentResolver().update(
                mUri,    // The URI for the record to update.
                values,  // The map of column names and new values to apply to them.
                null,    // No selection criteria are used, so no where columns are necessary.
                null     // No where columns are used, so no where arguments are necessary.
            );


    }

    /**
     * This helper method cancels the work done on a note.  It deletes the note if it was
     * newly created, or reverts to the original text of the note i
     * 如果光标非空，如果是编辑状态，就关闭mCursor并置为空，把原始内容放入到values中，
     * 然后更新ContentResolver。如果是插入状态，直接调用deleteNote()方法删除便笺。
     * 最后设置返回结果为“撤消”，退出本编辑活动。
     */
    private final void cancelNote() {
        System.out.println("启动  NoteEditor-cancelNote！");
        if (mCursor != null) {
            if (mState == STATE_EDIT) {
                // Put the original note text back into the database
                mCursor.close();
                mCursor = null;
                ContentValues values = new ContentValues();
                values.put(NotePad.Notes.COLUMN_NAME_NOTE, mOriginalContent);
                getContentResolver().update(mUri, values, null, null);
            } else if (mState == STATE_INSERT) {
                // We inserted an empty note, make sure to delete it
                deleteNote();
            }
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * Take care of deleting a note.  Simply deletes the entry.
     * 删除便笺。如果光标非空就关闭mCursor并置为空，删除ContentResolver，清空编辑框中的文字。
     */
    private final void deleteNote() {
        System.out.println("启动  NoteEditor-deleteNote！");
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
            getContentResolver().delete(mUri, null, null);
            mText.setText("");
        }
    }





}
