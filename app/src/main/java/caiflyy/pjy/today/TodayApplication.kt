package caiflyy.pjy.today

import android.app.Application
import caiflyy.pjy.today.di.appModule
import cn.bmob.v3.Bmob
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today
 * 日期：2019/4/11
 * 描述：应用程序类
 * 完成所有的初始化工作
 * @author 蔡葳
 */
class TodayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TodayApplication)
            androidFileProperties()
            modules(appModule)
        }
        Iconify.with(FontAwesomeModule())
        Bmob.initialize(this,"0a9f843be47dde960b095deb1c2677b9")
    }
}