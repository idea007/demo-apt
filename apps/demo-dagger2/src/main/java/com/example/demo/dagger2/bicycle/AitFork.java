package com.example.demo.dagger2.bicycle;

import android.util.Log;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/13
 */
public class AitFork implements IFork{
    @Override
    public void print() {
        Log.i("AitFork", "------ 前叉: 气压前叉");
    }
}
