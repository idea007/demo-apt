package com.dafay.demo.demo_mvvm

import android.text.TextUtils
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

/**
 * 模拟 databinder，进行双向驱动
 */
object ViewBinder {
    fun bind(editText: EditText, observablePack: ObservablePack<String>) {
        editText.doAfterTextChanged {
            if (!TextUtils.equals(observablePack.value, it)) {
                observablePack.value = it.toString()
            }
        }
        observablePack.onChangeListener = object : ObservablePack.OnChangeListener<String> {
            override fun onChange(newValue: String?) {
                if (!TextUtils.equals(newValue, editText.text)) {
                    editText.setText(newValue)
                }
            }
        }
    }
}