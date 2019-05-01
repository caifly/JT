package caiflyy.pjy.today.ui.video;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.ec.video
 *
 * @author 蔡葳
 * 日期 2017/11/1
 * 描述：自定义播放器控制类
 */
public class FullScreenUtils {
    private static final String TAG = "FullScreenUtils";
    
    /**
     * 隐藏或显示：状态栏、系统虚拟按钮栏
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     * 
     * code is from:
     * https://d.android.com/samples/ImmersiveMode/src/com.example.android.immersivemode/ImmersiveModeFragment.html
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void toggleHideyBar(Activity activity) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding: Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
