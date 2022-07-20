package com.example.m11.activities

import android.annotation.SuppressLint
import android.view.*
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m11.R
import com.example.m11.adapter.MyQueryResultAdapter
import com.example.m11.bean.QueryResultInfo
import com.example.m11.db.QuerySqlOP
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popwindow_choose.view.*
import kotlinx.android.synthetic.main.title_layout.*

class MainActivity : BaseActivity() {

    private var queryResultList = mutableListOf<QueryResultInfo>()
    override fun baseSetView(id: Int) {
        super.baseSetView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initQueryList()
    }
    private fun initQueryList() {
        val linearLayoutManager = LinearLayoutManager(this)
        queryResultList = QuerySqlOP(this@MainActivity).queryAllResult()
        rv_query_result.layoutManager = linearLayoutManager
        rv_query_result.adapter = MyQueryResultAdapter(queryResultList)
    }
    override fun onClick() {
        img_title_bar_back.setOnClickListener { intentActivity(this@MainActivity, CheckWorkManageActivity::class.java) }
        img_add.setOnClickListener { intentActivity(this@MainActivity, AddInfoActivity::class.java) }
        img_title_bar_choose.setOnClickListener { openPopWindow() }
    }
    private fun openPopWindow() {
        val mWindow = window
        val params = mWindow.attributes
        params.alpha = 0.76f
        mWindow.attributes = params
        @SuppressLint("InflateParams") val productListView: View = LayoutInflater.from(this).inflate(R.layout.popwindow_choose, null)
        val popupWindow = PopupWindow(productListView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.setBackgroundDrawable(null)
        popupWindow.isFocusable = true
        popupWindow.setOnDismissListener {
            params.alpha = 1f
            mWindow.attributes = params
        }
        popupWindow.showAtLocation(productListView.ll_popWindow, Gravity.CENTER, 0, 0)
        popupWindowOperation(productListView, popupWindow, mWindow, params)
    }

    private fun popupWindowOperation(productListView: View, popupWindow: PopupWindow, mWindow: Window, params: WindowManager.LayoutParams) {

        var companyTotal = ""
        var illegalTotal = ""
        productListView.cb_pop_all_company.setOnCheckedChangeListener { _, b ->
            if (b) {
                productListView.cb_pop_first_company.isChecked = false
                productListView.cb_pop_second_company.isChecked = false
                productListView.cb_pop_third_company.isChecked = false
                productListView.cb_pop_forth_company.isChecked = false
                productListView.tv_pop_company_total.text = "全部"
            }else{
                productListView.tv_pop_company_total.text = ""
            }
        }
        productListView.cb_pop_first_company.setOnCheckedChangeListener{ _, b ->
            if (b) {
                productListView.cb_pop_all_company.isChecked = false
                companyTotal += "第一客运分公司/"
//                companyTotal = judgeLong(companyTotal)
                productListView.tv_pop_company_total.text = companyTotal
            } else {
                companyTotal = companyTotal.replace("第一客运分公司/","")
                productListView.tv_pop_company_total.text = companyTotal
            }
        }
        productListView.cb_pop_second_company.setOnCheckedChangeListener{ _, b ->
            if (b) {
                productListView.cb_pop_all_company.isChecked = false
                companyTotal += "第二客运分公司/"
                productListView.tv_pop_company_total.text = companyTotal
            }else {
                companyTotal = companyTotal.replace("第二客运分公司/","")
                productListView.tv_pop_company_total.text = companyTotal
            }
        }
        productListView.cb_pop_third_company.setOnCheckedChangeListener{ _, b ->
            if (b) {
                productListView.cb_pop_all_company.isChecked = false
                companyTotal += "第三客运分公司/"
                productListView.tv_pop_company_total.text = companyTotal
            }else {
                companyTotal = companyTotal.replace("第三客运分公司/","")
                productListView.tv_pop_company_total.text = companyTotal
            }
        }
        productListView.cb_pop_forth_company.setOnCheckedChangeListener{ _, b ->
            if (b) {
                productListView.cb_pop_all_company.isChecked = false
                companyTotal += "第四客运分公司/"
                productListView.tv_pop_company_total.text = companyTotal
            }else {
                companyTotal = companyTotal.replace("第四客运分公司/","")
                productListView.tv_pop_company_total.text = companyTotal
            }
        }
        productListView.cb_pop_illegal_change_equipment.setOnCheckedChangeListener{ _, b ->
            if (b) {
                illegalTotal += "调整信息化设备，"
                productListView.tv_pop_illegal_total.text = illegalTotal
            }else {
                illegalTotal = illegalTotal.replace("调整信息化设备，","")
                productListView.tv_pop_illegal_total.text = illegalTotal
            }
        }

        productListView.cb_pop_illegal_video.setOnCheckedChangeListener{ _, b ->
            if (b) {
                illegalTotal += "饰品佩戴，"
                productListView.tv_pop_illegal_total.text = illegalTotal
            }else {
                illegalTotal = illegalTotal.replace("饰品佩戴，","")
                productListView.tv_pop_illegal_total.text = illegalTotal
            }
        }
        productListView.cb_pop_illegal_against_safe.setOnCheckedChangeListener{ _, b ->
            if (b) {
                illegalTotal += "违反安全行驶，"
                productListView.tv_pop_illegal_total.text = illegalTotal
            }else {
                illegalTotal = illegalTotal.replace("违反安全行驶，","")
                productListView.tv_pop_illegal_total.text = illegalTotal
            }
        }
        productListView.cb_pop_illegal_culture_behavior.setOnCheckedChangeListener{ _, b ->
            if (b) {
                illegalTotal += "文明行为，"
                productListView.tv_pop_illegal_total.text = illegalTotal
            }else {
                illegalTotal = illegalTotal.replace("文明行为，","")
                productListView.tv_pop_illegal_total.text = illegalTotal
            }
        }
        productListView.cb_pop_illegal_change_equipment_2.setOnCheckedChangeListener{ _, b ->
            if (b) {
                illegalTotal += "调整信息化设备2，"
                productListView.tv_pop_illegal_total.text = illegalTotal
            }else {
                illegalTotal = illegalTotal.replace("调整信息化设备2，","")
                productListView.tv_pop_illegal_total.text = illegalTotal
            }
        }
        val firstCompanyList = mutableListOf<String>()
        for (i in queryResultList.indices) {
            firstCompanyList.add(queryResultList[i].department)
            firstCompanyList.add(queryResultList[i].category)
            firstCompanyList.add(queryResultList[i].state)
        }
        if (firstCompanyList.contains("第一客运分公司")) {
            productListView.cb_pop_first_company.isChecked = true
        }
        if (firstCompanyList.contains("第二客运分公司")) {
            productListView.cb_pop_second_company.isChecked = true
        }
        if (firstCompanyList.contains("第三客运分公司")) {
            productListView.cb_pop_third_company.isChecked = true
        }
        if (firstCompanyList.contains("第四客运分公司")) {
            productListView.cb_pop_forth_company.isChecked = true
        }
        if (firstCompanyList.contains("文明行为")) {
            productListView.cb_pop_illegal_culture_behavior.isChecked = true
        }
        if (firstCompanyList.contains("饰品佩戴")) {
            productListView.cb_pop_illegal_video.isChecked = true
        }
        if (firstCompanyList.contains("调整信息化设备")) {
            productListView.cb_pop_illegal_change_equipment.isChecked = true
            productListView.cb_pop_illegal_change_equipment_2.isChecked = true
        }
        if (firstCompanyList.contains("违反安全行驶")) {
            productListView.cb_pop_illegal_against_safe.isChecked = true
        }
        if (firstCompanyList.contains("待反馈")) {
            productListView.cb_pop_state_wait_feedback.isChecked = true
        }
        if (firstCompanyList.contains("已完成")) {
            productListView.cb_pop_state_complete.isChecked = true
        }
        productListView.tv_pop_reset.setOnClickListener {
            productListView.cb_pop_all_company.isChecked = false
            productListView.cb_pop_first_company.isChecked = false
            productListView.cb_pop_second_company.isChecked = false
            productListView.cb_pop_third_company.isChecked = false
            productListView.cb_pop_forth_company.isChecked = false
            productListView.cb_pop_illegal_change_equipment.isChecked = false
            productListView.cb_pop_illegal_change_equipment_2.isChecked = false
            productListView.cb_pop_illegal_video.isChecked = false
            productListView.cb_pop_illegal_against_safe.isChecked = false
            productListView.cb_pop_illegal_culture_behavior.isChecked = false
            productListView.cb_pop_state_complete.isChecked = false
            productListView.cb_pop_state_wait_feedback.isChecked = false
        }
        productListView.tv_pop_sure.setOnClickListener {
            val companyChooseList = mutableListOf<String>()
            val illegalChooseList = mutableListOf<String>()
            val stateChooseList = mutableListOf<String>()
            val allCompanyIsChecked = productListView.cb_pop_all_company.isChecked
            val firstCompanyIsChecked = productListView.cb_pop_first_company.isChecked
            val secondCompanyIsChecked = productListView.cb_pop_second_company.isChecked
            val thirdCompanyIsChecked = productListView.cb_pop_third_company.isChecked
            val forthCompanyIsChecked = productListView.cb_pop_forth_company.isChecked
            val illegalChangeEquipmentIsChecked = productListView.cb_pop_illegal_change_equipment.isChecked
            val illegalChangeEquipment2IsChecked = productListView.cb_pop_illegal_change_equipment_2.isChecked
            val illegalVideoIsChecked = productListView.cb_pop_illegal_video.isChecked
            val illegalAgainstSafeIsChecked = productListView.cb_pop_illegal_against_safe.isChecked
            val illegalCultureBehaviorIsChecked = productListView.cb_pop_illegal_culture_behavior.isChecked
            val stateWaitFeedbackIsChecked = productListView.cb_pop_state_wait_feedback.isChecked
            val stateCompleteIsChecked = productListView.cb_pop_state_complete.isChecked
            if (allCompanyIsChecked){
                companyChooseList.add("all")
            } else{
                if (firstCompanyIsChecked) {
                    companyChooseList.add("第一客运分公司")
                }
                if (secondCompanyIsChecked) {
                    companyChooseList.add("第二客运分公司")
                }
                if (thirdCompanyIsChecked) {
                    companyChooseList.add("第三客运分公司")
                }
                if (forthCompanyIsChecked) {
                    companyChooseList.add("第四客运分公司")
                }
            }
            if (illegalChangeEquipmentIsChecked || illegalChangeEquipment2IsChecked) {
                illegalChooseList.add("调整信息化设备")
            }
            if (illegalVideoIsChecked) {
                illegalChooseList.add("饰品佩戴")
            }
            if (illegalAgainstSafeIsChecked) {
                illegalChooseList.add("违反安全行驶")
            }
            if (illegalCultureBehaviorIsChecked) {
                illegalChooseList.add("文明行为")
            }
            if (stateWaitFeedbackIsChecked) {
                stateChooseList.add("待反馈")
            }
            if (stateCompleteIsChecked) {
                stateChooseList.add("已完成")
            }

            val queryAllResultList = QuerySqlOP(this@MainActivity).queryAllResult()
            queryResultList = QuerySqlOP(this@MainActivity).queryChooseResult(queryAllResultList ,companyChooseList, illegalChooseList, stateChooseList)
            rv_query_result.adapter = MyQueryResultAdapter(queryResultList)
            popupWindow.dismiss()
            params.alpha = 1f
            mWindow.attributes = params
        }
    }

    private fun judgeLong(companyTotal: String): String {
        var newCompanyTotal = companyTotal
        if (companyTotal.length < 9) {
            newCompanyTotal = companyTotal.replace("/", "")
        }
        return newCompanyTotal
    }

}