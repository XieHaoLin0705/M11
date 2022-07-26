package com.example.m11.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.m11.R

open class BaseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initView()
        initData()
        onClick()
    }
    open fun initView() {}
    open fun initData() {}
    open fun onClick() {}
    open fun intentActivity(fromActivity: Activity?, toActivity: Class<*>?) {
        val intent = Intent(fromActivity, toActivity)
        startActivity(intent)
    }
}