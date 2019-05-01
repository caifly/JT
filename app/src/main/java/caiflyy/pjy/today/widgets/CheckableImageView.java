package caiflyy.pjy.today.widgets;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckableImageView extends AppCompatImageView implements Checkable {

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final int[] CheckedStateSet = {android.R.attr.state_checked};

    private boolean checked = false;

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean b) {
        if (b != checked) {
            checked = b;
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckedStateSet);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

}
