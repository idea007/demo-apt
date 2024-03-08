package com.example.demo.butterknife.reflection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demo.lib.annotations.ReflectionBindView;
import com.example.demo.butterknife.R;

public class TestReflectionActivity extends AppCompatActivity {

    @ReflectionBindView(R.id.tv_text)
    TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reflection);
        ReflectionBinding.bind(this);
        tvText.setText("更新内容");
    }
}