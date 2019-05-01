package caiflyy.pjy.today.ui.technology

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/10
 * 描述：评论界面数据提供
 * @author 蔡葳
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import caiflyy.pjy.today.cloud.article.NewsComments
import caiflyy.pjy.today.repository.TodayRepository

class CommentsViewModel(private val todayRepository: TodayRepository) : ViewModel() {

    fun onSuccess(): LiveData<List<NewsComments>>{
        return todayRepository.newsCommentsInfo
    }

    fun addComment(itemId: String, content: String) {
        todayRepository.addComment(itemId,content)
    }

    fun getComments(itemId: String?) {
        todayRepository.getComments(itemId)
    }
}
