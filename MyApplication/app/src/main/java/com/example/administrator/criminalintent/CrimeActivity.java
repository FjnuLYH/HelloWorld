package com.example.administrator.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);

        //添加UI fragment 到ragmentManager

        // 获取FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        /*
        如果在上方添加代码出现了错误，请检查导入语句，看看是否导入的是
        import android.support.v4.app.*;
         */


        //fragment事务

        //首先，依据R.id.fragement_container向FragmentManager请求并获取fragment
        //如果希望获取的fragment已经存在于队列中，则直接返回它。
        /*
        关于为什么队列中可能存在希望获取的fragment的原因，主要是因为设备在回收内存时，
        销毁Android会销毁CrimeActivity；之后重建时，会调用CrimeActivity.onCreate(。。。)方法。
        activity被销毁时，其FragmentManager会保留下来。这样在重建activity时，
        新的FragmentManager会首先视图获取保存的队列，然后再重建fragement队列，进而恢复到原来的状态。
         */
        Fragment fragment = fm.findFragmentById(R.id.fragement_container);

        /*
        如果指定容器资源ID不存在，则需要新建CrimeFragement，启动新事务并添加到队列中
         */
        if( fragment == null){
            fragment = new CrimeFragement();
            fm.beginTransaction()
                    .add(R.id.fragement_container,fragment)
                    .commit();
            /*
            add方法是整个事务的核心；
            参数1：容器视图资源ID
            参数2：新创建的CrimeFragement
             */
        }

    }
}
