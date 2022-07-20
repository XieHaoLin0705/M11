package com.example.m11.application

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.common.BaiduMapSDKException

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SDKInitializer.setAgreePrivacy(this, true)
        try {
            SDKInitializer.initialize(this)
        } catch (e: BaiduMapSDKException) {
            Log.e(TAG, e.toString())
        }
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }
}