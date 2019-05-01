package caiflyy.pjy.today.ui.task.item

import androidx.annotation.ColorInt
import caiflyy.pjy.today.ui.task.INSET
import caiflyy.pjy.today.ui.task.INSET_TYPE_KEY

class ColumnItem(@ColorInt colorRes: Int, index: Int) : CardItem(colorRes, index.toString()) {

    init {
        extras.put(INSET_TYPE_KEY, INSET)
    }

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2

}
