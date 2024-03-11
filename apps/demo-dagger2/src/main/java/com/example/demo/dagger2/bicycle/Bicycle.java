package com.example.demo.dagger2.bicycle;


import javax.inject.Inject;

/**
 * 自行车
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

    public Bicycle() {
        // 告诉了注入器 DaggerBicycleComponent 把 BicycleModule 提供的依赖注入到了 Bicycle 类中
        DaggerBicycleComponent.builder().bicycleModule(new BicycleModule()).build().inject(this);
    }
}
