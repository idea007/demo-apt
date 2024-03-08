package com.example.demo.autoservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.autoservice.inter.ITestService
import com.example.demo.autoservice.inter.IKtTestService
import java.util.ServiceLoader

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ServiceLoader.load(ITestService::class.java).forEach {
            Log.i(TAG, "------ ${it.sayHello()}")
        }

        ServiceLoader.load(IKtTestService::class.java).forEach {
            Log.i(TAG, "------ ${it.sayHello()}")
            Log.i(TAG, "------ ${it.scheme}")
        }
    }
}