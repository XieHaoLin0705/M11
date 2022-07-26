package com.example.m11.activities

import android.content.Intent
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.m11.R
import com.example.m11.db.QuerySqlOP
import kotlinx.android.synthetic.main.activity_add_info.*
import kotlinx.android.synthetic.main.title_layout.*

class AddInfoActivity : BaseActivity() {

    override fun setContentView(id: Int) {
        super.setContentView(R.layout.activity_add_info)
    }

    override fun initView() {
        img_title_bar_choose!!.visibility = GONE
        tv_title_bar_title!!.text = "稽查上传"
    }

    override fun onClick() {
        rl_add_location!!.setOnClickListener{
            val intent = Intent(this, LocationActivity::class.java)
            startActivityForResult(intent, 1)
        }
        img_title_bar_back!!.setOnClickListener { this@AddInfoActivity.finish() }
        rl_add_illegal_category.setOnClickListener {
            val items = arrayOf("调整信息化设备", "饰品佩戴", "违反安全行驶", "文明行为")
            val builder = AlertDialog.Builder(this@AddInfoActivity)
            builder.setTitle("请选择违规类别")
            builder.setSingleChoiceItems(items, 0) { dialog, which ->
                tv_add_illegal_category.text = items[which]
                dialog.dismiss()
            }
            builder.show()
        }
        rl_add_illegal_item.setOnClickListener {
            val items = arrayOf("1", "2", "3", "4")
            val builder = AlertDialog.Builder(this@AddInfoActivity)
            builder.setTitle("请选择违规项")
            builder.setSingleChoiceItems(items, 0) { dialog, which ->
                tv_add_illegal_item.text = items[which]
                dialog.dismiss()
            }
            builder.show()
        }
        val trainNumber = et_add_car_number.text
        val description = et_add_description.text
        tv_add_sure_submit.setOnClickListener {
            if (tv_add_illegal_category.text == "请选择违规类别") {
                Toast.makeText(this@AddInfoActivity, "违规类别不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (tv_add_illegal_item.text == "请选择违规项") {
                Toast.makeText(this@AddInfoActivity, "违规项不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (trainNumber.isEmpty()) {
                Toast.makeText(this@AddInfoActivity, "车号不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (trainNumber.length > 5) {
                Toast.makeText(this@AddInfoActivity, "车号最多为5位",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                Toast.makeText(this@AddInfoActivity, "违规描述不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (QuerySqlOP(this@AddInfoActivity).addIllegalInfo(trainNumber.toString().trim(), tv_add_illegal_category.text.toString().trim())) {
                this@AddInfoActivity.finish()
            } else {
                Toast.makeText(this@AddInfoActivity, "上传失败",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data == null) return
                tv_add_location.text =data.getStringExtra("address")
            }
        }
    }
}