package caiflyy.pjy.today.ui.task

import android.graphics.drawable.Animatable
import android.view.View
import androidx.annotation.StringRes
import caiflyy.pjy.today.R
import caiflyy.pjy.today.ui.task.item.HeaderItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_header.*


class ExpandableHeaderItem(@StringRes titleStringResId: Int,
                           @StringRes subtitleResId: Int)
    : HeaderItem(titleStringResId, subtitleResId), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)
        viewHolder.icon.apply {
            visibility = View.VISIBLE
            setImageResource(if (expandableGroup.isExpanded) R.drawable.collapse else R.drawable.expand)
            setOnClickListener {
                expandableGroup.onToggleExpanded()
                bindIcon(viewHolder)
            }
        }
    }

    private fun bindIcon(viewHolder: ViewHolder) {
        viewHolder.icon.apply {
            visibility = View.VISIBLE
            setImageResource(if (expandableGroup.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
            (drawable as Animatable).start()
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
