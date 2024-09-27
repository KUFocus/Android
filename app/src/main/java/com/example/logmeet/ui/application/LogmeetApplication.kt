package com.example.logmeet.ui.application

import android.app.Application
import android.util.Log
import com.example.logmeet.domain.repository.DataStoreModule

class LogmeetApplication : Application() {
    private lateinit var dataStore: DataStoreModule

    override fun onCreate() {
        super.onCreate()
        Log.d("LifeCycle", "CoNetApplication - onCreate() called")
        instance = this
        dataStore = DataStoreModule(this)

//        KakaoSdk.init(
//            context = this,
//            appKey = BuildConfig.KAKAO_APP_KEY,
//        )
    }

    fun getDataStore(): DataStoreModule {
        return dataStore
    }

    companion object {
        private lateinit var instance: LogmeetApplication
        fun getInstance(): LogmeetApplication {
            return instance
        }
    }
}