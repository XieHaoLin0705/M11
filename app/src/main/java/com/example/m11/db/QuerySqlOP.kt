package com.example.m11.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.m11.bean.CheckWorkInfo
import com.example.m11.bean.QueryResultInfo

class QuerySqlOP(context: Context?) {

    private var helper: MyDBOpenHelper? = null
    private var db: SQLiteDatabase? = null
    init {
        helper = MyDBOpenHelper(context)
    }
    fun queryAllResult(): ArrayList<QueryResultInfo> {
        db = helper!!.readableDatabase
        val queryResultInfoList = ArrayList<QueryResultInfo>()
        val cursor = db!!.query("resultInfo", null, null, null, null, null, "_id desc")
        if (cursor != null && cursor.count > 0) while (cursor.moveToNext()) queryResultInfoList.add(QueryResultInfo(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)))
        cursor.close()
        db!!.close()
        return queryResultInfoList
    }
    fun queryAllCheckWork(): ArrayList<CheckWorkInfo> {
        db = helper!!.readableDatabase
        val checkWorkInfoList = ArrayList<CheckWorkInfo>()
        val cursor = db!!.query("checkWorkInfo", null, null, null, null, null, null)
        if (cursor != null && cursor.count > 0) while (cursor.moveToNext()) checkWorkInfoList.add(CheckWorkInfo(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)))
        cursor.close()
        db!!.close()
        return checkWorkInfoList
    }

    fun addIllegalInfo(trainNumber: String, category: String): Boolean {
        db = helper!!.readableDatabase
        val values = ContentValues()
        values.put("trainNumber", trainNumber)
        values.put("state", "待反馈")
        values.put("driver", "谢昊霖（990705）")
        values.put("department", "第四客运分公司")
        values.put("category", category)
        values.put("date", "2021-12-13  11:38:23")
        val insert = db!!.insert("resultInfo", null, values)
        db!!.close()
        return insert > 0
    }
    fun queryLateCheckWork(): Int {
        db = helper!!.readableDatabase
        val lateCount: Int
        val cursor = db!!.query("checkWorkInfo", null, "signInState = ?", arrayOf("迟到"), null, null, null)
        lateCount = cursor.count
        cursor.close()
        db!!.close()
        return lateCount
    }
    fun queryWineNoPassCheckWork(): Int {
        db = helper!!.readableDatabase
        val lateCount: Int
        val cursor = db!!.query("checkWorkInfo", null, "wineCheckState = ?", arrayOf("不通过"), null, null, null)
        lateCount = cursor.count
        cursor.close()
        db!!.close()
        return lateCount
    }
    fun queryTrainCheckNoPassCheckWork(): Int {
        db = helper!!.readableDatabase
        val lateCount: Int
        val cursor = db!!.query("checkWorkInfo", null, "trainCheckState = ?", arrayOf("不通过"), null, null, null)
        lateCount = cursor.count
        cursor.close()
        db!!.close()
        return lateCount
    }
    fun queryTotalAlreadySingInCheckWork(): Int {
        db = helper!!.readableDatabase
        val lateCount: Int
        val cursor = db!!.query("checkWorkInfo", null, "signInState = ? or signInState = ?", arrayOf("正常签到", "迟到"), null, null, null)
        lateCount = cursor.count
        cursor.close()
        db!!.close()
        return lateCount
    }

    fun queryChooseResultByDB(queryResultList: MutableList<QueryResultInfo>, companyChooseList: MutableList<String>, illegalChooseList: MutableList<String>, stateChooseList: MutableList<String>) {
//        Cursor cursor = db.query("userInfo", null, "classes = ? and score = ? and sex = ?", new String[]{classList.get(0), scoreList.get(0), sexList.get(0)}, null, null, null);
        var company = ""
        var companySQL = ""
        var illegal = ""
        var illegalSQL = ""
        var state = ""
        var stateSQL = ""
        for (i in companyChooseList.indices) {
            companySQL += if (i == 0) {
                "department = ?"
            } else {
                " or department = ?"
            }
            company += companyChooseList[i]
        }
        for (i in illegalChooseList.indices) {
            illegalSQL += if (i == 0) {
                "category = ?"
            } else {
                " or category = ?"
            }
            illegal += illegalChooseList[i]
        }
        for (i in stateChooseList.indices) {
            stateSQL += if (i == 0) {
                "state = ?"
            } else {
                " or state = ?"
            }
            state += stateChooseList[i]
        }
        println("=================$companySQL")
        println("===-----------===$company")
        println("=================$illegalSQL")
        println("===-----------===$illegal")
        println("=================$stateSQL")
        println("===-----------===$state")

    }
    fun queryPartCheckWorkWithLine(checkAllWorkInfoList: MutableList<CheckWorkInfo>, checked24: Boolean, checked104: Boolean, checked321: Boolean): MutableList<CheckWorkInfo> {

        var i = 0
        var j = 0
        var k = 0
        var a = 0
        if (!checked24){
            while (a < checkAllWorkInfoList.size) {
                if (!checkAllWorkInfoList[a].trainLine.contains("24")) {
                    i++
                }
                if (i == 0) {
                    checkAllWorkInfoList.removeAt(a)
                    a--
                }
                i = 0
                a++
            }
        }
        var b = 0
        if (!checked104){
            while (b < checkAllWorkInfoList.size) {
                if (!checkAllWorkInfoList[b].trainLine.contains("104")) {
                    j++
                }
                if (j == 0) {
                    checkAllWorkInfoList.removeAt(b)
                    b--
                }
                j = 0
                b++
            }
        }
        var c = 0
        if (!checked321){
            while (c < checkAllWorkInfoList.size) {
                if (!checkAllWorkInfoList[c].trainLine.contains("321")) {
                    k++
                }
                if (k == 0) {
                    checkAllWorkInfoList.removeAt(c)
                    c--
                }
                k = 0
                c++
            }
        }
        return checkAllWorkInfoList
    }
    fun queryChooseResult(queryResultList: MutableList<QueryResultInfo>, companyChooseList: MutableList<String>, illegalChooseList: MutableList<String>, stateChooseList: MutableList<String>): MutableList<QueryResultInfo> {
        var a = 0
        var b = 0
        var c = 0
        var d = 0
        while (d < queryResultList.size) {
            for (j in companyChooseList.indices) {
                if (queryResultList[d].department.contains(companyChooseList[j])) {
                    a++
                }
            }
            if (a == 0) {
                queryResultList.removeAt(d)
                d--
            }
            a = 0
            d++
        }
        var e = 0
        while (e < queryResultList.size) {
            for (j in illegalChooseList.indices) {
                if (queryResultList[e].category.contains(illegalChooseList[j])) {
                    b++
                }
            }
            if (b == 0) {
                queryResultList.removeAt(e)
                e--
            }
            b = 0
            e++
        }
        var f = 0
        while (f < queryResultList.size) {
            for (j in stateChooseList.indices) {
                if (queryResultList[f].state.contains(stateChooseList[j])) {
                    c++
                }
            }
            if (c == 0) {
                queryResultList.removeAt(f)
                f--
            }
            c = 0
            f++
        }
        return queryResultList
    }

}