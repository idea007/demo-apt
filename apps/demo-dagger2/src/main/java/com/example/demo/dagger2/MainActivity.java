package com.example.demo.dagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.demo.dagger2.bicycle.Bicycle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bicycle myBicycle = new Bicycle();
        myBicycle.brakingSystem.print();
        myBicycle.frame.print();
        myBicycle.fork.print();
        myBicycle.frontShifter.print();
        myBicycle.backShifter.print();
        Log.i("MainActivity", "------ gearSystem1: " + myBicycle.gearSystem1 + " gearSystem2: " + myBicycle.gearSystem2);
    }
}