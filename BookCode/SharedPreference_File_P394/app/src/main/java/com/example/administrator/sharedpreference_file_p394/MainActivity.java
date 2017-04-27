package com.example.administrator.sharedpreference_file_p394;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MainActivity extends Activity {

    final String FILE_NAME="filename.bin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(new StringBuilder("a").append("b").append("c").toString() );
        //
        Button But_read=(Button)findViewById(R.id.But_read);
        final Button But_write=(Button)findViewById(R.id.But_write);

        //获取文本框
        final EditText edit1=(EditText)findViewById(R.id.edit1);
        final EditText edit2=(EditText)findViewById(R.id.edit2);

        But_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Write(edit1.getText().toString() );
                edit1.setText("");
            }
        });

        But_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit2.setText(Read());
            }
        });

    }


    private String Read(){
        try
        {
            //打开文件输入流
            FileInputStream fileinput = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead=0;
            StringBuffer SBuf=new StringBuffer("");
            //读取文件内容
            while( (hasRead=fileinput.read(buff)) > 0  )
            {
                SBuf.append( new String(buff,0,hasRead) );
            }
            fileinput.close();
            return SBuf.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private void Write(String content)
    {
        try
        {
            //追加方式打开
            FileOutputStream fileout = openFileOutput( FILE_NAME,MODE_APPEND);
            //包装成PrintStream
            PrintStream printstream = new PrintStream(fileout);
            //输出文件内容
            printstream.println(content);
            printstream.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
