package club.hanfeng.freewalk.studio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.studio.StudioConstants;
import club.hanfeng.freewalk.core.studio.StudioManager;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.user.UserConstants;
import cn.bmob.v3.BmobUser;

public class StudioActivity extends BaseActivity {

    private String[] items = new String[]{"拍照", "从相册选择"};
    private String filePath;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_studio;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initTopBar() {
    }

    @Override
    protected void initContent() {
        StudioDetail studioDetail = new StudioDetail(getContext());
        studioDetail.setTaskId(StudioConstants.STUDIO_TASKID);
        studioDetail.createRootView(null);
        studioDetail.init();

        ((FrameLayout) findViewById(R.id.studio_content)).addView(studioDetail.getRootView());
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case StudioConstants.CAMERA_REQUEST_CODE:
                    Intent intent = new Intent(getContext(), StudioStep1Activity.class);
                    intent.putExtra(StudioConstants.EXTRA_TYPE_FROM, StudioConstants.CAMERA_REQUEST_CODE);
                    intent.putExtra(StudioConstants.CACHE_FILE_PATH, filePath);
                    startActivity(intent);
                    break;
                case StudioConstants.IMAGE_REQUEST_CODE:
                    if (data != null) {
                        data.setClass(getContext(), StudioStep1Activity.class);
                        data.putExtra(StudioConstants.EXTRA_TYPE_FROM, StudioConstants.IMAGE_REQUEST_CODE);
                        data.setData(data.getData());
                        startActivity(data);
                    }
                    break;
            }
        }
    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(getContext())
                .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intentCapture = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        intentCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(StudioManager.getInstance().getCacheFile(filePath)));
                                        startActivityForResult(intentCapture,
                                                StudioConstants.CAMERA_REQUEST_CODE);
                                        break;
                                    case 1:
                                        Intent intentGallery = new Intent();
                                        intentGallery.setType("image/*");
                                        intentGallery
                                                .setAction(Intent.ACTION_PICK);
                                        startActivityForResult(intentGallery,
                                                StudioConstants.IMAGE_REQUEST_CODE);
                                        break;
                                }
                            }
                        }
                )
                .setCancelable(true)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_studio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            if (BmobUser.getCurrentUser(getContext(), MyUser.class) == null) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra(UserConstants.FROM_USERPAGE, true);
                ((Activity) getContext()).startActivityForResult(intent, MainPageConstants.REQUEST_USER_PAGE);
            } else {
                filePath = StudioManager.getInstance().getPhotoName();
                showDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
