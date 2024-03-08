package com.example.demo.butterknife.template;


import com.example.demo.butterknife.R;

/**
 * 需要被生成的模板代码
 */
public class TemplateCodeActivityBinding {

    public TemplateCodeActivityBinding(TemplateCodeActivity activity) {
        activity.tvText = activity.findViewById(R.id.tv_text);
    }
}
