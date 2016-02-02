package club.hanfeng.freewalk.comments;

import android.view.View;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.comments.CommentConstants;
import club.hanfeng.freewalk.framework.BaseActivity;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class CommentActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_comments;
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
    protected void initTopBar() {
        setTitle(getIntent().getStringExtra(CommentConstants.EXTRA_SCENE_NAME));
    }

    @Override
    protected void initContent() {
        CommentDetail commentDetail = new CommentDetail(getContext());
        commentDetail.createRootView(null);
        commentDetail.init();
        commentDetail.setId(getIntent().getStringExtra(CommentConstants.EXTRA_SCENE_ID));
        ((FrameLayout) (findViewById(R.id.content))).addView(commentDetail.getRootView());
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
}
