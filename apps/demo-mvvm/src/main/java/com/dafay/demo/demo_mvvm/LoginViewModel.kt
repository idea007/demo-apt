package com.dafay.demo.demo_mvvm

import android.widget.EditText
import com.dafay.demo.lib.base.data.MockDataSource
import com.dafay.demo.lib.base.utils.debug
import com.dafay.demo.lib.base.utils.error

/**
 * @ClassName LoginViewModel
 * @Des TODO: 模拟双向绑定的 "ViewModel"，是 m-v-vm 里面的 "vm"，跟 android 的 ViewModel 完全没有关系
 * 实现双向绑定，需要借助
 * 1. 可观察的数据对象 （此例中 ObservablePack)
 * 2. 视图与数据的绑定  (此例中 ViewBinder)
 * @Author lipengfei
 * @Date 2023/7/3 09:40
 */
class LoginViewModel(etAccount: EditText, etPwd: EditText) {
    var account = ObservablePack<String>()
    var pwd = ObservablePack<String>()
    var isShowLoading = ObservablePack<Boolean>()

    init {
        ViewBinder.bind(etAccount, account)
        ViewBinder.bind(etPwd, pwd)
    }

    fun init() {
        val lastLoggedInfo = MockDataSource.getLastLoggedInfo()
        account.value = lastLoggedInfo.account
        pwd.value = lastLoggedInfo.pwd
    }

    fun login(account: String, pwd: String) {
        isShowLoading.value = true
        MockDataSource.login(account, pwd,
            object : MockDataSource.RequestCallback {
                override fun onSuccess() {
                    isShowLoading.value = false
                    debug("login success")
                }

                override fun onFailure() {
                    isShowLoading.value = false
                    error("login failure")
                }
            })

    }
}