package com.example.demo.autoservice.impl

import com.example.demo.autoservice.inter.IKtTestService
import com.google.auto.service.AutoService

/**
 * https://www.cnblogs.com/aspirant/p/10616704.html
 * kotlin 上使用 @AutoService 注解，解析器需使用 kapt
 */
@AutoService(IKtTestService::class)
class LocalKtTestService : IKtTestService {
    override fun sayHello(): String {
        return "say hello from ${this.javaClass.simpleName}"
    }

    override var scheme: String?
        get() = "scheme  from ${this.javaClass.simpleName}"
        set(value) {
            this.scheme = value
        }
}