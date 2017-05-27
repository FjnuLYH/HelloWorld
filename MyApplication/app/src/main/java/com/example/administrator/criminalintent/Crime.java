package com.example.administrator.criminalintent;

import java.util.UUID;

/**
 * Created by Administrator on 2017/5/24.
 */

public class Crime {
    private UUID mID;
    private String mTitle;
 //构造函数
    public Crime(){
        mID=UUID.randomUUID();
    }
//为变量创造get set方法
    public UUID getmID() {
        return mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
