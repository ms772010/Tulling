package com.example.administrator.tullingrobot;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class history extends AppCompatActivity {
    private SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sp= getSharedPreferences("history", Context.MODE_PRIVATE);
        System.out.println(sp.getAll());
    }
}
