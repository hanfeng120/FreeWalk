package club.hanfeng.freewalk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import club.hanfeng.freewalk.R;

public class PictureViewerActivity extends AppCompatActivity {

    private ImageView ivContent;
    private RelativeLayout rlLabel;
    private ImageView ivBack;
    private ImageView ivShare;
    private ImageView ivComments;
    private TextView tvComments;
    private ImageView ivStars;
    private TextView tvStars;
    private ImageView ivSave;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_viewer);

        initView();
        initData();
        setListener();

    }

    private void setListener() {

    }

    private void initData() {

    }

    private void initView() {
        ivContent = (ImageView)findViewById( R.id.iv_picture_viewer_content );
        rlLabel = (RelativeLayout)findViewById( R.id.rl_picture_viewer_label );
        ivBack = (ImageView)findViewById( R.id.iv_picture_viewer_back );
        ivShare = (ImageView)findViewById( R.id.iv_picture_viewer_share );
        ivComments = (ImageView)findViewById( R.id.iv_picture_viewer_comments );
        tvComments = (TextView)findViewById( R.id.tv_picture_viewer_comments );
        ivStars = (ImageView)findViewById( R.id.iv_picture_viewer_stars );
        tvStars = (TextView)findViewById( R.id.tv_picture_viewer_stars );
        ivSave = (ImageView)findViewById( R.id.iv_picture_viewer_save );
        tvLocation = (TextView)findViewById( R.id.tv_picture_viewer_location );
    }

    public void clickItem(View view) {
        switch (view.getId()) {
            case R.id.iv_picture_viewer_back:
                finish();
                break;
            case R.id.iv_picture_viewer_share:

                break;
            case R.id.iv_picture_viewer_comments:

                break;
            case R.id.iv_picture_viewer_stars:

                break;
            case R.id.iv_picture_viewer_save:

                break;
            case R.id.iv_picture_viewer_content:
                showOrHideMenu();
                break;
            case R.id.ll_picture_viewer_user:

                break;
            case R.id.tv_picture_viewer_location:
                Intent intent = new Intent(this, SceneActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("title", "图片查看器");
                startActivity(intent);
                break;
            case R.id.rl_picture_viewer:
                showOrHideMenu();
                break;
        }
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

}
