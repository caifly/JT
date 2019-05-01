package caiflyy.pjy.today.ui.profile
/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.launcher
 * 日期：2019/4/10
 * 描述：启动目标
 * 根据启动的状态跳转不同界面
 * 第一次启动跳转引导界面
 * 非第一次启动跳转主界面
 * @author 蔡葳
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import caiflyy.pjy.today.database.user.CacheUser
import caiflyy.pjy.today.repository.TodayRepository

class ProfileViewModel(private val todayRepository: TodayRepository): ViewModel() {
  fun updateUser(cacheUser: CacheUser) {
    todayRepository.updateUser(cacheUser)
  }

  fun uploadImage(imageFilePath: String) {
    todayRepository.uploadImage(imageFilePath)
  }

    fun getCacheUserInfo() {
      todayRepository.getCacheUserInfo()
    }

    val onSuccess: LiveData<CacheUser> = todayRepository.cacheUserInfo
}
