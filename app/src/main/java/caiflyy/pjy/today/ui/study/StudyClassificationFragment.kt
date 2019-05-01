package caiflyy.pjy.today.ui.study


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.EXTRA_KEY_OBJECT_ID
import caiflyy.pjy.today.utils.TAG
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.study
 * 日期：2019/4/25
 * 描述：学习一级分类信息界面
 * @author 蔡葳
 */
class StudyClassificationFragment : Fragment() {
    private val viewModel:StudyClassificationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnkoLogger(TAG).error("onViewCreated")
        val gridLayoutManager = GridLayoutManager(context, 2)
        rcv.layoutManager = gridLayoutManager
        val adapter = StudyClassificationAdapter(R.layout.item_study_classification,null)
        rcv.adapter = adapter
        viewModel.onSuccess.observe(this, Observer {
            AnkoLogger(TAG).error("学习分类观察者==${it.size}")
            adapter.setNewData(it)
            adapter.setOnItemClickListener {
                    p0, view, position ->
                fragmentManager?.commit {
                    val bundle = Bundle()
                    bundle.putString(EXTRA_KEY_OBJECT_ID, it[position].objectId)
                    findNavController().navigate(R.id.actionCourse,bundle)
                }

            }
        })
        viewModel.getStudyClassification()
    }


}
