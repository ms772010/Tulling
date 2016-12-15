package com.example.administrator.tullingrobot;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class history extends AppCompatActivity {
    private SharedPreferences sp ;
    private Map<String, ?> map ;
    private TextView mytextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sp= getSharedPreferences("history", Context.MODE_PRIVATE);
        map=sp.getAll();
        mytextview= (TextView) findViewById(R.id.textView);
        /*Editor editor=sp.edit();
        editor.clear();
        editor.commit();*/
        for (Map.Entry entry : map.entrySet()) {

            Object key = entry.getKey( );
            mytextview.append(map.get(key).toString());
            mytextview.append("\n");

        }

    }
}
