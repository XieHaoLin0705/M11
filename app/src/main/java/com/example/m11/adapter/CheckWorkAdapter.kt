package com.example.m11.adapter

import com.example.m11.bean.QueryResultInfo
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.m11.R
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.m11.bean.CheckWorkInfo

class CheckWorkAdapter(private val checkWorkInfoList: List<CheckWorkInfo>) : RecyclerView.Adapter<CheckWorkAdapter.CheckWorkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckWorkViewHolder {
        return CheckWorkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_check_work, parent, false))
    }

    override fun onBindViewHolder(holder: CheckWorkViewHolder, position: Int) {
        val (trainLine, trainTime, trainNumber, driver, trainCheckState, wineCheckState, signInState, signOutState) = checkWorkInfoList[position]
        holder.tv_item_check_work_train_line.text = trainLine
        holder.tv_item_check_work_train_time.text = trainTime
        holder.tv_item_check_work_train_number.text = trainNumber
        holder.tv_item_check_work_driver.text = driver
        holder.tv_item_check_work_train_state.text = trainCheckState
        holder.tv_item_check_work_wine_state.text = wineCheckState
        holder.tv_item_check_work_signIn_state.text = signInState
        holder.tv_item_check_work_signOut_state.text = signOutState
        if (trainCheckState == "通过") {
            holder.tv_item_check_work_train_state_point.setBackgroundResource(R.drawable.background_small_point_green)
            holder.tv_item_check_work_train_state.setTextColor(Color.parseColor("#47A917"))
        } else if (trainCheckState == "不通过") {
            holder.tv_item_check_work_train_state_point.setBackgroundResource(R.drawable.background_small_point_red)
            holder.tv_item_check_work_train_state.setTextColor(Color.parseColor("#F5222D"))
        }
        if (wineCheckState == "通过") {
            holder.tv_item_check_work_wine_state_point.setBackgroundResource(R.drawable.background_small_point_green)
            holder.tv_item_check_work_wine_state.setTextColor(Color.parseColor("#47A917"))
        } else if (wineCheckState == "不通过") {
            holder.tv_item_check_work_wine_state_point.setBackgroundResource(R.drawable.background_small_point_red)
            holder.tv_item_check_work_wine_state.setTextColor(Color.parseColor("#F5222D"))
        }
        if (signInState == "正常签到") {
            holder.tv_item_check_work_signIn_state_point.setBackgroundResource(R.drawable.background_small_point_green)
            holder.tv_item_check_work_signIn_state.setTextColor(Color.parseColor("#47A917"))
        } else if (signInState == "迟到") {
            holder.tv_item_check_work_signIn_state_point.setBackgroundResource(R.drawable.background_small_point_yellow)
            holder.tv_item_check_work_signIn_state.setTextColor(Color.parseColor("#FA8C16"))
        } else if (signInState == "未签到") {
            holder.tv_item_check_work_signIn_state_point.setBackgroundResource(R.drawable.background_small_point_red)
            holder.tv_item_check_work_signIn_state.setTextColor(Color.parseColor("#F5222D"))
        }
    }

    override fun getItemCount(): Int {
        return checkWorkInfoList.size
    }

    class CheckWorkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_item_check_work_train_line: TextView
        val tv_item_check_work_train_time: TextView
        val tv_item_check_work_train_number: TextView
        val tv_item_check_work_driver: TextView
        val tv_item_check_work_train_state_point: TextView
        val tv_item_check_work_wine_state_point: TextView
        val tv_item_check_work_signIn_state_point: TextView
        val tv_item_check_work_signOut_state_point: TextView
        val tv_item_check_work_train_state: TextView
        val tv_item_check_work_wine_state: TextView
        val tv_item_check_work_signIn_state: TextView
        val tv_item_check_work_signOut_state: TextView

        init {
            tv_item_check_work_train_line = itemView.findViewById(R.id.tv_item_check_work_train_line)
            tv_item_check_work_train_time = itemView.findViewById(R.id.tv_item_check_work_train_time)
            tv_item_check_work_train_number = itemView.findViewById(R.id.tv_item_check_work_train_number)
            tv_item_check_work_driver = itemView.findViewById(R.id.tv_item_check_work_driver)
            tv_item_check_work_train_state_point = itemView.findViewById(R.id.tv_item_check_work_train_state_point)
            tv_item_check_work_wine_state_point = itemView.findViewById(R.id.tv_item_check_work_wine_state_point)
            tv_item_check_work_signIn_state_point = itemView.findViewById(R.id.tv_item_check_work_signIn_state_point)
            tv_item_check_work_signOut_state_point = itemView.findViewById(R.id.tv_item_check_work_signOut_state_point)
            tv_item_check_work_train_state = itemView.findViewById(R.id.tv_item_check_work_train_state)
            tv_item_check_work_wine_state = itemView.findViewById(R.id.tv_item_check_work_wine_state)
            tv_item_check_work_signIn_state = itemView.findViewById(R.id.tv_item_check_work_signIn_state)
            tv_item_check_work_signOut_state = itemView.findViewById(R.id.tv_item_check_work_signOut_state)
        }
    }
}