package com.example.administrator.tullingrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener {
    private HttpData myHttpData;
    private List<ListData> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHttpData=(HttpData) new HttpData("你好",this).execute();

    }
    private void initView(){
        lists=new ArrayList<ListData>();
    }
    @Override
    public void getDataUrl(String data) {
        System.out.println(data);
        ListData listData=new ListData(data);
        lists.add(listData);
    }
}
