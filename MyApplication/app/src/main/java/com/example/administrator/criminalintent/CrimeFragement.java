package com.example.administrator.criminalintent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/5/25.
 */

public class CrimeFragement extends android.support.v4.app.Fragment{
    /*注意，继承Fragment类似，Android studio会找到两个同名Fragment类，
    Fragment(android.app)  与 Fragment(android.support.v4.app)
    此处必须用后者
     */

    private Crime mCrime;
    private EditText mTitleField;


    //覆盖Fragment.onCreate()方法
    /*
    此处的onCreate方法必须是public，因为托管Fragment的activity需要调用它。
    另外，onCreate方法只配置Fragment实例，不配置和创建Fragment视图。
    Fragment视图的配置和创建在下面函数中实现
     */
    @Override
    public  void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        mCrime = new Crime();
    }



    /*
    onCreateView方法：
    参数1:Fragment的视图是通过直接调用LayoutInflater.inflate(。。。)方法传入布局资源ID生成的
    参数2:视图的父视图，开发人员需要父视图来正确配置组件
    参数3:告知布局生成器是否将生成的视图添加给父视图。这里的false表名将以Activity代码的方式添加视图
     */
    /*
    Fragment.onCreateView(。。。)方法中的组件引用几乎与Activity.onCreate(。。。)一致。
    唯一的区别是需要调用Fragment视图的View.findViewById(int ID)方法；
    而之前Activity.findViewById(int ID)方法分方便，能够后台自动调用View.findViewById(int ID)方法；
    而Fragment没有这样对应的便利方法，需要卡法则显式调用View.findViewById(int ID)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View v =inflater.inflate(R.layout.frame_layout ,container ,false);

        //生成并使用EditText组件
        mTitleField = (EditText)v.findViewById( R.id.container_title );

        //以下设置监听器
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }







}
