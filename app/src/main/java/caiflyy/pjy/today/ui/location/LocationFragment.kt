package caiflyy.pjy.today.ui.location


import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import caiflyy.pjy.today.repository.TodayRepository
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.*
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.android.ext.android.inject


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.location
 * 日期：2019/4/25
 * 描述：位置信息界面
 * @author 蔡葳
 */
class LocationFragment : Fragment(), LocationSource, AMapLocationListener {
    val todayRepository:TodayRepository by inject()
    val LOCATION_MARKER_FLAG = "mylocation"
    private val STROKE_COLOR = Color.argb(180, 3, 145, 255)
    private val FILL_COLOR = Color.argb(10, 0, 0, 180)
    private var mFirstFix = false
    private var mLocMarker: Marker? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mCircle: Circle? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null

    lateinit var aMap: AMap
    lateinit var sensorEventHelper:SensorEventHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
//        todayRepository.saveCurrentItem(R.id.actionLauncherLocation)
        aMap = mapView.map
        sensorEventHelper = SensorEventHelper(context)
        sensorEventHelper.registerSensorListener()
        aMap.mapType = AMap.MAP_TYPE_NORMAL
        aMap.setLocationSource(this)
        aMap.uiSettings.isMyLocationButtonEnabled = true
        aMap.isMyLocationEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        sensorEventHelper.registerSensorListener()
    }

    override fun onPause() {
        super.onPause()
        sensorEventHelper.unRegisterSensorListener()
        sensorEventHelper.setCurrentMarker(null)
        mapView.onPause()
        deactivate()
        mFirstFix = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocMarker?.destroy()
        if (mapView!=null){
            mapView.onDestroy()
        }
        mlocationClient?.onDestroy()
    }

    override fun deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient?.stopLocation();
            mlocationClient?.onDestroy();
        }
        mlocationClient = null;
    }

    override fun activate(onLocationChangedListener: LocationSource.OnLocationChangedListener?) {
        mListener = onLocationChangedListener
        val vLocationOption: AMapLocationClientOption
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(context)
            vLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient?.setLocationListener(this)
            //设置为高精度定位模式
            vLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //设置定位参数
            mlocationClient?.setLocationOption(vLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient?.startLocation()
        }
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {
                val location = LatLng(aMapLocation.latitude, aMapLocation.longitude)
                if (!mFirstFix) {
                    mFirstFix = true
                    //添加定位精度圆
                    addCircle(location, aMapLocation.accuracy.toDouble())
                    //添加定位图标
                    addMarker(location)
                    //定位图标旋转
                    sensorEventHelper.setCurrentMarker(mLocMarker)
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
                } else {
                    mCircle?.center = location
                    mCircle?.radius = aMapLocation.accuracy.toDouble()
                    mLocMarker?.setPosition(location)
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(location))
                }
            } else {
                val errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo()
                Log.e("AmapErr", errText)
            }
        }
    }

    private fun addCircle(latlng: LatLng, radius: Double) {
        val options = CircleOptions()
        options.strokeWidth(1f)
        options.fillColor(FILL_COLOR)
        options.strokeColor(STROKE_COLOR)
        options.center(latlng)
        options.radius(radius)
        mCircle = aMap.addCircle(options)
    }

    private fun addMarker(latlng: LatLng) {
        if (mLocMarker != null) {
            return
        }
        val options = MarkerOptions()
        options.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(
                    this.resources,
                    caiflyy.pjy.today.R.drawable.img_location_gps_locked
                )
            )
        )
        options.anchor(0.5f, 0.5f)
        options.position(latlng)
        mLocMarker = aMap.addMarker(options)
        mLocMarker?.title = LOCATION_MARKER_FLAG
    }
}
