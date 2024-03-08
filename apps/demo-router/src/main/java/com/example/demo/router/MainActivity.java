package com.example.demo.router;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demo.router.model.ResolveResult;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResolveResult resolveResult = RouterManager.instance.resolve("scheme://test_clazz/123456/answer/654321");
                Log.i(TAG, "------ resolveResult.target=" + resolveResult);
            }
        });
    }
}