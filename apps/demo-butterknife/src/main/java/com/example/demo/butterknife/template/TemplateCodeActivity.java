package com.example.demo.butterknife.template;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demo.butterknife.R;


public class TemplateCodeActivity extends AppCompatActivity {
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_code);
        TemplateBinding.bind(this);
        tvText.setText("更新内容");
    }
}