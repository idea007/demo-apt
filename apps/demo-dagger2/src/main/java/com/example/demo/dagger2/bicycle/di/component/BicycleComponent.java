package com.example.demo.dagger2.bicycle.di.component;

import com.example.demo.dagger2.bicycle.Bicycle;
import com.example.demo.dagger2.bicycle.GearSystem;
import com.example.demo.dagger2.bicycle.di.module.BicycleModule;

import dagger.Component;

/**
 * @Scope 去标注注入器
 */
@GearSystem.BicycleScope
@Component(modules = BicycleModule.class)
public interface BicycleComponent {
    void inject(Bicycle bicycle);
}
