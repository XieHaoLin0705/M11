package com.example.m11.adapter

import com.example.m11.bean.QueryResultInfo
import androidx.recyclerview.widget.RecyclerView
import com.example.m11.adapter.MyQueryResultAdapter.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.m11.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView

class MyQueryResultAdapter(private val queryResultInfoList: List<QueryResultInfo>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_query_result, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (trainNumber, state, driver, department, category, date) = queryResultInfoList[position]
        holder.item_train_number.text = trainNumber
        holder.item_state.text = state
        holder.item_driver.text = driver
        holder.item_department.text = department
        holder.item_category.text = category
        holder.item_date.text = date
        if (state == "已完成") {
            holder.item_state.setBackgroundResource(R.drawable.background_item_state_finish)
            holder.item_state.setTextColor(Color.parseColor("#47A917"))
        }
    }

    override fun getItemCount(): Int {
        return queryResultInfoList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_train_number: TextView
        val item_state: TextView
        val item_driver: TextView
        val item_department: TextView
        val item_category: TextView
        val item_date: TextView

        init {
            item_train_number = itemView.findViewById(R.id.item_train_number)
            item_state = itemView.findViewById(R.id.item_state)
            item_driver = itemView.findViewById(R.id.item_driver)
            item_department = itemView.findViewById(R.id.item_department)
            item_category = itemView.findViewById(R.id.item_category)
            item_date = itemView.findViewById(R.id.item_date)
        }
    }
}