package com.example.administrator.tullingrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener {
    private HttpData myHttpData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHttpData=(HttpData) new HttpData("ok",this).execute();

    }

    @Override
    public void getDataUrl(String data) {
        System.out.println(data);
    }
}
