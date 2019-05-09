package caiflyy.pjy.today.ui.technology

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/10
 * 描述：技术文章界面数据提供
 * @author 蔡葳
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import caiflyy.pjy.today.cloud.article.ArticleBean
import caiflyy.pjy.today.repository.TodayRepository

class ArticleViewModel(private val todayRepository:TodayRepository) : ViewModel() {

    fun onSuccess(): LiveData<List<ArticleBean>>{
        return todayRepository.articleInfo
    }

    fun updateArticle(articleBean: ArticleBean) {
        todayRepository.updateArticle(articleBean)
    }

    fun getCloudArticleInfo() {
        todayRepository.getCloudArticleInfo()
    }

    fun saveCurrentItem(actionId: Int) {
//        todayRepository.saveCurrentItem(actionWeather)
    }
}
