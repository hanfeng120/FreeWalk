package club.hanfeng.freewalk.utils;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;

/**
 * Created by HanFeng on 2015/10/23.
 */
public class VoiceGuideUtils {

    private Activity activity;

    public VoiceGuideUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 播放语音导览，如果不存在则从网络下载，完成后播放
     *
     * @param url 语音导览文件地址
     * @param player 播放器对象
     * @param progressBar 进度条对象
     */
    public void playVoiceGuide(String url,MediaPlayer player,ProgressBar progressBar) {

    }

}
