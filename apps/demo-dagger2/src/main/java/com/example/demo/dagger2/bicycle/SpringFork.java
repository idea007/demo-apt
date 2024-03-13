package com.example.demo.dagger2.bicycle;

import android.util.Log;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/13
 */
public class SpringFork implements IFork{
    @Override
    public void print() {
        Log.i("SpringFork", "------ 前叉: 弹簧前叉");
    }
}
