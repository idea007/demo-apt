package com.example.demo.autoservice.impl

import com.example.demo.autoservice.inter.ITestService
import com.google.auto.service.AutoService

/**
 * https://www.cnblogs.com/aspirant/p/10616704.html
 * kotlin 上使用 @AutoService 注解，需使用 kapt
 */
@AutoService(ITestService::class)
class KotlinTestServiceImpl : ITestService {

    override fun sayHello(): String {
        return "say hello from ${this.javaClass.simpleName}"
    }
}