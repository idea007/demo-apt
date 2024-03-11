package com.example.demo.dagger2.bicycle;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/11
 */
public class GearSystem {

    /**
     * @Scope 定义一个 BicycleScope 注解
     */
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BicycleScope {
    }

    private String name;

    GearSystem(String name) {
        this.name = name;
    }

    public void print() {
        Log.i("GearSystem", "------ 变速器: " + name);
    }

}
