package caiflyy.pjy.today.ui.launcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.TAG
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.androidx.viewmodel.ext.android.viewModel

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
class LauncherFragment : Fragment(), LifecycleObserver {
    private val viewModel: LauncherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navItem.observe(this, Observer {
            AnkoLogger(TAG).error("启动导航观察者$it")
            findNavController().navigate(
                it,               null,
                NavOptions.Builder().setPopUpTo(
                    R.id.launcherFragment,
                    true
                ).build()
            )
        })
        viewModel.getCurrentView()
    }
}
