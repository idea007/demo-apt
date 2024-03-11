package com.example.demo.dagger2.bicycle;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 指拨
 * 用于自定义注解，也就是说 @Qulifier 就如同Java提供的几种基本元注解一样用来标记注解类。
 * 我们在使用 @Module 来标注提供依赖的方法时，方法名我们是可以随便定义的（虽然我们定义方法名一般以provide开头，但这并不是强制的，只是为了增加可读性而已）。
 * 那么 Dagger2 怎么知道这个方法是为谁提供依赖呢？答案就是返回值的类型，
 * Dagger2根据返回值的类型来决定为哪个被@Inject标记了的变量赋值。
 * 但是问题来了，一旦有多个一样的返回类型 Dagger2 就不知道具体该使用那个了。
 * @Qulifier 的存在正式为了解决这个问题，我们使用 @Qulifier 来定义自己的注解，
 * 然后通过自定义的注解去标注提供依赖的方法和依赖需求方（也就是被 @Inject 标注的变量），
 * 这样 Dagger2 就知道为谁提供依赖了。（一个更为精简的定义：当类型不足以鉴别一个依赖的时候，我们就可以使用这个注解标示）
 */
public class Shifter {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierFrontShifter { }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierBackShifter { }

    private String name;

    public Shifter(String name) {
        this.name = name;
    }

    public void print() {
        Log.i("Shifter", "------ 指拨: " + name);
    }
}
