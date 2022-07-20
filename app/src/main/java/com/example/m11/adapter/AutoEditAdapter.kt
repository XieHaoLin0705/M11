package com.example.m11.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.m11.R
import java.util.*

class AutoEditAdapter (mList: MutableList<String>,mList2: MutableList<String>, context: Context): BaseAdapter(), Filterable  {

    private var mFilter: ArrayFilter? = null
    private var mList: List<String>? = null
    private var mList2: List<String>? = null
    private var context: Context? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mUnfilteredData: ArrayList<String>? = null

    interface OnItemClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }
    init {
        this.mList = mList
        this.context = context
        this.mList2 = mList2
    }

    override fun getCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    override fun getItem(p0: Int): Any {
        return mList!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (p1 == null) {
            view = View.inflate(context, R.layout.item_map_search, null)
            holder = ViewHolder()
            holder.text1 = view.findViewById<View>(R.id.name) as TextView
            holder.text2 = view.findViewById(R.id.address)as TextView
            holder.dianji = view.findViewById(R.id.dianji)
            view.tag = holder
        } else {
            view = p1
            holder = view.tag as ViewHolder
        }
//item的点击事件
        //item的点击事件
        if (mOnItemClickListener != null) {
            holder.dianji!!.setOnClickListener { mOnItemClickListener!!.onClick(p0) }
            holder.dianji!!.setOnLongClickListener {
                mOnItemClickListener!!.onLongClick(p0)
                false
            }
        }
        val pc = mList!![p0]
        val pc2 = mList2!![p0]

        holder.text1!!.text = pc
        holder.text2!!.text = pc2


        return view
    }

    override fun getFilter(): Filter {
        if (mFilter == null) {
            mFilter = ArrayFilter()
        }
        return mFilter!!
    }

    class ViewHolder {
        var text1: TextView? = null
        var text2: TextView? = null
        var dianji: LinearLayout? = null
    }


    inner class ArrayFilter : Filter() {
        override fun performFiltering(prefix: CharSequence): FilterResults {
            val results = FilterResults()
            if (mUnfilteredData == null) {
                mUnfilteredData = ArrayList<String>(mList)
            }
            if (prefix.isEmpty()) {
                val list: java.util.ArrayList<String> = mUnfilteredData!!
                results.values = list
                results.count = list.size
            } else {
                val prefixString = prefix.toString().lowercase(Locale.getDefault())
                val unfilteredValues: java.util.ArrayList<String> = mUnfilteredData!!
                val count = unfilteredValues.size
                val newValues = java.util.ArrayList<String>(count)
                for (i in 0 until count) {
                    val pc = unfilteredValues[i]
                    if (pc.startsWith(prefixString)) {
                        newValues.add(pc)
                    } else if (pc.startsWith(prefixString)) {
                        newValues.add(pc)
                    }
                }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence,
                                    results: FilterResults) {
            mList = results.values as List<String>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}