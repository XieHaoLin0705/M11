package com.example.m11.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.*
import android.view.View.VISIBLE
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m11.R
import com.example.m11.adapter.CheckWorkAdapter
import com.example.m11.bean.CheckWorkInfo
import com.example.m11.db.QuerySqlOP
import kotlinx.android.synthetic.main.activity_check_work_manage.*
import kotlinx.android.synthetic.main.popwindow_choose.view.*
import kotlinx.android.synthetic.main.popwindow_choose_line.view.*
import kotlinx.android.synthetic.main.title_layout.*

class CheckWorkManageActivity : BaseActivity() {

    private var checkWorkInfoList = mutableListOf<CheckWorkInfo>()

    override fun baseSetView(id: Int) {
        super.baseSetView(R.layout.activity_check_work_manage)
    }

    override fun initView() {
        ll_check_work_title_private.visibility = VISIBLE
        tv_title_bar_title.text = "考勤状态"
        img_title_bar_choose.setImageResource(R.drawable.icon_title_search)
        initCheckStateResultList()
    }

    override fun initData() {
        val shouldSignInNumber = checkWorkInfoList.size
        var beLateNumber = 0
        var wineNoPassNumber = 0
        var trainNoPassNumber = 0
        var alreadySignInNumber = 0
        for (i in checkWorkInfoList.indices) {
            if (checkWorkInfoList[i].signInState == "迟到") {
                beLateNumber++
            }
            if (checkWorkInfoList[i].wineCheckState == "不通过") {
                wineNoPassNumber++
            }
            if (checkWorkInfoList[i].trainCheckState == "不通过") {
                trainNoPassNumber++
            }
            if ((checkWorkInfoList[i].signInState != "未签到")) {
                alreadySignInNumber++
            }
        }
        tv_check_work_total_late_number.text = beLateNumber.toString()
        tv_check_work_total_wine_number.text = wineNoPassNumber.toString()
        tv_check_work_total_check_no_pass_number.text = trainNoPassNumber.toString()
        tv_check_work_total_already_signIn_number.text = alreadySignInNumber.toString()
        tv_check_work_total_should_signIn_number.text = shouldSignInNumber.toString()
        tv_check_work_total_no_signIn_number.text = (shouldSignInNumber - alreadySignInNumber).toString()
        if (tv_check_work_total_late_number.text == "0") {
            tv_check_work_total_late_number.setTextColor(Color.parseColor("#8C000000"))
        } else {
            tv_check_work_total_late_number.setTextColor(Color.parseColor("#2B6BFE"))
        }
        if (tv_check_work_total_wine_number.text == "0") {
            tv_check_work_total_wine_number.setTextColor(Color.parseColor("#8C000000"))
        } else {
            tv_check_work_total_wine_number.setTextColor(Color.parseColor("#2B6BFE"))
        }
        if (tv_check_work_total_check_no_pass_number.text == "0") {
            tv_check_work_total_check_no_pass_number.setTextColor(Color.parseColor("#8C000000"))
        } else {
            tv_check_work_total_check_no_pass_number.setTextColor(Color.parseColor("#2B6BFE"))
        }
        if (tv_check_work_total_no_signIn_number.text == "0") {
            tv_check_work_total_no_signIn_number.setTextColor(Color.parseColor("#8C000000"))
        } else {
            tv_check_work_total_no_signIn_number.setTextColor(Color.parseColor("#2B6BFE"))
        }
    }

    override fun onClick() {
        img_title_bar_choose.setOnClickListener { openPopWindow() }
    }

    private fun openPopWindow() {
        val mWindow = window
        val params = mWindow.attributes
        params.alpha = 0.76f
        mWindow.attributes = params
        @SuppressLint("InflateParams") val productListView: View = LayoutInflater.from(this).inflate(R.layout.popwindow_choose_line, null)
        val popupWindow = PopupWindow(productListView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.setBackgroundDrawable(null)
        popupWindow.isFocusable = true
        popupWindow.setOnDismissListener {
            params.alpha = 1f
            mWindow.attributes = params
        }
        popupWindow.showAtLocation(productListView.rl_popWindow_choose_line, Gravity.NO_GRAVITY, 0, 0)
        popupWindowOperation(productListView, popupWindow, mWindow, params)
    }

    private fun popupWindowOperation(productListView: View, popupWindow: PopupWindow, mWindow: Window, params: WindowManager.LayoutParams) {
        val lineList = mutableListOf<String>()
        for (i in checkWorkInfoList.indices) {
            lineList.add(checkWorkInfoList[i].trainLine)
        }
        if (lineList.contains("104")) {
            productListView.cb_pop_search_line_104.isChecked = true
        }
        if (lineList.contains("321")) {
            productListView.cb_pop_search_line_321.isChecked = true
        }
        if (lineList.contains("24")) {
            productListView.cb_pop_search_line_24.isChecked = true
        }
        
        productListView.tv_pop_search_line_sure.setOnClickListener {
            val isChecked24  = productListView.cb_pop_search_line_24.isChecked
            val isChecked104  = productListView.cb_pop_search_line_104.isChecked
            val isChecked321  = productListView.cb_pop_search_line_321.isChecked
            val checkWorkAllInfoList = QuerySqlOP(this@CheckWorkManageActivity).queryAllCheckWork()
            checkWorkInfoList= QuerySqlOP(this@CheckWorkManageActivity).queryPartCheckWorkWithLine(checkWorkAllInfoList, isChecked24, isChecked104, isChecked321)
            initData()
            rv_check_work_state_result.adapter = CheckWorkAdapter(checkWorkInfoList)
            popupWindow.dismiss()
            params.alpha = 1f
            mWindow.attributes = params
        }
        productListView.tv_pop_search_line_cancel.setOnClickListener {
            popupWindow.dismiss()
            params.alpha = 1f
            mWindow.attributes = params
        }
    }
    private fun initCheckStateResultList() {
        val linearLayoutManager = LinearLayoutManager(this)
        checkWorkInfoList = QuerySqlOP(this@CheckWorkManageActivity).queryAllCheckWork()
        rv_check_work_state_result.layoutManager = linearLayoutManager
        rv_check_work_state_result.adapter = CheckWorkAdapter(checkWorkInfoList)
    }

}