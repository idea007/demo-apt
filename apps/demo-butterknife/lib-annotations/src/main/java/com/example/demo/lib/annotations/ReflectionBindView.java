package com.example.demo.lib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保留策略（Retention）： RetentionPolicy.RUNTIME 运行阶段保留
 * 作用对象（Target）：ElementType.FIELD 成员变量
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReflectionBindView {
    int value();
}
