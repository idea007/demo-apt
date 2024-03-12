package com.dafay.demo.demo_mvvm

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dafay.demo.lib.base.databinding.ActivityLoginBinding
import com.dafay.demo.lib.base.ui.base.BaseActivity
import com.dafay.demo.lib.base.utils.hideLoading
import com.dafay.demo.lib.base.utils.showLoading

/**
 * TODO: m-v-vm
 * model（数据层）: JavaBean、数据处理，对应 MockDataSource
 * view（显示层）: 对应 xml 布局
 * viewmodel（双向绑定框架库）：数据驱动显示
 *
 * 拆分 view 与 controller
 * 使用 databinding 双向绑定
 * 问题：
 * 1. 编译时注解生成代码编译打包慢
 * 2. 数据与布局耦合到一起（这部分代码 apt 帮我们完成了）
 */
class LoginActivity : BaseActivity(com.dafay.demo.lib.base.R.layout.activity_login) {
    override val binding: ActivityLoginBinding by viewBinding()

    private lateinit var loginViewModel: LoginViewModel

    override fun initViews() {
        initViewModel()
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

    private fun initViewModel() {
        loginViewModel = LoginViewModel(binding.etAccount, binding.etPwd)
        loginViewModel.init()
    }

    override fun initObserver() {
        loginViewModel.isShowLoading.onChangeListener = object : ObservablePack.OnChangeListener<Boolean> {
            override fun onChange(isShow: Boolean?) {
                isShow?.let {
                    if (it) showLoading() else hideLoading()
                }
            }
        }
    }

    override fun bindListener() {
        binding.btnConfirm.setOnClickListener {
            loginViewModel.login(
                binding.etAccount.text.toString(),
                binding.etPwd.text.toString()
            )
        }
    }
}