package caiflyy.pjy.today.ui.task.item

import androidx.annotation.ColorInt
import androidx.recyclerview.widget.ItemTouchHelper

class SwipeToDeleteItem(@ColorInt colorRes: Int) : CardItem(colorRes) {

    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
}
