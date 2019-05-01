package caiflyy.pjy.today.ui.study


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.navigation
 * 日期：2019/4/11
 * 描述：学习喜好界面
 * @author 蔡葳
 */
class StudyFavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_favorite, container, false)
    }


}
