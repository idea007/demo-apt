package com.dafay.demo.demo_mvvm

/**
 * 模拟 livedata，数据变化时进行回调
 */
class ObservablePack<T> {
    var value: T? = null
        set(value) {
            field = value
            onChangeListener?.onChange(value)
        }

    var onChangeListener: OnChangeListener<T>? = null

    interface OnChangeListener<T> {
        fun onChange(newValue: T?)
    }
}