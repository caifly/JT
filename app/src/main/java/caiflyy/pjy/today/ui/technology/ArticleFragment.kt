package caiflyy.pjy.today.ui.technology


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import caiflyy.pjy.today.R
import caiflyy.pjy.today.cloud.article.ArticleBean
import caiflyy.pjy.today.utils.EXTRA_KEY_ITEM_ID
import caiflyy.pjy.today.utils.EXTRA_KEY_LOCATION_Y
import caiflyy.pjy.today.utils.TAG
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.technology
 * 日期：2019/4/15
 * 描述：技术文章界面
 * @author 蔡葳
 */
class ArticleFragment : Fragment(), ArticleAdapter.OnItemButtonClickListener {
    private val viewModel: ArticleViewModel by viewModel()

    override fun onLike(articleBean: ArticleBean) {
        viewModel.updateArticle(articleBean)
    }

    override fun onComment(locationY: Int, itemId: String) {
        fragmentManager?.commit {
            val bundle = Bundle()
            bundle.putInt(EXTRA_KEY_LOCATION_Y,locationY)
            bundle.putString(EXTRA_KEY_ITEM_ID,itemId)
            findNavController().navigate(R.id.actionCourse,bundle)
        }
    }

    override fun onReport(itemId: Int) {
    }

    override fun onPhoto(itemId: Int) {
    }

    override fun onShared(itemId: Int) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveCurrentItem(R.id.actionLauncherArticle)
        rcv.layoutManager = LinearLayoutManager(activity)
        val articleAdapter = ArticleAdapter(R.layout.item_article, null)
        articleAdapter.onItemButtonClickListener = this
        articleAdapter.setDuration(1000)
        articleAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rcv.adapter = articleAdapter
        rcv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                ArticleContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        })
        viewModel.onSuccess().observe(this, Observer{
            articleAdapter.setNewData(it)
            AnkoLogger(TAG).error("文章信息观察者")
        })
        viewModel.getCloudArticleInfo()
    }
}
