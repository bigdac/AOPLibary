package com.li.aoplibary;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.li.mlibrary.aop.CheckNetWork;
import com.li.mlibrary.aop.astClick;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(this);

    }

    @Override
    @CheckNetWork()
    @astClick(3000)
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }
}
