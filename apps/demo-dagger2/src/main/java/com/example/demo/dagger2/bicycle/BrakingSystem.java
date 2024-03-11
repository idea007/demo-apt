package com.example.demo.dagger2.bicycle;

import android.util.Log;

import javax.inject.Inject;

/**
 * 自行车刹车系统
 */
public class BrakingSystem {

    /**
     * @Inject 有两个作用,二是注解可以在需要这个类实例的时候来找到这个构造函数并把相关实例构造出来，以此来为被 @Inject 标记了的变量提供依赖
     */
    @Inject
    public BrakingSystem() {
    }

    public void print() {
        Log.i("", "------ 刹车系统: 碟刹");
    }
}
