package club.hanfeng.freewalk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import club.hanfeng.freewalk.core.navigation.NavigationConstants;
import club.hanfeng.freewalk.core.utils.FreeWalkToast;
import club.hanfeng.freewalk.framework.DataCenter;
import club.hanfeng.freewalk.service.aidl.IVoicePlayerService;
import club.hanfeng.freewalk.utils.CommonUtils;

/**
 * Created by HanFeng on 2016/2/27.
 */
public class VoicePlayerAgency {

    private static volatile VoicePlayerAgency instance;

    private IVoicePlayerService playerService;

    private boolean isDownloading;

    private VoicePlayerAgency() {

    }

    public static VoicePlayerAgency getInstance() {
        if (instance == null) {
            synchronized (VoicePlayerAgency.class) {
                if (instance == null) {
                    instance = new VoicePlayerAgency();
                }
            }
        }
        return instance;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playerService = IVoicePlayerService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void init(Context context) {
        Intent serviceIntent = new Intent(context, VoicePlayerService.class);
        context.bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
        context.startService(serviceIntent);
    }

    public void create(String path) {
        try {
            playerService.createMediaPlayer(path);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void playOrPause() {
        try {
            if (playerService.isPlaying()) {
                pause();
            } else {
                play();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            playerService.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            playerService.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            playerService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getDuration() {
        try {
            return playerService.getDuration();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isPlaying() {
        try {
            return playerService.isPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCurrentPosition() {
        try {
            return playerService.getCurrentPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getFilePath() {
        try {
            return playerService.getFilePath();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void seekTo(int position) {
        try {
            playerService.seekTo(position);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void start(String voicePath) {
        final String filePath = CommonUtils.combinePath(voicePath, "/voice/");
        if (filePath.equals(getFilePath())) {
            if (!isPlaying()) {
                play();
            }
        } else {
            if (createFileOnLocal(filePath).exists()) {
                create(filePath);
                playOrPause();
                DataCenter.getInstance().notifyDataChanged(NavigationConstants.TASK_ID_PLAYER_CHANGED);
            } else {
                if (isDownloading) {
                    FreeWalkToast.shortToast("语音文件正在加载中");
                } else {
                    isDownloading = true;
                    downLoadFile(filePath, voicePath);
                }
            }
        }

    }

    private File createFileOnLocal(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private void downLoadFile(final String filePath, String voicePath) {
        RequestParams requestParams = new RequestParams(voicePath);
        requestParams.setSaveFilePath(filePath);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(File result) {
                isDownloading = false;
                create(filePath);
                playOrPause();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                FreeWalkToast.shortToast("资源加载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
