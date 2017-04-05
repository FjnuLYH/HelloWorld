package com.example.administrator.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//实现ActionMode.CallBack接口
    private ActionMode.Callback mCallback = new ActionMode.Callback() {

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.array_item, menu);
            startActionMode(mCallback);//----------

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            boolean ret = false;
            if (item.getItemId() == R.id.textview) {
                mode.finish();
                ret = true;
            }
            return ret;
        }
    };



    setOnLongClickListener(new View.OnLongClickListener() {
        startActionMode(mCallBack);
.        // Called when the user long-clicks on someView
        public boolean onLongClick(View view) {
            if (mCallback != null) {
                return false;
            }
            // Start the CAB using the ActionMode.Callback defined above
            mActionMode = getActivity().startActionMode(mActionModeCallback);
            view.setSelected(true);
            return true;
        }

    });




    //列表内容  文字+安卓图标
    private String[] TieleItem = {"One","Two","Three","Four","Five"};
    private int[] images = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};







};



