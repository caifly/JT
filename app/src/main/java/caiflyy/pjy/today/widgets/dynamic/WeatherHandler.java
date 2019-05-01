package caiflyy.pjy.today.widgets.dynamic;

import android.content.Context;
import android.graphics.Canvas;

/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.today.widgets.dynamic
 * 日期：2019/4/23
 * 描述：天气动画功能动态绘制接口
 *
 * @author 蔡葳
 */
public interface WeatherHandler {
    void onDrawElements(Canvas canvas);

    void onSizeChanged(Context context, int width, int height);
}
