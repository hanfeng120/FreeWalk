package club.hanfeng.freewalk.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.zxing.activity.CaptureActivity;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.constants.Constants;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.utils.FreeWalkToast;
import club.hanfeng.freewalk.scene.SceneActivity;

public class ScanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        startActivityForResult(new Intent(this, CaptureActivity.class), 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            if (TextUtils.isEmpty(result)) {
                FreeWalkToast.shortToast("无效二维码");
            } else {

                String[] results = result.split(";");
                if (Constants.SCAN_KEY.equals(results[0])) {
                    if (FreeWalkApplication.getSid().equals(results[1])) {
                        Intent sceneIntent = new Intent(this, SceneActivity.class);
                        sceneIntent.putExtra(SceneConstants.EXTRA_ID, results[2]);
                        sceneIntent.putExtra(SceneConstants.EXTRA_TITLE, results[3]);
                        startActivity(sceneIntent);
                    } else {
                        FreeWalkToast.shortToast("请选择当前景区后再次扫描");
                    }
                    finish();
                }
            }
        } else {
            finish();
        }
    }
}
