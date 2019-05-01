package caiflyy.pjy.today.widgets.dynamic;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;

/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.today.widgets.dynamic
 * 日期：2019/4/23
 * 描述：天气动画功能基础天气默认类型类
 *
 * @author 蔡葳
 */
public class DefaultType extends BaseWeatherType {

    public DefaultType(Context context) {
        super(context);
        setColor(0xFF51C0F8);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        clearCanvas(canvas);
        canvas.drawColor(getDynamicColor());
    }

    @Override
    public void generateElements() {

    }

    @Override
    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {
        super.endAnimation(dynamicWeatherView, listener);
        listener.onAnimationEnd(null);
    }
}
