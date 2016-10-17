package com.example.administrator.tullingrobot;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class HttpData extends AsyncTask<String,Void,String> {
    private String requesturl;//requesturl是图灵接口地址

    public HttpData(String requesturl){
        this.requesturl=requesturl;
    }
    @Override
    protected String doInBackground(String... params) {
        URL url= null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(requesturl);
            urlConnection = (HttpURLConnection)url.openConnection();//建立链接
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
