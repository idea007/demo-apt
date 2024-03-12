package com.example.demo.mvp.di.component

import com.dafay.demo.demo_mvp_dagger.LoginActivity
import com.example.demo.mvp.di.module.ActivityLoginModule
import dagger.Component

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/11
 */
@Component(modules = arrayOf(ActivityLoginModule::class))
interface ActivityLoginComponent {

    fun inject(loginActivity: LoginActivity)

}