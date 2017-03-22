package com.example.administrator.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    //列表内容  文字+安卓图标
    private String[] TieleItem = {"One","Two","Three","Four","Five"};
    private int[] images = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list01 = (ListView)findViewById(R.id.list);//此处可能返回空指针！


        TextView txt = (TextView)findViewById(R.id.txt);//上下文菜单
        registerForContextMenu(txt);


        //准备列表项
        List< Map<String,Object> > ListItems = new ArrayList< Map<String,Object> >();
        for( int i=0; i< TieleItem.length;i++)
        {
            Map<String,Object> item= new HashMap<String,Object>();
            item.put( "TieleItem", TieleItem[i] );
            item.put( "Image", images[i] );
            ListItems.add( item );
        }

        //创建一个SimpleAdapter
        String[] Key = new String[]{"TieleItem", "Image"};
        int[] ID = new int[]{R.id.TieleItem, R.id.Image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, ListItems,
                R.layout.array_item,
                Key,
                ID//决定填充哪些组件(选项)
        );

        ListView list = (ListView) findViewById(R.id.list);
        //为ListView设置adapter
        list.setAdapter(simpleAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//第项被点击时的事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this,TieleItem[position],
                        Toast.LENGTH_SHORT);
                toast.show();

            }


            public void onNothingSelected( AdapterView<?> partent ){

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu , View source,
                                    ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0x111,0,"Menu1");
        menu.add(0,0x112,0,"Menu2");
        menu.add(0,0x113,0,"Menu3");
        menu.setGroupCheckable(0,true,true);
        menu.setHeaderTitle("上下文菜单");


    }

    @Override
    public boolean onContextItemSelected(MenuItem mi){
        Toast toast ;
        switch (mi.getItemId())
        {
            case 0x111:
                 toast = Toast.makeText(MainActivity.this,"Menu1",Toast.LENGTH_SHORT);
                toast.show();
                break;

            case 0x112:
                 toast = Toast.makeText(MainActivity.this,"Menu2",Toast.LENGTH_SHORT);
                toast.show();
                break;

            case 0x113:
                 toast = Toast.makeText(MainActivity.this,"Menu3",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }
}
