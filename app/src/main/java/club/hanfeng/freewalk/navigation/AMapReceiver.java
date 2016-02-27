package club.hanfeng.freewalk.navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import club.hanfeng.freewalk.core.navigation.NavigationConstants;

public class AMapReceiver extends BroadcastReceiver {
    public AMapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra(NavigationConstants.TYPE_ID);
        String voicePath = intent.getStringExtra(NavigationConstants.TYPE_VOICE_PATH);

//        if (file.exists()) {
//            if (playerService != null) {
//                playOrPause();
//            }
//        } else if (isLoading) {
//            OutputUtils.toastShort(this, "语音文件正在加载...");
//        } else {
//            pbDialog.show();
//            downloadVoiceFile();
//        }
    }
}
