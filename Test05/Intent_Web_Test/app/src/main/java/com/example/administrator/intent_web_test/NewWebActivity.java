package com.example.administrator.intent_web_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NewWebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_new_web);


        Intent intent = getIntent();
        String URL_cross = (String)intent.getSerializableExtra("url");



        String URL ="http://baidu.com";
        init(URL_cross);

    }


    private void init(String URL){
        WebView webView= (WebView) findViewById(R.id.mywebview);
        //WebView加载web资源
        webView.loadUrl(URL);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }


}
