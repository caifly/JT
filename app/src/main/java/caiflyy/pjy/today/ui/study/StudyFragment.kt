package caiflyy.pjy.today.ui.study


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_study.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.study
 * 日期：2019/4/25
 * 描述：学习信息界面
 * @author 蔡葳
 */
class StudyFragment : Fragment() {
    private val studyViewModel:StudyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_study, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout.addTab(tab_layout.newTab().setText(caiflyy.pjy.today.R.string.text_study_tab_title_class))
        tab_layout.addTab(tab_layout.newTab().setText(caiflyy.pjy.today.R.string.text_study_tab_title_favorite))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = AdapterFragmentViewPager(fragmentManager!!, studyViewModel.getFragment())
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}
