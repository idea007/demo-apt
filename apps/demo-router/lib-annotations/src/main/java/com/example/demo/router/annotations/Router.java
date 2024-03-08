package com.example.demo.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Router
 * @Des Router 注解
 * @Author lipengfei
 * @Date 2023/8/3 16:14
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Router {
    /**
     * 定义路由模板，可以声明多条
     */
    String[] value();

    /**
     * 优先级，默认 100,越高越优先匹配到
     */
    int priority() default 100;

    /**
     * 声明属于哪个模块，一般只有一个类定义在了别人的模块里才要这样
     */
    String module() default "";
}
