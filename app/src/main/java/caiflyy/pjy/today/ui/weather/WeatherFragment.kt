package caiflyy.pjy.today.ui.weather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import caiflyy.pjy.today.R
import caiflyy.pjy.today.database.city.City
import caiflyy.pjy.today.utils.EXTRA_KEY_CITY_ID
import caiflyy.pjy.today.utils.EXTRA_KEY_CITY_NAME
import caiflyy.pjy.today.utils.TAG
import caiflyy.pjy.today.widgets.dynamic.DynamicWeatherView
import kotlinx.android.synthetic.main.fragment_weather.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/14
 * 描述：多城市天气信息容器界面
 * @author 蔡葳
 */
class WeatherFragment : Fragment() {
    private val viewModel:WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveCurrentItem(R.id.actionLauncherWeather)
        viewModel.getCites().observe(this, Observer {
            if (it.isEmpty()){
                AnkoLogger(TAG).error("插入新城市")
                viewModel.insertCity()
            }else{
                val adapter:WeatherCityPagerAdapter= WeatherCityPagerAdapter(childFragmentManager)
                for (city: City in it){
                    AnkoLogger(TAG).error("已有城市id:"+city.id)
                    val cityWeatherFragment=CityWeatherFragment()
                    val data = Bundle()
                    data.putLong(EXTRA_KEY_CITY_ID, city.id)
                    data.putString(EXTRA_KEY_CITY_NAME, city.name)
                    cityWeatherFragment.arguments = data
                    adapter.addFrag(cityWeatherFragment, city.name)
                }
                weatherViewPager.adapter = adapter
                weatherViewPager.offscreenPageLimit = adapter.count
                pager_title.setViewPager(weatherViewPager);
                animation_view.visibility=View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dynamicWeatherView.onResume()
    }

    override fun onPause() {
        super.onPause()
        dynamicWeatherView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dynamicWeatherView!=null){
            dynamicWeatherView.onDestroy()
        }
    }

    fun getDynamicWeatherView(): DynamicWeatherView {
        return dynamicWeatherView
    }

}
