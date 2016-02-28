package club.hanfeng.freewalk.navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import club.hanfeng.freewalk.core.navigation.NavigationConstants;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.scene.SceneActivity;

public class AMapReceiver extends BroadcastReceiver {
    public AMapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra(NavigationConstants.TYPE_ID);
        String title = intent.getStringExtra(SceneConstants.EXTRA_TITLE);

        Intent sceneIntent = new Intent(context, SceneActivity.class);
        sceneIntent.putExtra(SceneConstants.EXTRA_ID, id);
        sceneIntent.putExtra(SceneConstants.EXTRA_TITLE, title);
        sceneIntent.putExtra(SceneConstants.EXTRA_AUTO_PLAY, true);
        context.startActivity(sceneIntent);
    }
}
