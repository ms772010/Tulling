package com.example.administrator.tullingrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener,View.OnClickListener {
    private HttpData myHttpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText sendText;
    private Button sendButton;
    private String context_str;
    private TextAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }
    private void initView(){
        lv= (ListView) findViewById(R.id.lv);
        sendText= (EditText) findViewById(R.id.sendText);
        sendButton= (Button) findViewById(R.id.sendBtn);
        lists=new ArrayList<ListData>();
        sendButton.setOnClickListener(this);
        adapter=new TextAdapter(lists,this);
        lv.setAdapter(adapter);
    }

    @Override
    public void getDataUrl(String data) {

        ListData listData=new ListData(data,ListData.RECEIVER);
        lists.add(listData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        context_str=sendText.getText().toString();
        ListData listData=new ListData(context_str,ListData.SEND);
        lists.add(listData);
        adapter.notifyDataSetChanged();
        myHttpData=(HttpData) new HttpData(context_str,this).execute();

    }
}
