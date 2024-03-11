package com.example.demo.dagger2.bicycle;

import dagger.Component;

/**
 * @Scope 去标注注入器
 */
@GearSystem.BicycleScope
@Component(modules = BicycleModule.class)
public interface BicycleComponent {
    void inject(Bicycle bicycle);
}
