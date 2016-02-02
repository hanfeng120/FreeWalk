package club.hanfeng.freewalk.scene;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.ScenePagerAdapter;
import club.hanfeng.freewalk.comments.WriteCommentActivity;
import club.hanfeng.freewalk.core.collection.CollectionManager;
import club.hanfeng.freewalk.core.comments.CommentConstants;
import club.hanfeng.freewalk.core.scene.SceneBaseAdapter;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.scene.SceneManager;
import club.hanfeng.freewalk.core.scene.data.SceneInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.service.VoicePlayerService;
import club.hanfeng.freewalk.service.aidl.IVoicePlayerService;
import club.hanfeng.freewalk.utils.CommonUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SceneActivity extends BaseActivity {

    private static final int WHAT_PROGRESS_UPDATE = 1;

    private ViewPager viewPager;
    private ProgressDialog pbDialog;
    private ToggleButton tbPlay;
    private TextView tvCurDur, tvDuration;
    private SeekBar sbProgress;
    private ListView listView;
    private ImageView ivBack;
    private ImageView ivComment, ivCollect, ivShare;
    private RelativeLayout rlTop;
    private TextView tvTitle;
    private View headerView;
    private TextView tvComment, tvCollect, tvShare;

    private List<SceneInfo> sceneInfos;
    private String id;
    private String title;
    private SceneBaseAdapter adapter;
    private int headerViewHeight;
    private boolean isLoading;

    private boolean autoPlay;
    private boolean perDownInG;
    private File file;

    private IVoicePlayerService playerService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROGRESS_UPDATE:
                    int curDuration = 0;
                    try {
                        curDuration = playerService.getCurrentPosition();
                        sbProgress.setProgress(curDuration);
                        handler.sendEmptyMessageDelayed(WHAT_PROGRESS_UPDATE, 500);
                        tvCurDur.setText(CommonUtils.stringForTime(curDuration));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playerService = IVoicePlayerService.Stub.asInterface(service);
            updateUI();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scene_back:
                finish();
                break;
            case R.id.tb_media_play:
                if (file.exists()) {
                    if (playerService != null) {
                        playOrPause();
                    }
                } else if (isLoading) {
                    OutputUtils.toastShort(this, "语音文件正在加载...");
                } else {
                    pbDialog.show();
                    downloadVoiceFile();
                }
                break;
            case R.id.iv_scene_go:
                break;
            case R.id.scene_comment:
                Intent intent = new Intent(getContext(), WriteCommentActivity.class);
                intent.putExtra(CommentConstants.EXTRA_SCENE_NAME, title);
                intent.putExtra(CommentConstants.EXTRA_SCENE_ID, id);
                startActivity(intent);
                break;
            case R.id.scene_collect:
                collectScene();
                break;
            case R.id.scene_share:
                break;
        }
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_scene;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initIntentData() {
        id = getIntent().getStringExtra(SceneConstants.EXTRA_ID);
        title = getIntent().getStringExtra(SceneConstants.EXTRA_TITLE);
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        listView = (ListView) findViewById(R.id.lv_scene);
        tbPlay = (ToggleButton) findViewById(R.id.tb_media_play);
        tvCurDur = (TextView) findViewById(R.id.tv_media_current);
        tvDuration = (TextView) findViewById(R.id.tv_media_duration);
        sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        ivBack = (ImageView) findViewById(R.id.iv_scene_back);
        rlTop = (RelativeLayout) findViewById(R.id.rl_scene_top);
        tvTitle = (TextView) findViewById(R.id.tv_scene_title);

        headerView = View.inflate(this, R.layout.header_lv_scene, null);
        viewPager = (ViewPager) headerView.findViewById(R.id.vp_scene);
        tvComment = (TextView) headerView.findViewById(R.id.scene_comment);
        tvCollect = (TextView) headerView.findViewById(R.id.scene_collect);
        tvShare = (TextView) headerView.findViewById(R.id.scene_share);

        listView.addHeaderView(headerView);

        ivBack.setOnClickListener(getOnClickListener());
        tbPlay.setOnClickListener(getOnClickListener());
        tvComment.setOnClickListener(getOnClickListener());
        tvCollect.setOnClickListener(getOnClickListener());
        tvShare.setOnClickListener(getOnClickListener());

        headerView.measure(0, 0);
        headerViewHeight = headerView.getMeasuredHeight();
        rlTop.setAlpha(0);
        tvTitle.setAlpha(0);

        setListener();
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        pbDialog = new ProgressDialog(this);
        pbDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pbDialog.setCanceledOnTouchOutside(false);
        pbDialog.setTitle("正在下载");

        autoPlay = SpUtils.getInstance(this).getValue(SpUtils.SETTING_AUTOPLAY, false);
        perDownInG = SpUtils.getInstance(this).getValue(SpUtils.SETTING_DOWNLOAD, false);

        bindAtService();
        getDataFromServer();
    }

    private void collectScene() {
        CollectionManager.getInstance().saveSceneCollection(getContext(), id, new SaveListener() {
            @Override
            public void onSuccess() {
                OutputUtils.toastShort(getContext(), "收藏成功");
            }

            @Override
            public void onFailure(int i, String s) {
                if (i == -1) {
                    OutputUtils.toastShort(getContext(), "请先登录");
                } else {
                    OutputUtils.toastShort(getContext(), "您已经收藏过了哦");
                }
            }
        });
    }

    private void updateUI() {
        try {
            if (file != null && file.exists()) {
                if (!file.getPath().equals(playerService.getFilePath())) {
                    handler.removeCallbacksAndMessages(null);
                    playerService.stop();
                    sbProgress.setProgress(0);
                    tvCurDur.setText("00:00");
                    tvDuration.setText("00:00");
                    createPlayerService();
                }
            }
            if (playerService != null) {
                if (playerService.isPlaying()) {
                    tbPlay.setChecked(true);
                    setMediaDefault();
                    handler.sendEmptyMessage(WHAT_PROGRESS_UPDATE);
                } else {
                    tbPlay.setChecked(false);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindAtService() {
        Intent serviceIntent = new Intent(this, VoicePlayerService.class);
        bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
        startService(serviceIntent);
    }

    @Override
    protected void refreshView(int viewId) {

    }

    /**
     * 控件监听器
     */
    private void setListener() {
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && playerService != null) {
                    try {
                        playerService.seekTo(progress);
                        tvCurDur.setText(CommonUtils.stringForTime(progress));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (playerService != null) {
                    pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (playerService != null) {
                    play();
                } else {
                    sbProgress.setProgress(0);
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (getHeaderOnScreenY() > headerViewHeight / 2) {
                    rlTop.setAlpha(1);
                    tvTitle.setAlpha(1);
                } else {
                    float alpha = getHeaderOnScreenY() * 2f / headerViewHeight;
                    rlTop.setAlpha(alpha);
                    tvTitle.setAlpha(alpha);
                }
            }
        });
    }


    /**
     * 网络服务器中获得数据存入JavaBean中
     */
    public void getDataFromServer() {
        SceneManager.getInstance().getSceneInfo(getContext(), id, new FindListener<SceneInfo>() {
            @Override
            public void onSuccess(List<SceneInfo> list) {
                if (list != null && list.size() > 0) {
                    sceneInfos = list;
                    parseData();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 解析服务器数据，设置页面
     */
    public void parseData() {
        if (sceneInfos.get(0).headImages.size() > 0) {
            viewPager.setAdapter(new ScenePagerAdapter(this, sceneInfos.get(0).headImages));
            viewPager.setCurrentItem(0);
        } else {
            //TODO 默认图片
        }

        adapter = new SceneBaseAdapter(this, sceneInfos);
        listView.setAdapter(adapter);

        createFile();
        updateUI();
    }

    private void createFile() {
        file = new File(CommonUtils.combinePath(sceneInfos.get(0).voicePath, "/voice/"));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists() && perDownInG && autoPlay) {
            downloadVoiceFile();
        }
    }

    /**
     * 下载语音导航文件
     */
    public void downloadVoiceFile() {
        isLoading = true;
        RequestParams requestParams = new RequestParams(sceneInfos.get(0).voicePath);
        requestParams.setSaveFilePath(file.getPath());
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pbDialog.setMax((int) total);
                pbDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {
                pbDialog.dismiss();
                createPlayerService();
                playOrPause();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                pbDialog.dismiss();
                isLoading = false;
                tbPlay.setChecked(false);
                OutputUtils.toastShort(SceneActivity.this, "网络错误，请重试...");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createPlayerService() {
        try {
            if (file.exists()) {
                playerService.createMediaPlayer(file.getPath());
            }
            setMediaDefault();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放或者暂停播放语音导览
     */
    private void playOrPause() {
        try {
            if (playerService.isPlaying()) {//暂停播放
                pause();
            } else {//播放
                play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置语音资源初始化信息
     */
    private void setMediaDefault() {
        try {
            sbProgress.setMax(playerService.getDuration());
            tvDuration.setText(CommonUtils.stringForTime(playerService.getDuration()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    private void pause() {
        try {
            tbPlay.setChecked(false);
            playerService.pause();
            handler.removeMessages(WHAT_PROGRESS_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始播放
     */
    private void play() {
        try {
            tbPlay.setChecked(true);
            playerService.play();
            handler.removeMessages(WHAT_PROGRESS_UPDATE);
            handler.sendEmptyMessage(WHAT_PROGRESS_UPDATE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private int getHeaderOnScreenY() {
        int[] local = new int[2];
        headerView.getLocationOnScreen(local);
        int headerOnScreenY = local[1];

        listView.getLocationOnScreen(local);
        int listViewOnScreenY = local[1];
        return listViewOnScreenY - headerOnScreenY;
    }

    @Override
    protected void onDestroy() {

        if (conn != null) {
            unbindService(conn);
            conn = null;
        }

        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
