package caiflyy.pjy.today.ui.login
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
import caiflyy.pjy.today.repository.TodayRepository

class LoginViewModel(private val todayRepository : TodayRepository) : ViewModel() {

  val isLoginOk: LiveData<Boolean> = todayRepository.isLoginOk

  fun userLogin(lUserPhone: String, lPassword: String, isLogin: Boolean) {
    todayRepository.login(lUserPhone, lPassword, isLogin)
  }

  fun getCurrentView() {
    todayRepository.getCurrentView()
  }
}
