package com.example.demo.dagger2.bicycle.di.module;

import com.example.demo.dagger2.bicycle.AitFork;
import com.example.demo.dagger2.bicycle.Frame;
import com.example.demo.dagger2.bicycle.GearSystem;
import com.example.demo.dagger2.bicycle.IFork;
import com.example.demo.dagger2.bicycle.Shifter;
import com.example.demo.dagger2.bicycle.SpringFork;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * BicycleModule 类来生成依赖对象。@Module 就是用来标注这个类的，@Provide 则是用来标注具体提供依赖对象的方法（这里有个不成文的规定，被@Provide标注的方法命名我们一般以provide开头，这并不是强制的但有益于提升代码的可读性）。
 */
@Module
public class BicycleModule {

    public BicycleModule() {
    }

    /**
     * @Provides 用于标注 Module 所标注的类中的方法，该方法在需要提供依赖时被调用，从而把预先提供好的对象当做依赖给标注了 @Inject 的变量赋值
     */
    @Provides
    public Frame provideFrame() {
        return new Frame("铝合金");
    }


    @Shifter.QualifierFrontShifter
    @Provides
    Shifter provideFrontShifter(){
        return new Shifter("前拨");
    }

    @Shifter.QualifierBackShifter
    @Provides
    Shifter provideBackShifter(){
        return new Shifter("后拨");
    }


    /**
     * 2. @BicycleScope 去标记依赖提供方 BicycleModule
     */
    @GearSystem.BicycleScope
    @Provides
    GearSystem provideGearSystem(){
        return new GearSystem("禧玛诺");
    }

    /**
     * @Named 注解用于标识依赖项，以便在需要提供多个相同类型但不同实例的依赖项时进行区分
     */
    @Provides
    @Named("Air Fork")
    IFork provideAirFork(){return new AitFork();}

    @Provides
    @Named("Spring Fork")
    IFork provideSpringFork(){return new SpringFork();}

}
