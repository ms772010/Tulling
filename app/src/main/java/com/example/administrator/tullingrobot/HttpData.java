package com.example.administrator.tullingrobot;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;


/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class HttpData extends AsyncTask<String,Void,String> {
    private String requesturl="http://www.tuling123.com/openapi/api";//requesturl是图灵接口地址
    private URL url=null;
    private HttpURLConnection urlConnection=null;
    public HttpData(String requesturl){
        this.requesturl=requesturl;
    }
    @Override
    //http://www.tuling123.com/openapi/api
    protected String doInBackground(String... params) {
        try {
            url =new URL(requesturl);
            urlConnection=(HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);   //写要发送给网站的数据，写为json格式

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);    //读网站传回的json数据，输出string

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           urlConnection.disconnect();
        }


        return null;
    }

    private void writeStream(OutputStream out) {
    }

    private void readStream(InputStream in){

    }
}
