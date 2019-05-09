package caiflyy.pjy.today.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import caiflyy.pjy.today.cloud.TodayCloud
import caiflyy.pjy.today.database.TodayDatabase
import caiflyy.pjy.today.repository.TodayRepository
import caiflyy.pjy.today.ui.launcher.LauncherViewModel
import caiflyy.pjy.today.ui.login.LoginViewModel
import caiflyy.pjy.today.ui.profile.ProfileViewModel
import caiflyy.pjy.today.ui.study.StudyClassificationViewModel
import caiflyy.pjy.today.ui.study.StudyCourseViewModel
import caiflyy.pjy.today.ui.study.StudyViewModel
import caiflyy.pjy.today.ui.technology.ArticleViewModel
import caiflyy.pjy.today.ui.technology.CommentsViewModel
import caiflyy.pjy.today.ui.weather.CityWeatherViewModel
import caiflyy.pjy.today.ui.weather.WeatherViewModel
import caiflyy.pjy.today.utils.LocationUtils
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import caiflyy.pjy.today.utils.image.PicassoImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
/**
 * 项目名称：Demo08
 * 包名：caiflyy.pjy.today.di
 * 日期：2019/4/10
 * 描述：依赖注入
 * @author 蔡葳
 */
val appModule = module {
    viewModel { LauncherViewModel(get()) }
    viewModel { CityWeatherViewModel(get()) }
    viewModel { ArticleViewModel(get()) }
    viewModel { CommentsViewModel(get()) }
    viewModel { StudyClassificationViewModel(get()) }
    viewModel { StudyCourseViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { WeatherViewModel(get()) }
    viewModel { StudyViewModel(get()) }
    single { TodayRepository() }
    single { TodayCloud() }
    single<ImageLoaderManager> { ImageLoaderManager(PicassoImageLoader()) }
    single { LocationUtils(androidContext()) }
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            TodayDatabase::class.java, "today.db"
        ).build()
    }
}