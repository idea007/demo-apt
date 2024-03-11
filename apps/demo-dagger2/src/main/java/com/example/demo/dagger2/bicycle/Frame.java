package com.example.demo.dagger2.bicycle;

import android.util.Log;

import javax.inject.Inject;

/**
 * 车架
 *  构造函数是带参数时，或者 Frame 类是我们无法修改时？就需要 @Module 和 @Provide 上场了。
 */
public class Frame {

    private String material;

    public Frame(String material) {
        this.material = material;
    }

    public void print() {
        Log.i("Frame", "------ 车架: " + material);
    }
}
