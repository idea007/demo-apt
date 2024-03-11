package com.dafay.demo.demo_mvc

import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dafay.demo.lib.base.data.MockDataSource
import com.dafay.demo.lib.base.databinding.ActivityLoginBinding
import com.dafay.demo.lib.base.ui.base.BaseActivity
import com.dafay.demo.lib.base.utils.debug
import com.dafay.demo.lib.base.utils.error
import com.dafay.demo.lib.base.utils.hideLoading
import com.dafay.demo.lib.base.utils.showLoading
import com.dafay.demo.lib.base.utils.toast

class LoginActivity : BaseActivity(com.dafay.demo.lib.base.R.layout.activity_login) {
    override val binding: ActivityLoginBinding by viewBinding()

    override fun initViews() {
        val lastLoggedInfo = MockDataSource.getLastLoggedInfo()
        binding.etAccount.setText(lastLoggedInfo.account)
        binding.etPwd.setText(lastLoggedInfo.pwd)
        binding.etAccount.doAfterTextChanged {
            checkConfirmBtn()
        }
        binding.etPwd.doAfterTextChanged {
            checkConfirmBtn()
        }
    }

    private fun checkConfirmBtn() {
        if (binding.etAccount.text.isNullOrBlank() || binding.etPwd.text.isNullOrBlank()) {
            binding.btnConfirm.isEnabled = false
        } else {
            binding.btnConfirm.isEnabled = true
        }
    }

    override fun bindListener() {
        binding.btnConfirm.setOnClickListener {
            showLoading()
            MockDataSource.login(
                binding.etAccount.text.toString(),
                binding.etPwd.text.toString(),
                object : MockDataSource.RequestCallback {
                    override fun onSuccess() {
                        hideLoading()
                        debug("login success")
                        toast("登录成功")
                    }

                    override fun onFailure() {
                        hideLoading()
                        error("login failure")
                        toast("登录失败")
                    }
                })
        }
    }
}