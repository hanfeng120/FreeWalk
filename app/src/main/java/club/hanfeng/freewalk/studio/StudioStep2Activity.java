package club.hanfeng.freewalk.studio;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.btp.callback.UploadListener;

import java.io.File;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.studio.StudioConstants;
import club.hanfeng.freewalk.core.studio.StudioManager;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.utils.FileUtils;
import club.hanfeng.freewalk.utils.FreeWalkProgress;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

public class StudioStep2Activity extends BaseActivity {

    private TextView sceneLocation;
    private RelativeLayout locationContent;
    private EditText comment;
    private RatingBar ratingBar;

    private String filePath;
    private Uri uri;
    private String id;
    private String name;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_studio_step2;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        setTitle("现场直播");
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
        sceneLocation = (TextView) findViewById(R.id.studio_location);
        comment = (EditText) findViewById(R.id.studio_comment);
        locationContent = (RelativeLayout) findViewById(R.id.studio_location_content);
        ratingBar = (RatingBar) findViewById(R.id.studio_rating);

        if (!TextUtils.isEmpty(filePath)) {
            ((ImageView) findViewById(R.id.studio_image)).setImageURI(Uri.fromFile(new File(filePath)));
        }
        if (uri != null) {
            ((ImageView) findViewById(R.id.studio_image)).setImageURI(uri);
        }
        findViewById(R.id.studio_location_content).setOnClickListener(getOnClickListener());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                }
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            if (!TextUtils.isEmpty(data.getStringExtra("name"))) {
                name = data.getStringExtra("name");
                id = data.getStringExtra("id");
                sceneLocation.setText(name);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.studio_location_content:
                startActivityForResult(new Intent(getContext(), StudioSceneListActivity.class), 0);
                break;
        }
    }

    private void sendStudioInfo() {
        if (filePath != null || uri != null) {
            if (!TextUtils.isEmpty(id)) {
                if (ratingBar.getRating() > 0) {
                    FreeWalkProgress.show(getContext(), "正在发布...");
                    uploadPicture();
                } else {
                    OutputUtils.toastShort(getContext(), "给景区评个分呗~");
                }
            } else {
                OutputUtils.toastShort(getContext(), "请选择景区");
            }
        } else {
            OutputUtils.toastShort(getContext(), "加载图片失败，请重试");
            finish();
        }
    }

    private void uploadPicture() {
        String tempPath = "";
        if (!TextUtils.isEmpty(filePath)) {
            tempPath = filePath;
        }
        if (uri != null) {
            tempPath = FileUtils.getPathFromUri(getContext(), uri);
        }
        if (TextUtils.isEmpty(tempPath)) {
            return;
        }
        StudioManager.getInstance().upLoadStudioPic(getContext(), tempPath, new UploadListener() {
            @Override
            public void onError(int i, String s) {
                FreeWalkProgress.dismiss(getContext());
            }

            @Override
            public void onSuccess(String s, String s1, BmobFile bmobFile) {
                uploadStudioInfo(bmobFile.getUrl());
            }

            @Override
            public void onProgress(int i) {

            }
        });
    }

    private void uploadStudioInfo(String imageUrl) {
        StudioInfo studioInfo = new StudioInfo();
        studioInfo.setUid(BmobUser.getCurrentUser(getContext(), MyUser.class).getUsername());
        studioInfo.setSid(FreeWalkApplication.getSid());
        studioInfo.setId("0001");
        studioInfo.setName("鸟巢");
        studioInfo.setIntroduce(comment.getText().toString());
        studioInfo.setImageUrl(imageUrl);
        studioInfo.setComments(12);
        studioInfo.setStars(23);
        StudioManager.getInstance().upLoadStudio(getContext(), studioInfo, new SaveListener() {
            @Override
            public void onSuccess() {
                FreeWalkProgress.dismiss(getContext());
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                FreeWalkProgress.dismiss(getContext());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_studio_step2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            sendStudioInfo();
        }
        return super.onOptionsItemSelected(item);
    }
}