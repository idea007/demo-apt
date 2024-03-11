package com.example.demo.mvp

import com.dafay.demo.lib.base.data.LoggedInfo

/**
 * @ClassName ILoginView
 * @Des
 * @Author lipengfei
 * @Date 2023/7/2 23:33
 */
interface ILoginView {
    fun registerEditTextChangeds()

    fun updateViews(loggedInfo: LoggedInfo)

    fun showViewLoading()

    fun dismissViewLoading()

    fun updateConfirmBtn(isEnabled: Boolean)
}