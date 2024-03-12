package com.dafay.demo.demo_mvp_dagger

import android.text.Editable
import com.dafay.demo.lib.base.data.MockDataSource

/**
 * @ClassName LoginPresenterImpl
 * @Des
 * @Author lipengfei
 * @Date 2023/7/2 23:35
 */
class LoginPresenterImpl(val iLoginView: ILoginView) : ILoginPresenter {
    override fun init() {
        val lastLoggedInfo = MockDataSource.getLastLoggedInfo()
        iLoginView.updateViews(lastLoggedInfo)
        iLoginView.registerEditTextChangeds()
    }

    override fun checkIsAllowLogin(account: Editable, pwd: Editable) {
        if (account.isNullOrBlank() || pwd.isNullOrBlank()) {
            iLoginView.updateConfirmBtn(false)
        } else {
            iLoginView.updateConfirmBtn(true)
        }
    }

    override fun login(account: String, pwd: String) {
        iLoginView.showViewLoading()
        MockDataSource.login(account, pwd, object : MockDataSource.RequestCallback {
            override fun onSuccess() {
                iLoginView.dismissViewLoading()
                iLoginView.showToast("登录成功")
                // 显示 toast 也要交由 iView 去显示
            }

            override fun onFailure() {
                iLoginView.dismissViewLoading()
                iLoginView.showToast("登录失败")
            }
        })
    }
}