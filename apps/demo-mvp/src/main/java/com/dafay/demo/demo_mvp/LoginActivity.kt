package com.dafay.demo.demo_mvp

import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dafay.demo.lib.base.data.LoggedInfo
import com.dafay.demo.lib.base.databinding.ActivityLoginBinding
import com.dafay.demo.lib.base.ui.base.BaseActivity
import com.dafay.demo.lib.base.utils.hideLoading
import com.dafay.demo.lib.base.utils.showLoading
import com.dafay.demo.lib.base.utils.toast

/**
 * TODO: mvp
 * model（数据层）: JavaBean、数据处理，对应 MockDataSource
 * view（显示层）: 对应 xml 布局
 * presenter（表示器）：对应 presenter
 *
 * 拆分 view 与 controller
 * 面向接口编程，易于单测
 * 问题：定义过多文件，代码分散
 *
 */
class LoginActivity : BaseActivity(com.dafay.demo.lib.base.R.layout.activity_login), ILoginView {
    override val binding: ActivityLoginBinding by viewBinding()

    private lateinit var loginPresenter: ILoginPresenter

    override fun initViews() {
        initPresenter()
    }

    private fun initPresenter() {
        loginPresenter = LoginPresenterImpl(this)
        loginPresenter.init()
    }

    override fun bindListener() {
        binding.btnConfirm.setOnClickListener {
            loginPresenter.login(binding.etAccount.text.toString(), binding.etPwd.text.toString())
        }
    }


    override fun registerEditTextChangeds() {
        binding.etAccount.doAfterTextChanged {
            loginPresenter.checkIsAllowLogin(binding.etAccount.text, binding.etPwd.text)
        }
        binding.etPwd.doAfterTextChanged {
            loginPresenter.checkIsAllowLogin(binding.etAccount.text, binding.etPwd.text)
        }
    }

    override fun updateViews(loggedInfo: LoggedInfo) {
        binding.etAccount.setText(loggedInfo.account)
        binding.etPwd.setText(loggedInfo.pwd)
    }

    override fun showViewLoading() {
        showLoading()
    }

    override fun dismissViewLoading() {
        hideLoading()
    }

    override fun updateConfirmBtn(isEnabled: Boolean) {
        binding.btnConfirm.isEnabled = isEnabled
    }

    override fun showToast(content: String) {
        toast(content)
    }
}