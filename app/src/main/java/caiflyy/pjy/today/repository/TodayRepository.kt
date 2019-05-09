package caiflyy.pjy.today.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import caiflyy.pjy.today.R
import caiflyy.pjy.today.cloud.RepoResult
import caiflyy.pjy.today.cloud.TodayCloud
import caiflyy.pjy.today.cloud.article.ArticleBean
import caiflyy.pjy.today.cloud.article.NewsComments
import caiflyy.pjy.today.data.TodayUser
import caiflyy.pjy.today.data.weather.StudyClassification
import caiflyy.pjy.today.database.TodayDatabase
import caiflyy.pjy.today.database.city.City
import caiflyy.pjy.today.database.user.CacheUser
import caiflyy.pjy.today.database.weather.ForecastDaily
import caiflyy.pjy.today.database.weather.ForecastHourly
import caiflyy.pjy.today.database.weather.LifeStyle
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.utils.LocationUtils
import caiflyy.pjy.today.utils.PREF_KEY_CURRENT_NAV_ITEM
import caiflyy.pjy.today.utils.TAG
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.datatype.BmobGeoPoint
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.datatype.BmobRelation
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.amap.api.location.AMapLocationListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.repository
 * 日期：2019/4/10
 * 描述：数据仓库操作类
 * @author 蔡葳
 */
class TodayRepository:KoinComponent {
    private val sharedPreferences: SharedPreferences by inject()
    private val locationUtils: LocationUtils by inject()
    private val todayDatabase: TodayDatabase by inject()
    private val todayCloud: TodayCloud by inject()

    val navItem = MutableLiveData<Int>()
    val isLoginOk = MutableLiveData<Boolean>()
    val cityWeatherInfo: MutableLiveData<Weather> = MutableLiveData()
    val articleInfo: MutableLiveData<List<ArticleBean>> = MutableLiveData()
    val newsCommentsInfo: MutableLiveData<List<NewsComments>> = MutableLiveData()
    val classificationInfo: MutableLiveData<List<StudyClassification>> = MutableLiveData()
    val courseInfo: MutableLiveData<List<StudyClassification>> = MutableLiveData()
    val cacheUserInfo: MutableLiveData<CacheUser> = MutableLiveData()

    private var currentView=0


    private var commentList:MutableList<NewsComments>?=null

    init {
        currentView=sharedPreferences.getInt(PREF_KEY_CURRENT_NAV_ITEM, R.id.actionLauncherHome)
        AnkoLogger(TAG).error("====获取保存状态$currentView")
        if (BmobUser.getCurrentUser(TodayUser::class.java)!=null){
            AnkoLogger(TAG).error("====${BmobUser.getCurrentUser(TodayUser::class.java).location?.latitude}")
        }
    }

    fun saveCurrentItem(itemId: Int) {
        sharedPreferences.edit(true) {
            putInt(PREF_KEY_CURRENT_NAV_ITEM, itemId)
        }
    }

    fun login(lUserPhone: String, lPassword: String, isLogin: Boolean) {
        locationUtils.getLocation(AMapLocationListener { aMapLocation ->
            if (aMapLocation?.errorCode == 0) {
                val location = BmobGeoPoint(aMapLocation.longitude, aMapLocation.latitude)
                val todayUser = TodayUser(aMapLocation.city, location)
                todayUser.username = lUserPhone
                todayUser.setPassword(lPassword)
                if (isLogin) {
                    todayUser.signUp(object : SaveListener<TodayUser>() {
                        override fun done(todayUser: TodayUser?, e: BmobException?) {
                            if (e == null) {
                                isLoginOk.postValue(true)
                                locationUtils.stopLocation()
                                cacheUser(todayUser)
                            } else {
                                AnkoLogger(TAG).error("注册失败")
                                isLoginOk.postValue(false)
                            }
                        }
                    })
                } else {
                    todayUser.login(object : SaveListener<TodayUser>() {
                        override fun done(todayUser: TodayUser?, e: BmobException?) {
                            if (e == null) {
                                isLoginOk.postValue(true)
                                locationUtils.stopLocation()
                                cacheUser(todayUser)
                            } else {
                                AnkoLogger(TAG).error("登录失败")
                                isLoginOk.postValue(false)
                            }
                        }
                    })
                }
            } else {
                AnkoLogger(TAG).error("定位错误信息：${aMapLocation?.errorCode} --${aMapLocation.errorInfo}")
            }
        })
    }

    fun cacheUser(user:TodayUser?){
        if (user!=null){
            val cacheUser = CacheUser(
                user.objectId,
                user.title,
                user.sex,
                user.type,
                user.nickname,
                user.headIcon,
                user.description,
                user.email
            )
            doAsync {
                todayDatabase.getCacheUserDao().addCacheUser(cacheUser)
                AnkoLogger(TAG).error("缓存登录用户")
            }
        }
    }

    /**
     * 获取城市信息
     */
    fun getCites(): LiveData<List<City>> {
        AnkoLogger(TAG).error("用户城市:" + BmobUser.getCurrentUser(TodayUser::class.java).title)
        return todayDatabase.getCityDao().getAllCity()
    }

    /**
     * 添加新城市
     */
    fun insertCity() {
        val city = City()
        city.name = BmobUser.getCurrentUser(TodayUser::class.java).title
        doAsync {
            todayDatabase.getCityDao().addCity(city)
        }
    }

    /**
     * 获取单个城市天气信息
     */
    fun getCityWeatherInfo(cityId: Long, cityName: String) {
        doAsync {
            val weather = todayDatabase.getWeatherDao().getWeatherById(cityId)
            if (weather == null) {
                getCloudWeatherInfo(cityId, cityName, true)
            } else {
                val daily = todayDatabase.getForecastDailyDao().getForecastDailyById(weather.id)
                val hourly = todayDatabase.getForecastHourlyDao().getForecastHourlyById(weather.id)
                val lifeStyle = todayDatabase.getLifeStyleDao().getLifeStyleById(weather.id)
                weather.forecastDaily = daily
                weather.forecastHourly = hourly
                weather.lifeStyle = lifeStyle
                cityWeatherInfo.postValue(weather)
                AnkoLogger(TAG).error("有城市天气缓存信息")
            }
        }
    }

    /**
     * 获取云端天气数据
     */
    fun getCloudWeatherInfo(cityId: Long, cityName: String, isFirst: Boolean) {
        val weather = Weather()
        weather.cityId = cityId
        val airInfoCallback = object : Callback<RepoResult> {
            override fun onFailure(call: Call<RepoResult>?, t: Throwable?) {
                AnkoLogger(TAG).error("获取云端空气质量数据失败！！")
            }

            override fun onResponse(call: Call<RepoResult>?, response: Response<RepoResult>?) {
                response?.isSuccessful.let {
                    AnkoLogger(TAG).error("获取云端空气质量数据成功！！")
                    weather.airQuality = response?.body()!!.HeWeather6[0].air_now_city!!
                    cityWeatherInfo.value = weather
                    if (isFirst) {
                        doAsync {
                            cacheWeatherInfo(weather, cityId)
                        }
                    } else {
                        doAsync {
                            refreshCache(weather, cityId)
                        }
                    }
                }
            }
        }
        val weatherInfoCallback = object : Callback<RepoResult> {
            override fun onFailure(call: Call<RepoResult>?, t: Throwable?) {
                AnkoLogger(TAG).error("获取云端天气数据失败！！")
            }

            override fun onResponse(call: Call<RepoResult>?, response: Response<RepoResult>?) {
                response?.isSuccessful.let {
                    AnkoLogger(TAG).error("获取云端天气数据成功！！")
                    weather.forecastDaily = response?.body()!!.HeWeather6[0].daily_forecast!!
                    weather.forecastHourly = response.body()!!.HeWeather6[0].hourly!!
                    weather.now = response.body()!!.HeWeather6[0].now!!
                    weather.lifeStyle = response.body()!!.HeWeather6[0].lifestyle!!
                    todayCloud.getAirInfo(cityName, airInfoCallback)
                }
            }
        }
        todayCloud.getWeatherInfo(cityName, weatherInfoCallback)
    }

    private fun refreshCache(weather: Weather, cityId: Long) {
        todayDatabase.getWeatherDao().updateWeather(weather)
        val w = todayDatabase.getWeatherDao().getWeatherById(cityId)
        for (daily: ForecastDaily in weather.forecastDaily) {
            daily.weatherId = w!!.id
        }
        for (hourly: ForecastHourly in weather.forecastHourly) {
            hourly.weatherId = w!!.id
        }
        for (lifeStyle: LifeStyle in weather.lifeStyle) {
            lifeStyle.weatherId = w!!.id
        }
        todayDatabase.getForecastHourlyDao().updateForecastHourly(weather.forecastHourly)
        todayDatabase.getForecastDailyDao().updateForecastDaily(weather.forecastDaily)
        todayDatabase.getLifeStyleDao().updateLifeStyle(weather.lifeStyle)
        AnkoLogger(TAG).error("刷新天气数据成功！！")
    }

    /**
     * 缓存天气信息到本地数据库
     */
    private fun cacheWeatherInfo(weather: Weather, cityId: Long) {
        todayDatabase.getWeatherDao().addWeather(weather)
        val w = todayDatabase.getWeatherDao().getWeatherById(cityId)
        for (daily: ForecastDaily in weather.forecastDaily) {
            daily.weatherId = w!!.id
        }
        for (hourly: ForecastHourly in weather.forecastHourly) {
            hourly.weatherId = w!!.id
        }
        for (lifeStyle: LifeStyle in weather.lifeStyle) {
            lifeStyle.weatherId = w!!.id
        }
        todayDatabase.getForecastHourlyDao().addForecastHourly(weather.forecastHourly)
        todayDatabase.getForecastDailyDao().addForecastDaily(weather.forecastDaily)
        todayDatabase.getLifeStyleDao().addLifeStyle(weather.lifeStyle)
        AnkoLogger(TAG).error("保存天气数据成功！！")
    }

    /**
     * 获取云端文章信息
     */
    fun getCloudArticleInfo() {
        val query = BmobQuery<ArticleBean>()
        query.findObjects(object : FindListener<ArticleBean>() {
            override fun done(list: List<ArticleBean>, e: BmobException?) {
                if (e == null) {
                    for (article in list) {
                        val queryUser = BmobQuery<TodayUser>()
                        queryUser.apply {
                            addWhereRelatedTo("likes", BmobPointer(article))
                            findObjects(object : FindListener<TodayUser>() {
                                override fun done(likes: List<TodayUser>, e: BmobException?) {
                                    if (e == null) {
                                        article.likeCount = likes.size
                                        if (BmobUser.getCurrentUser(TodayUser::class.java) in likes) {
                                            AnkoLogger(TAG).error("喜爱的文章")
                                            article.isLike = true
                                        }
                                        articleInfo.value = list
                                    } else {
                                        AnkoLogger(TAG).error("获取文章用户失败")
                                    }
                                }
                            })
                        }

                    }
                } else {
                    AnkoLogger(TAG).error("获取文章信息失败")
                }

            }
        })

    }

    fun updateArticle(articleBean: ArticleBean) {
        val like = BmobRelation()
        like.add(BmobUser.getCurrentUser(TodayUser::class.java))
        val article = ArticleBean(
            articleBean.title,
            articleBean.content,
            articleBean.imgPath,
            articleBean.likeCount,
            articleBean.isLike,
            like
        )
        article.objectId = articleBean.objectId
        article.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    AnkoLogger(TAG).error("获取文章信息更新成功")
                } else {
                    AnkoLogger(TAG).error("获取文章信息更新失败")
                }
            }
        })
    }

    fun getComments(itemId: String?) {
        val query = BmobQuery<NewsComments>()
        query.addWhereEqualTo("newsId", itemId)
        query.findObjects(object : FindListener<NewsComments>() {
            override fun done(list: MutableList<NewsComments>?, e: BmobException?) {
                if (e == null) {
                    commentList=list
                    newsCommentsInfo.value = commentList
                }
            }

        })
    }

    fun addComment(itemId: String, content: String) {
        val comment = NewsComments(
            itemId,
            content,
            System.currentTimeMillis(),
            BmobUser.getCurrentUser(TodayUser::class.java)!!.objectId,
            BmobUser.getCurrentUser(TodayUser::class.java)!!.nickname,
            BmobUser.getCurrentUser(TodayUser::class.java)!!.headIcon)
        commentList?.add(comment)
        newsCommentsInfo.postValue(commentList)
        comment.save(object :SaveListener<String>(){
            override fun done(objectId: String?, e: BmobException?) {
                if (e==null){
                    AnkoLogger(TAG).error("保存评论信息成功")
                }else{
                    AnkoLogger(TAG).error("保存评论信息失败")
                }
            }
        })
    }

    fun getStudyClassification() {
        val query = BmobQuery<StudyClassification>()
        query.addWhereEqualTo("parentID", "parent")
        query.findObjects(object : FindListener<StudyClassification>() {
            override fun done(list: MutableList<StudyClassification>?, e: BmobException?) {
                if (e == null) {
                    classificationInfo.value = list
                }
            }

        })
    }

    fun getStudyCourse(objectId: String?) {
        val query = BmobQuery<StudyClassification>()
        query.addWhereEqualTo("parentID", objectId)
        query.findObjects(object : FindListener<StudyClassification>() {
            override fun done(list: MutableList<StudyClassification>?, e: BmobException?) {
                if (e == null) {
                    courseInfo.value = list
                }
            }

        })
    }


    fun updateUser(cacheUser: CacheUser) {
        doAsync {
            todayDatabase.getCacheUserDao().updateCacheUser(cacheUser)
            AnkoLogger(TAG).error("缓存用户数据更新成功")
        }
        BmobUser.getCurrentUser(TodayUser::class.java).apply {
            nickname = cacheUser.nickName
            email = cacheUser.email
            sex = cacheUser.sex
            update(object :UpdateListener(){
                override fun done(e: BmobException?) {
                    if (e==null){
                        AnkoLogger(TAG).error("云端用户数据更新成功")
                    }else{
                        AnkoLogger(TAG).error("云端用户数据更新失败")
                    }
                }
            })
        }
    }

    fun uploadImage(imageFilePath: String) {
        val bmobFile = BmobFile(File(imageFilePath))
        bmobFile.uploadblock(object : UploadFileListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    AnkoLogger(TAG).error("上传文件成功:" + bmobFile.fileUrl)
                } else {
                    AnkoLogger(TAG).error("上传文件失败：" + e.message)
                }

            }

            override fun onProgress(value: Int?) {
                // 返回的上传进度（百分比）
            }
        })
    }

    fun getCacheUserInfo() {
        doAsync {
            val cacheUser=todayDatabase.getCacheUserDao().getCacheUser()
            if (cacheUser!=null){
                cacheUserInfo.postValue(cacheUser)
            }
        }
    }

    fun getCurrentView() {
        navItem.postValue(currentView)
    }
}