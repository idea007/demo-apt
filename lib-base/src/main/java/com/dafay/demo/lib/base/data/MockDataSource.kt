package com.dafay.demo.lib.base.data

import android.os.Handler
import android.os.Looper
import com.dafay.demo.lib.base.utils.debug
import java.util.Random
import java.util.concurrent.Executors

/**
 * @ClassName MockDataSource
 * @Des 模拟数据源
 */
object MockDataSource {

    val singleThreadExecutor = Executors.newSingleThreadExecutor()

    val mainHandler = Handler(Looper.getMainLooper())

    /**
     * 获取上次登录的信息
     */
    fun getLastLoggedInfo(): LoggedInfo {
        return LoggedInfo("上山打老虎", "12345")
    }

    fun login(account: String, pwd: String, callback: RequestCallback) {
        singleThreadExecutor.execute({
            Thread.sleep(1000)
            val randomNum = Random().nextInt(100)
            debug("randomNum:${randomNum}")
            if (randomNum % 2 != 0) {
                mainHandler.post({
                    callback.onSuccess()
                })
            } else {
                mainHandler.post({
                    callback.onFailure()
                })
            }
        })
    }


    interface RequestCallback {
        fun onSuccess()
        fun onFailure()
    }
}