package caiflyy.pjy.today.ui.study


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import caiflyy.pjy.today.ui.video.ActivityVideoViewPlaying
import caiflyy.pjy.today.utils.EXTRA_KEY_OBJECT_ID
import caiflyy.pjy.today.utils.EXTRA_KEY_VIDEO
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.study
 * 日期：2019/4/25
 * 描述：学习分类信息界面
 * @author 蔡葳
 */
class StudyCourseFragment : Fragment() {
    private val viewModel:StudyCourseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(caiflyy.pjy.today.R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle=arguments
        val objectId=bundle?.getString(EXTRA_KEY_OBJECT_ID)
        val linearLayoutManager = LinearLayoutManager(context)
        rcv.layoutManager = linearLayoutManager
        val adapter = StudyCourseAdapter(caiflyy.pjy.today.R.layout.item_study_course,null)
        rcv.adapter = adapter
        viewModel.onSuccess.observe(this, Observer {
            adapter.setNewData(it)
            adapter.setOnItemClickListener {
                    p0, view, positon ->
                val _Intent = Intent()
                _Intent.setClass(context, ActivityVideoViewPlaying::class.java)
                _Intent.putExtra(EXTRA_KEY_VIDEO, it[positon].video)
                startActivity(_Intent)
            }
        })
        viewModel.getStudyCourse(objectId)
    }


}
