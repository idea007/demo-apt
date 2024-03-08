package com.example.demo.butterknife.template;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * 调用模板代码
 */
public class TemplateBinding {

    public static void bind(Activity activity) {
        // 使用 getCanonicalName() 基础类的规范名称，避免匿名内部类等带来的影响
        String templateClassName = activity.getClass().getCanonicalName() + "Binding";
        try {
            Class bindClass = Class.forName(templateClassName);
            Constructor constructor = bindClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
