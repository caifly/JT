package caiflyy.pjy.today.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import caiflyy.pjy.today.repository.TodayRepository
import caiflyy.pjy.today.utils.TAG
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.android.ext.android.inject

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.home
 * 日期：2019/4/11
 * 描述：主介绍界面
 * @author 蔡葳
 */
class HomeFragment : Fragment() {
    val todayRepository:TodayRepository by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todayRepository.saveCurrentItem(R.id.actionLauncherHome)
        AnkoLogger(TAG).error("Home状态保存${R.id.actionLauncherHome}")
    }
}
