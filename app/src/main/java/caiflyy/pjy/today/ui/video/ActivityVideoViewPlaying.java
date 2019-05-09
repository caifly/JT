package caiflyy.pjy.today.ui.video;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import caiflyy.pjy.today.R;
import caiflyy.pjy.today.ui.video.video_play.BDCloudVideoView;
import com.baidu.cloud.media.player.IMediaPlayer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static caiflyy.pjy.today.utils.ConstantsKt.EXTRA_KEY_VIDEO;

/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.ec.video
 *
 * @author 蔡葳
 * 日期 2017/11/1
 * 描述：视频播放Activity
 */
public class ActivityVideoViewPlaying extends AppCompatActivity implements IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnBufferingUpdateListener,
        BDCloudVideoView.OnPlayerStateListener{
    private String AK = "d8af52a7b3de4ab69c1e4891fff81e49";
    private String mVideoSource;


    private ArrayList<VideoInfo> playList;
    /**
     * drm版权保护 drmToken的生成与使用方式，请联系百度客服。 使用时，搜索该类中涉及drmToken的地方，酌情修改。
     */
    // private String drmToken =
    // "100a44b2c672011782cc64152dd71b4f9ca7821ea46191ce71e003eb5039468b_vod-gaqrms5ix6xad5yk_1471004113";

    private BDCloudVideoView mVV = null;
    private AdvancedMediaController mediaController = null;
    private RelativeLayout headerBar = null;
    private RelativeLayout fullHeaderRl = null;
    private RelativeLayout fullControllerRl = null;
    private RelativeLayout normalHeaderRl = null;
    private RelativeLayout normalControllerRl = null;

    private RelativeLayout mViewHolder = null;

    private Timer barTimer;
    private volatile boolean isFullScreen = false;

    private Toast toast;

    /**
     * 记录播放位置
     */
    private int mLastPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_view_playing);
        // 设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(0xff282828);
        }
        /**
         * 防闪屏
         */
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_view_playing);
        Intent _Intent = getIntent();
        if (_Intent!=null){
           mVideoSource = _Intent.getStringExtra(EXTRA_KEY_VIDEO);
        }
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        bindData();
    }

    protected void bindData() {
        mViewHolder = (RelativeLayout) findViewById(R.id.view_holder);
        mediaController = (AdvancedMediaController) findViewById(R.id.media_controller_bar);
        fullHeaderRl = (RelativeLayout) findViewById(R.id.rl_fullscreen_header);
        fullControllerRl = (RelativeLayout) findViewById(R.id.rl_fullscreen_controller);
        normalHeaderRl = (RelativeLayout) findViewById(R.id.rl_normalscreen_header);
        normalControllerRl = (RelativeLayout) findViewById(R.id.rl_normalscreen_controller);
        headerBar = (RelativeLayout) findViewById(R.id.rl_header_bar);

        /**
         * 设置ak
         */
        BDCloudVideoView.setAK(AK);

        mVV = new BDCloudVideoView(this);
        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnInfoListener(this);
        mVV.setOnBufferingUpdateListener(this);
        mVV.setOnPlayerStateListener(this);

        if (SharedPrefsStore.isPlayerFitModeCrapping(getApplicationContext())) {
            mVV.setVideoScalingMode(BDCloudVideoView.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        } else {
            mVV.setVideoScalingMode(BDCloudVideoView.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }

        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(-1, -1);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mViewHolder.addView(mVV, rllp);

        mediaController.setMediaPlayerControl(mVV);

        mVV.setLogEnabled(false);
//        mVV.setMaxProbeTime(1 * 1000); // 设置首次缓冲的最大时长

        mVV.setVideoPath(mVideoSource);
        // 初始化好之后立即播放（您也可以在onPrepared回调中调用该方法）
        mVV.start();

        initOtherUI();
    }

    private void initOtherUI() {
        // header
        final ImageButton ibBack = (ImageButton) this.findViewById(R.id.ibtn_back);
        ibBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });
        RelativeLayout rlback = (RelativeLayout) this.findViewById(R.id.rl_back);
        rlback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ibBack.performClick();
            }

        });
        TextView tvTitle = (TextView) this.findViewById(R.id.tv_top_title);
        tvTitle.setText("视频名称");
        final ImageButton ibScreen = (ImageButton) this.findViewById(R.id.ibtn_screen_control);
        ibScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FullScreenUtils.toggleHideyBar(ActivityVideoViewPlaying.this);
                    // to mini size, to portrait
                    fullHeaderRl.removeAllViews();
                    fullControllerRl.removeAllViews();
                    normalHeaderRl.addView(headerBar);
                    normalControllerRl.addView(mediaController);
                    isFullScreen = false;
                    ibScreen.setBackgroundResource(R.drawable.img_video_btn_to_fullscreen);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    FullScreenUtils.toggleHideyBar(ActivityVideoViewPlaying.this);
                    normalHeaderRl.removeAllViews();
                    normalControllerRl.removeAllViews();
                    fullHeaderRl.addView(headerBar);
                    fullControllerRl.addView(mediaController);

                    isFullScreen = true;
                    ibScreen.setBackgroundResource(R.drawable.img_video_btn_to_mini );
                    hideOuterAfterFiveSeconds();
                }
            }

        });
        mediaController.setNextListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // fetch the next video. 为了demo的简便性，仅从主页列表获取
//                if (playList == null) {
//                    playList = SharedPrefsStore.getAllMainVideoFromSP(getApplicationContext());
//                }
//                int i = 0;
//                int length = playList.size();
//                for (i = 0; i < length; ++i) {
//                    VideoInfo fInfo = playList.get(i);
//                    if (fInfo.getUrl().equals(mVideoSource) && fInfo.getTitle().equals(info.getTitle())) {
//                        break;
//                    }
//                }
//                if (i == length - 1) {
//                    // is already the last one
//                    Toast.makeText(getApplicationContext(), "已经是最后一个", Toast.LENGTH_SHORT).show();
//                } else if (i < length - 1) {
//                    // set the next info
//                    info = playList.get(i + 1);
//                    tryToPlayOther();
//                    mediaController.clearViewContent();
//                } else {
//                    // i >= length, should not come in
//                    Log.d(TAG, "i >= length, should not come in");
//                }
            }

        });
        mediaController.setPreListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // fetch the pre video. 为了demo的简便性，仅从主页列表获取
//                if (playList == null) {
//                    playList = SharedPrefsStore.getAllMainVideoFromSP(getApplicationContext());
//                }
//                int i = 0;
//                int length = playList.size();
//                for (i = 0; i < length; ++i) {
//                    VideoInfo fInfo = playList.get(i);
//                    if (fInfo.getUrl().equals(info.getUrl()) && fInfo.getTitle().equals(info.getTitle())) {
//                        break;
//                    }
//                }
//                if (i == 0) {
//                    // is already the first one
//                    Toast.makeText(getApplicationContext(), "已经是第一个", Toast.LENGTH_SHORT).show();
//                } else if (i < length) {
//                    // set the next info
//                    info = playList.get(i - 1);
//                    tryToPlayOther();
//                    mediaController.clearViewContent();
//                } else {
//                    // i >= length, should not come in
//                    Log.d(TAG, "i >= length, should not come in");
//                }
            }

        });

        mediaController.setDownloadListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (info.getUrl().startsWith("file://")) {
//                    Toast.makeText(ActivityVideoViewPlaying.this, "该资源已经是本地文件", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (!info.getUrl().endsWith(".m3u8")) {
//                    Toast.makeText(ActivityVideoViewPlaying.this, "抱歉，当前仅支持m3u8资源的下载", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                VideoDownloadManager downloadManagerInstance = VideoDownloadManager
//                        .getInstance(ActivityVideoViewPlaying.this, MainActivity.SAMPLE_USER_NAME);
//                DownloadableVideoItem item = downloadManagerInstance
//                        .getDownloadableVideoItemByUrl(info.getUrl());
//                if (item != null) {
//                    // already
//                    Toast.makeText(ActivityVideoViewPlaying.this, "该资源已经在缓存列表，请到「本地缓存」查看", Toast.LENGTH_SHORT).show();
//                } else {
//                    SharedPrefsStore.addToCacheVideo(ActivityVideoViewPlaying.this, info);
//                    SampleObserver sampleObs = new SampleObserver();
//                    DownloadObserverManager.addNewObserver(info.getUrl(), sampleObs);
//                    downloadManagerInstance.startOrResumeDownloader(info.getUrl(), sampleObs);
//                    Toast.makeText(AdvancedPlayActivity.this, "开始缓存，可到「本地缓存」查看进度", Toast.LENGTH_SHORT).show();
//                }
            }

        });

        if (!SharedPrefsStore.isDefaultPortrait(this)) {
            ibScreen.performClick();
        }
    }

    /**
     * 播放下一个视频源
     *
     * 下一个视频源可以是：
     * 一个全新的视频：反注释下面的reSetRender，清除上一个播放源的最后一帧；
     * 同一视频的另一种分辨率的新链接：1、在stopPlayback之前拿到当前播放位置；2、反注释setInitPlayPosition设置位置；
     */
    private void tryToPlayOther() {
//        mVV.stopPlayback(); // 释放上一个视频源
//        mVV.setInitPlayPosition(1 * 1000); // 指定初始播放位置，单位为毫秒；
//        mVV.reSetRender(); // 清除上一个播放源的最后遗留的一帧
//        mVV.setVideoPath(info.getUrl());
//        mVV.vector_demo10_start();
    }

    boolean isPausedByOnPause = false;

    @Override
    protected void onPause() {
        super.onPause();
        // 当home键切出，想暂停视频的话，反注释下面的代码。同时要反注释onResume中的代码
        if (mVV.isPlaying()) {
            isPausedByOnPause = true;
            mVV.pause();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 当home键切出，暂停了视频此时想回复的话，反注释下面的代码
        if (isPausedByOnPause) {
            isPausedByOnPause = false;
            mVV.start();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mVV != null) {
            mVV.enterForeground();
        }
    }

    @Override
    protected void onStop() {
        // enterBackground should be invoke before super.onStop()
        if (mVV != null) {
            mVV.enterBackground();
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mVV != null) {
            mVV.stopPlayback(); // 释放播放器资源
            mVV.release(); // 释放播放器资源和显示资源
        }

        if (mediaController != null) {
            mediaController.release();
        }

        if (toast != null) {
            toast.cancel();
        }
        super.onDestroy();
    }

    /**
     * 检测'点击'空白区的事件，若播放控制控件未显示，设置为显示，否则隐藏。
     *
     * @param v
     */
    public void onClickEmptyArea(View v) {
        if (!isFullScreen) {
            return;
        }
        if (barTimer != null) {
            barTimer.cancel();
            barTimer = null;
        }
        if (this.mediaController != null) {
            if (mediaController.getVisibility() == View.VISIBLE) {
                mediaController.hide();
                headerBar.setVisibility(View.GONE);
            } else {
                mediaController.show();
                headerBar.setVisibility(View.VISIBLE);
                hideOuterAfterFiveSeconds();
            }
        }
    }

    private void hideOuterAfterFiveSeconds() {
        if (!isFullScreen) {
            return;
        }
        if (barTimer != null) {
            barTimer.cancel();
            barTimer = null;
        }
        barTimer = new Timer();
        barTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!isFullScreen) {
                    return;
                }
                if (mediaController != null) {
                    mediaController.getMainThreadHandler().post(new Runnable() {

                        @Override
                        public void run() {
                            mediaController.hide();
                            headerBar.setVisibility(View.GONE);
                        }

                    });
                }
            }

        }, 5 * 1000);

    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
//        Log.d(TAG, "onBufferingUpdate percent=" + percent);
        if (mediaController != null && mVV != null) {
            mediaController.onTotalCacheUpdate(percent * mVV.getDuration() / 100);
        }
    }

    @Override
    public void onPlayerStateChanged(BDCloudVideoView.PlayerState nowState) {
        if (mediaController != null) {
            mediaController.changeState();
        }
    }
}
