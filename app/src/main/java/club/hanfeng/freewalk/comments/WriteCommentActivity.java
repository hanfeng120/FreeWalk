package club.hanfeng.freewalk.comments;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.comments.CommentConstants;
import club.hanfeng.freewalk.core.comments.data.CommentInfo;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class WriteCommentActivity extends BaseActivity {

    private String name;
    private String id;
    private TextView content;
    private RatingBar ratingBar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_write_comment;
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
        name = getIntent().getStringExtra(CommentConstants.EXTRA_SCENE_NAME);
        id = getIntent().getStringExtra(CommentConstants.EXTRA_SCENE_ID);
    }

    @Override
    protected void initTopBar() {
        setTitle("写点评");
    }

    @Override
    protected void initContent() {
        ((TextView) findViewById(R.id.location)).setText(name);
        content = (TextView) findViewById(R.id.comment);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
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

    private void sendComment() {
        if (!TextUtils.isEmpty(content.getText().toString()) && ratingBar.getRating() > 0) {
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.setSid(FreeWalkApplication.getSid());
            commentInfo.setId(id);
            commentInfo.setUid(BmobUser.getCurrentUser(getContext()).getUsername());
            commentInfo.setPortraitUrl(BmobUser.getCurrentUser(getContext(), MyUser.class).getPortraitUrl());
            commentInfo.setNickName(BmobUser.getCurrentUser(getContext(), MyUser.class).getNickName());
            commentInfo.setComment(content.getText().toString());
            commentInfo.setStars((int) ratingBar.getRating());
            commentInfo.save(getContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    content.setText(null);
                    OutputUtils.toastShort(getContext(), "点评成功");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    OutputUtils.toastShort(getContext(), "点评失败啦，请重试...");
                }
            });
        } else {
            OutputUtils.toastShort(getContext(), "填写完整啦");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_studio_step2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            sendComment();
        }
        return super.onOptionsItemSelected(item);
    }

}
