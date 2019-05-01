package caiflyy.pjy.today.ui.study

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.study
 * 日期：2019/4/25
 * 描述：
 * @author 蔡葳
 */
class AdapterFragmentViewPager(fragmentManager:FragmentManager,val fragments:List<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}