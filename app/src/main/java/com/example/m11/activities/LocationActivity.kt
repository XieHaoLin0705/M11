package com.example.m11.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.example.m11.R
import com.example.m11.adapter.AutoEditAdapter
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlin.math.abs

class LocationActivity : BaseActivity(), SensorEventListener {
    private var area: String? = null
    private var address: String? = null
    private var lastX = 0.0
    private var mBaiDuMap: BaiduMap? = null
    private var mCurrentAccurate = 0f
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var isFirstLoc = true
    private var isShowListView = false
    private var mCurrentDirection = 0
    private var mLocClient: LocationClient? = null
    private var mLocClient2: LocationClient? = null
    private var mSensorManager: SensorManager? = null
    private var myLocationData: MyLocationData? = null
    private var poiSearch: PoiSearch? = null
    private var stringlist = mutableListOf<String>()
    private var stringlist2 = mutableListOf<String>()
    private var mSuggestionSearch: SuggestionSearch? = null
    private val myListener = MyLocationListener()
    private var latLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLocation()
    }
    override fun baseSetView(id: Int) {
        super.baseSetView(R.layout.activity_location)
    }

    override fun initView() {
        mBaiDuMap = bmapView!!.map
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING
        mSensorManager!!.registerListener(this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI)
        mBaiDuMap!!.setMyLocationConfiguration(MyLocationConfiguration(mCurrentMode, true, null))
        val builder = MapStatus.Builder()
        builder.overlook(0f)
        mBaiDuMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        poiSearch = PoiSearch.newInstance();
        bmapView!!.showZoomControls(false)
        tv_title_bar_title.text = "稽查地点"
        img_title_bar_choose.visibility = GONE
        mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch!!.setOnGetSuggestionResultListener(listener)
    }

    override fun onClick() {
        tv_location_pass_back.setOnClickListener {
            val intent = Intent()
            intent.putExtra("address", address)
            setResult(30, intent)
            finish()
        }
        img_title_bar_back.setOnClickListener { this.finish() }
    }

    private fun initLocation() {
        mBaiDuMap!!.isMyLocationEnabled = true
        LocationClient.setAgreePrivacy(true)
        try {
            mLocClient = LocationClient(this)
            mLocClient!!.registerLocationListener(myListener)
            val option = LocationClientOption()
            option.setIsNeedAddress(true)
            option.isOpenGps = true
            option.setCoorType("bd09ll")
            option.setScanSpan(0)
            mLocClient!!.locOption = option
            mLocClient!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun initLocation2() {
        mBaiDuMap!!.isMyLocationEnabled = true
        LocationClient.setAgreePrivacy(true)
        try {
            mLocClient2 = LocationClient(this)
            mLocClient2!!.registerLocationListener(myListener)
            val option = LocationClientOption()
            option.setIsNeedAddress(true)
            option.isOpenGps = true
            option.setCoorType("bd09ll")
            option.setScanSpan(0)
            mLocClient!!.locOption = option
            mLocClient!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class MyLocationListener : BDLocationListener {
        @SuppressLint("SetTextI18n")
        override fun onReceiveLocation(location: BDLocation) {
            if (bmapView == null) {
                return
            }
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            mCurrentAccurate = location.radius
            myLocationData = MyLocationData.Builder().accuracy(location.radius).direction(mCurrentDirection.toFloat()).latitude(location.latitude).longitude(location.longitude).build()
            mBaiDuMap!!.setMyLocationData(myLocationData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude, location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mBaiDuMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
            address = location.addrStr.replace("中国","")
            tv_location_detail_address!!.text = location.district + location.street
            tv_location_total_address!!.text = address
            area = location.district
            setEdit(location.city)
        }
    }

    override fun onResume() {
        super.onResume()
        bmapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        bmapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSensorManager!!.unregisterListener(this)
        if (mLocClient != null) {
            mLocClient!!.stop()
        }
        mBaiDuMap!!.isMyLocationEnabled = false
        bmapView!!.onDestroy()
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val x = sensorEvent.values[SensorManager.DATA_X].toDouble()
        if (abs(x - lastX) > 1.0) {
            mCurrentDirection = x.toInt()
            myLocationData = MyLocationData.Builder().accuracy(mCurrentAccurate).direction(mCurrentDirection.toFloat()).latitude(mCurrentLat).longitude(mCurrentLon).build()
//            mBaiDuMap!!.setMyLocationData(myLocationData)
        }
        lastX = x
    }
    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    fun setEdit(city: String?) {

        if (isShowListView){
            lv_address_result.visibility = VISIBLE
        } else {
            lv_address_result.visibility = GONE
        }
        et_location_search.setOnClickListener(View.OnClickListener { et_location_search.showDropDown() })
        et_location_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                mSuggestionSearch!!.requestSuggestion(SuggestionSearchOption()
                        .keyword(et_location_search.text.toString())
                        .city(city))
                lv_address_result.visibility = VISIBLE
                isShowListView = true
            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable!!.isEmpty()){
                    lv_address_result.visibility = GONE
                    isShowListView = false
                }
            }
        })
    }

    private var listener: OnGetSuggestionResultListener = object : OnGetSuggestionResultListener {
        override fun onGetSuggestionResult(res: SuggestionResult?) {
            if (res?.allSuggestions == null) { //未找到相关结果
                return
            } else { //获取在线建议检索结果
                val resl: List<SuggestionResult.SuggestionInfo> = res.allSuggestions
                stringlist.clear()
                stringlist2.clear()
                for (i in resl.indices) {
                    stringlist.add(resl[i].key)
                    stringlist2.add(resl[i].city + resl[i].district + resl[i].key)
                    latLng = resl[i].pt
                }
                val adapter = AutoEditAdapter(stringlist, stringlist2, this@LocationActivity)
                lv_address_result.adapter = adapter
                adapter.setOnItemClickListener(object : AutoEditAdapter.OnItemClickListener{
                    override fun onClick(position: Int) {
                        et_location_search.setText(stringlist[position])
                        et_location_search.dismissDropDown()
                        lv_address_result.visibility = GONE
                        isShowListView = false
                        initLocation2()
                        val locationData: MyLocationData = MyLocationData.Builder().latitude(resl[position].pt.latitude).longitude(resl[position].pt.longitude).build()
                        mBaiDuMap!!.setMyLocationData(locationData)
//                        val myLocConfig = MyLocationConfiguration(locationMode, true, mLocBitmap)
//                        mBaiduMap.setMyLocationConfiguration(myLocConfig)
                        val fromResource = BitmapDescriptorFactory.fromResource(R.drawable.location)
                        mBaiDuMap!!.setMyLocationConfiguration(MyLocationConfiguration(null, true, fromResource))
                        tv_location_detail_address.text = stringlist[position]
                        tv_location_total_address.text = resl[position].address
                        address = resl[position].address.replace("-","")
                        if (isFirstLoc) {
                            isFirstLoc = false
                        }
                        val ll2 = LatLng(resl[position].pt.latitude, resl[position].pt.longitude)
                        val builder = MapStatus.Builder()
                        builder.target(ll2).zoom(18.0f)
                        mBaiDuMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
                    }
                    override fun onLongClick(position: Int) {}
                })
            }
        }
    }
}