package caiflyy.pjy.today.utils

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils
 * 日期：2019/4/13
 * 描述：定位工具类
 * @author 蔡葳
 */
class LocationUtils(val context:Context) {
    private var locationClient: AMapLocationClient = AMapLocationClient(context)

    fun getLocation(locationListener: AMapLocationListener){
        val locationClientOption = AMapLocationClientOption()
        locationClientOption.isOnceLocation = true
        locationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        locationClient.setLocationOption(locationClientOption)
        locationClient.setLocationListener(locationListener)
        locationClient.startLocation()
    }

    fun stopLocation(){
        locationClient.stopLocation()
    }

}