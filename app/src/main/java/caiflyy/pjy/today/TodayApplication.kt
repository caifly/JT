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