package caiflyy.pjy.today.ui.task.item

import androidx.annotation.ColorInt
import caiflyy.pjy.today.R
import caiflyy.pjy.today.ui.task.INSET
import caiflyy.pjy.today.ui.task.INSET_TYPE_KEY
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_card.*

open class CardItem (@ColorInt private val colorRes: Int, val text: CharSequence? = "") : Item() {

    init {
        extras.put(INSET_TYPE_KEY, INSET)
    }

    override fun getLayout() = R.layout.item_card

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setBackgroundColor(colorRes)
        viewHolder.text.text = text
    }
}
