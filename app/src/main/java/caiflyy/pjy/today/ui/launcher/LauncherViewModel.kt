package caiflyy.pjy.today.ui.launcher
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

class LauncherViewModel(private val todayRepository : TodayRepository) : ViewModel() {

  val navItem: LiveData<Int> = todayRepository.navItem

  fun getCurrentView() {
    todayRepository.getCurrentView()
  }
}
