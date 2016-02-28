package club.hanfeng.freewalk.studio;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.collection.CollectionManager;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.studio.StudioConstants;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.core.user.UserManager;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.core.utils.ImageLoaderUtil;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.scene.SceneActivity;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.ShareUtils;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class PictureViewerActivity extends BaseActivity {

    private ImageView ivContent;
    private RelativeLayout rlLabel;
    private ImageView ivBack;
    private ImageView ivShare;
    private Button ivComments;
    private TextView tvComments;
    private Button ivStars;
    private TextView tvStars;
    private Button ivSave;
    private TextView tvLocation;
    private TextView tvSendTime;
    private ImageView portrait;
    private TextView userName;

    private StudioInfo studioInfo;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_picture_viewer;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initIntentData() {
        studioInfo = (StudioInfo) getIntent().getSerializableExtra(StudioConstants.EXTRA_TYPE_STUDIO);
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        ivContent = (ImageView) findViewById(R.id.iv_picture_viewer_content);
        rlLabel = (RelativeLayout) findViewById(R.id.rl_picture_viewer_label);
        ivBack = (ImageView) findViewById(R.id.iv_picture_viewer_back);
        ivShare = (ImageView) findViewById(R.id.iv_picture_viewer_share);
        ivComments = (Button) findViewById(R.id.iv_picture_viewer_comments);
        tvComments = (TextView) findViewById(R.id.tv_picture_viewer_comments);
        ivStars = (Button) findViewById(R.id.iv_picture_viewer_stars);
        tvStars = (TextView) findViewById(R.id.tv_picture_viewer_stars);
        ivSave = (Button) findViewById(R.id.iv_picture_viewer_save);
        tvLocation = (TextView) findViewById(R.id.tv_picture_viewer_location);
        tvSendTime = (TextView) findViewById(R.id.send_time);
        portrait = (ImageView) findViewById(R.id.portrait);
        userName = (TextView) findViewById(R.id.username);

        ivContent.setOnClickListener(getOnClickListener());
        rlLabel.setOnClickListener(getOnClickListener());
        ivBack.setOnClickListener(getOnClickListener());
        ivShare.setOnClickListener(getOnClickListener());
        ivComments.setOnClickListener(getOnClickListener());
        tvComments.setOnClickListener(getOnClickListener());
        ivStars.setOnClickListener(getOnClickListener());
        tvStars.setOnClickListener(getOnClickListener());
        ivSave.setOnClickListener(getOnClickListener());
        tvLocation.setOnClickListener(getOnClickListener());
        portrait.setOnClickListener(getOnClickListener());
        userName.setOnClickListener(getOnClickListener());
        findViewById(R.id.rl_picture_viewer).setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        if (studioInfo == null) {
            return;
        }
        loadStudioInfo();
        loadUserInfo();
    }

    private void loadStudioInfo() {
        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ImageOptions options = builder.build();
        x.image().bind(ivContent, studioInfo.getImageUrl(), options);
        tvLocation.setText(studioInfo.getName());
        tvSendTime.setText(studioInfo.getCreatedAt());
        tvComments.setText(studioInfo.getComments() + "");
        tvStars.setText(studioInfo.getStars() + "");
    }

    private void loadUserInfo() {
        UserManager.getInstance().loadUserInfo(getContext(), studioInfo.getUid(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                if (list != null && list.size() > 0) {
                    initUserInfo(list.get(0));
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initUserInfo(MyUser user) {
        ImageLoaderUtil.displayImageInRound(user.getPortraitUrl(), portrait);
        userName.setText(user.getNickName());
    }

    @Override
    protected void refreshView(int viewId) {

    }

    /**
     * 显示或者隐藏内容区域以外的菜单
     */
    private void showOrHideMenu() {
        if (rlLabel.isShown()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(300);
            alphaAnimation.setFillAfter(true);
            rlLabel.startAnimation(alphaAnimation);
            rlLabel.setVisibility(View.GONE);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(300);
            alphaAnimation.setFillAfter(true);
            rlLabel.startAnimation(alphaAnimation);
            rlLabel.setVisibility(View.VISIBLE);
        }
    }

    private void collectStudio() {
        CollectionManager.getInstance().saveStudioPhoto(getContext(), studioInfo, new SaveListener() {
            @Override
            public void onSuccess() {
                OutputUtils.toastShort(getContext(), "保存到我的相册中");
            }

            @Override
            public void onFailure(int i, String s) {
                if (i == -1) {
                    OutputUtils.toastShort(getContext(), "请先登录");
                } else {
                    OutputUtils.toastShort(getContext(), "已经保存过了哦");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture_viewer_back:
                finish();
                break;
            case R.id.iv_picture_viewer_share:
                ShareUtils.shareInfo(getContext(), "", studioInfo.getImageUrl());
                break;
            case R.id.iv_picture_viewer_comments:

                break;
            case R.id.iv_picture_viewer_stars:
                collectStudio();
                break;
            case R.id.iv_picture_viewer_save:

                break;
            case R.id.iv_picture_viewer_content:
                showOrHideMenu();
                break;
            case R.id.ll_picture_viewer_user:

                break;
            case R.id.tv_picture_viewer_location:
                Intent intent = new Intent(getContext(), SceneActivity.class);
                intent.putExtra(SceneConstants.EXTRA_ID, studioInfo.getId());
                startActivity(intent);
                break;
            case R.id.rl_picture_viewer_label:
                showOrHideMenu();
                break;
        }
    }
}
