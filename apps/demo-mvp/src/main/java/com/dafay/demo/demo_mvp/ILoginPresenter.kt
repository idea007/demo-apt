package com.example.demo.mvp

import android.text.Editable

/**
 * @ClassName LoginPresenter
 * @Des
 * @Author lipengfei
 * @Date 2023/7/2 23:32
 */
interface ILoginPresenter {
    fun init()

    fun checkIsAllowLogin(text: Editable, text1: Editable)

    fun login(account: String, pwd: String)
}