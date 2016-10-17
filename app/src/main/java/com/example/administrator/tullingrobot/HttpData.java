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
    private String requesturl;//requesturl是图灵接口地址
    private URL url=null;
    private HttpURLConnection urlConnection=null;
    public HttpData(String requesturl){
        this.requesturl=requesturl;
    }
    @Override
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
            writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           urlConnection.disconnect();
        }


        return null;
    }
}
