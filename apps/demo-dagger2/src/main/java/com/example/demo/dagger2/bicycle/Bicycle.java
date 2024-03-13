package com.example.demo.dagger2.bicycle;


import com.example.demo.dagger2.bicycle.di.component.DaggerBicycleComponent;
import com.example.demo.dagger2.bicycle.di.module.BicycleModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 自行车
 * Frame: 车架
 * BrakingSystem: 刹车系统
 * GearSystem: 变速器
 * Shifter: 指拨
 * Fork: 前叉
 */
public class Bicycle {

    /**
     * @Inject 有两个作用, 一是用来标记需要依赖项的变量，告诉 Dagger2 为此变量提供依赖
     */
    @Inject
    public BrakingSystem brakingSystem;

    @Inject
    public Frame frame;

    @Shifter.QualifierFrontShifter
    @Inject
    public Shifter frontShifter;

    @Shifter.QualifierBackShifter
    @Inject
    public Shifter backShifter;

    @Inject
    public GearSystem gearSystem1;

    @Inject
    public GearSystem gearSystem2;

    /**
     * @Named 注解在 Dagger 2 中区分不同实例的依赖项，从而实现更灵活的依赖注入。
     */
    @Inject
    @Named("Air Fork")
    public IFork fork;

    public Bicycle() {
        // 告诉了注入器 DaggerBicycleComponent 把 BicycleModule 提供的依赖注入到了 Bicycle 类中
        DaggerBicycleComponent.builder().bicycleModule(new BicycleModule()).build().inject(this);
    }
}
