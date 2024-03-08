package com.example.demo.butterknife.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demo.butterknife.R;
import com.example.demo.lib.annotations.BindView;

public class TestAptActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apt);
        Binding.bind(this);
        tvText.setText("更新内容");
    }
}