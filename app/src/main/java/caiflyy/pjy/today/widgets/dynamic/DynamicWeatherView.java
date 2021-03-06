package caiflyy.pjy.today.widgets.dynamic;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;
import caiflyy.pjy.today.database.weather.Weather;


/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.today.widgets.dynamic
 * 日期：2019/4/23
 * 描述：天气动画功能动态界面类
 *
 * @author 蔡葳
 */
public class DynamicWeatherView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private DrawThread mDrawThread;
    private BaseWeatherType weatherType;
    private int mViewWidth;
    private int mViewHeight;
    private Weather originWeather;
    int fromColor;
    private SurfaceHolder holder;

    public DynamicWeatherView(Context context) {
        this(context, null);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        weatherType = new DefaultType(context);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
    }

    public int getColor() {
        return weatherType.getColor();
    }

    public void setType(final BaseWeatherType type) {
        if (this.weatherType != null) {
            this.weatherType.endAnimation(this, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fromColor = weatherType.getColor();
                    weatherType = type;
                    if (weatherType != null) {
                        weatherType.onSizeChanged(mContext, mViewWidth, mViewHeight);
                    }
                    weatherType.startAnimation(DynamicWeatherView.this, fromColor);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            fromColor = type.getColor();
            this.weatherType = type;
            if (this.weatherType != null) {
                this.weatherType.onSizeChanged(mContext, mViewWidth, mViewHeight);
            }
            this.weatherType.startAnimation(this, fromColor);
        }

    }

    public Weather getOriginWeather() {
        return originWeather;
    }

    public void setOriginWeather(Weather originWeather) {
        this.originWeather = originWeather;
    }

    public BaseWeatherType getWeather() {
        return weatherType;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        if (weatherType != null) {
            weatherType.onSizeChanged(mContext, w, h);
        }
    }

    public void onResume() {
        if (mDrawThread != null) {
            mDrawThread.setSuspend(false);
        }
    }

    public void onPause() {
        if (mDrawThread != null) {
            mDrawThread.setSuspend(true);
        }
    }

    public void onDestroy() {
        mDrawThread.setRunning(false);
        getHolder().removeCallback(this);
        if (this.weatherType != null) {
            this.weatherType.endAnimation(this, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawThread = new DrawThread();
        mDrawThread.setRunning(true);
        mDrawThread.start();
        weatherType.startAnimation(this, weatherType.getColor());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDrawThread.setRunning(false);
    }

    private class DrawThread extends Thread {

        private boolean isRunning = false;

        private boolean suspended = false;

        private final Object control = new Object();

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public void setSuspend(boolean suspend) {
            if (!suspend) {
                synchronized (control) {
                    control.notifyAll();
                }
            }
            this.suspended = suspend;
        }

        @Override
        public void run() {
            while (isRunning) {
                if (suspended) {
                    try {
                        synchronized (control) {
                            control.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final long startTime = AnimationUtils.currentAnimationTimeMillis();
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    weatherType.onDrawElements(canvas);
                    holder.unlockCanvasAndPost(canvas);
                    final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                    final long needSleepTime = 16 - drawTime;
                    if (needSleepTime > 0) {
                        SystemClock.sleep(needSleepTime);
                    }
                }

            }
        }
    }
}
