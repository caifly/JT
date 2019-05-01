package caiflyy.pjy.today.cloud.weather

import caiflyy.pjy.today.cloud.RepoResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.cloud.weather
 * 日期：2019/4/15
 * 描述：云端天气获取接口
 * @author 蔡葳
 */
interface WeatherService {
    @GET
    fun getCityWeather(@Url url: String,@QueryMap params:WeakHashMap<String, Object>): Call<RepoResult>
}