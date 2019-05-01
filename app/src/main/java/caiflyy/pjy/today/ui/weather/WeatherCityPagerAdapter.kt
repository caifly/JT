package caiflyy.pjy.today.ui.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/14
 * 描述：城市滑屏适配器
 * @author 蔡葳
 */
class WeatherCityPagerAdapter(private val manager:FragmentManager):FragmentPagerAdapter(manager){
    private val fragmentList= ArrayList<Fragment>()
    private val titleList= ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFrag(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}