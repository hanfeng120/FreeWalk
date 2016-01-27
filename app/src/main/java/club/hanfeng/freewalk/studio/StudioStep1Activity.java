package club.hanfeng.freewalk.studio;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.studio.StudioConstants;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.utils.OutputUtils;

public class StudioStep1Activity extends BaseActivity {

    private String filePath;
    private Uri uri;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_studio_step1;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected void initIntentData() {
        int from = getIntent().getIntExtra(StudioConstants.EXTRA_TYPE_FROM, -1);
        if (from >= 0) {
            switch (from) {
                case StudioConstants.CAMERA_REQUEST_CODE:
                    filePath = getIntent().getStringExtra(StudioConstants.CACHE_FILE_PATH);
                    break;
                case StudioConstants.IMAGE_REQUEST_CODE:
                    uri = getIntent().getData();
                    break;
            }
        }

    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        if (!TextUtils.isEmpty(filePath)) {
            ((ImageView) findViewById(R.id.iv_content)).setImageURI(Uri.fromFile(new File(filePath)));
        }
        if (uri != null) {
            ((ImageView) findViewById(R.id.iv_content)).setImageURI(uri);
        }
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_studio_step1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_next) {
            OutputUtils.toastShort(getContext(), "Test");
            Intent intent = getIntent();
            intent.setClass(getContext(), StudioStep2Activity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
