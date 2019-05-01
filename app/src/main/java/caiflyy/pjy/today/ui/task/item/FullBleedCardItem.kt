package caiflyy.pjy.today.ui.task.item

import androidx.annotation.ColorRes
import caiflyy.pjy.today.ui.task.INSET_TYPE_KEY

class FullBleedCardItem(@ColorRes colorRes: Int) : CardItem(colorRes) {

    init {
        extras.remove(INSET_TYPE_KEY)
    }
}
