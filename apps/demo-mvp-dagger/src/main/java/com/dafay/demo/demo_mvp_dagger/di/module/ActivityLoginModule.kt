package com.example.demo.mvp.di.module

import android.app.Activity
import com.dafay.demo.demo_mvp_dagger.ILoginPresenter
import com.dafay.demo.demo_mvp_dagger.LoginActivity
import com.dafay.demo.demo_mvp_dagger.LoginPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/11
 */
@Module
class ActivityLoginModule(private var loginActivity: LoginActivity) {

    @Provides
    fun provideLoginPresenter(): ILoginPresenter {
        return LoginPresenterImpl(loginActivity)
    }

}