package com.example.demo.butterknife.reflection;

import android.app.Activity;
import android.util.Log;

import com.example.demo.lib.annotations.ReflectionBindView;

import java.lang.reflect.Field;

public class ReflectionBinding {
    public static String TAG = ReflectionBinding.class.getSimpleName();

    public static void bind(Activity activity) {
        // 反射方式遍历目标类所有 field
        for (Field field : activity.getClass().getDeclaredFields()) {
            // if(field.isAnnotationPresent(ReflectionBindView.class))
            Log.i(TAG, "------ field.getName()"+field.getName());
            ReflectionBindView bindView = field.getAnnotation(ReflectionBindView.class);
            if (bindView != null) {
                try {
                    // 目标类 field 可能设置为 privite/protected，修改可访问性
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
