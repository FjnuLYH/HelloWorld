package com.example.administrator.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.array_item);


        ListView list01 = (ListView)findViewById(R.id.list);//此处可能返回空指针！
        if( list01 == null) System.out.print("list01 is NULL!");

        TextView txt = (TextView)findViewById(R.id.txt);//上下文菜单
        registerForContextMenu(txt);

        //列表内容
        String[] Item = {"One","Two","Three","Four","Five"};
        //内容包装为ArrayAdapter
        if( Item == null) System.out.print("Item is NULL!");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.array_item,Item);
        if( adapter == null) System.out.print("adapter is NULL!");


        list01.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu , View source,
                                    ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0x111,0,"Menu1");
        menu.add(0,0x112,0,"Menu2");
        menu.add(0,0x113,0,"Menu3");
        menu.setGroupCheckable(0,true,true);
    }

    @Override
    public boolean onContextItemSelected(MenuItem mi){
        Toast toast ;
        switch (mi.getItemId())
        {
            case 0x111:
                 toast = Toast.makeText(MainActivity.this,"Menu1",Toast.LENGTH_SHORT);
                break;

            case 0x112:
                 toast = Toast.makeText(MainActivity.this,"Menu2",Toast.LENGTH_SHORT);
                break;

            case 0x113:
                 toast = Toast.makeText(MainActivity.this,"Menu3",Toast.LENGTH_SHORT);
                break;


        }
        return true;
    }
}
