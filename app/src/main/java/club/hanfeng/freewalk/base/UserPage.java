package club.hanfeng.freewalk.base;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.xutils.x;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.AboutActivity;
import club.hanfeng.freewalk.activity.CollectActivity;
import club.hanfeng.freewalk.activity.FeedbackActivity;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.activity.PictureActivity;
import club.hanfeng.freewalk.activity.ResourceActivity;
import club.hanfeng.freewalk.activity.UserInfoActivity;
import club.hanfeng.freewalk.bean.MyUser;
import club.hanfeng.freewalk.utils.CommonUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.SpUtils;
import cn.bmob.v3.BmobUser;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class UserPage extends BasePage implements CompoundButton.OnCheckedChangeListener {

    private Switch swNews, swAutoPlay, swDownLoad;
    private TextView tvVerCode, tvName, tvTag, tvLogin;
    private Button btnExit;
    private ImageView icon;
    private RelativeLayout rlLogin;
    private boolean loginState;//用户登录状态
    private MyUser user;
    public UserPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_userpage, null);
        flBasePage.addView(view);

        swNews = (Switch) view.findViewById(R.id.sw_news);
        swAutoPlay = (Switch) view.findViewById(R.id.sw_auto_play);
        swDownLoad = (Switch) view.findViewById(R.id.sw_download);
        tvVerCode = (TextView) view.findViewById(R.id.tv_setting_vercode);
        tvName = (TextView) view.findViewById(R.id.tv_set_username);
        tvTag = (TextView) view.findViewById(R.id.tv_set_usertag);
        tvLogin = (TextView) view.findViewById(R.id.tv_set_user_login);
        rlLogin = (RelativeLayout) view.findViewById(R.id.rl_set_user_login);
        btnExit = (Button) view.findViewById(R.id.btn_set_exit);
        icon = (ImageView) view.findViewById(R.id.img_set_icon);

        swNews.setOnCheckedChangeListener(this);
        swAutoPlay.setOnCheckedChangeListener(this);
        swDownLoad.setOnCheckedChangeListener(this);

        setState();
        initUserInfo();
    }

    /**
     * 设置当前应用状态
     */
    private void setState() {
        //1  设置新消息状态
        if (SpUtils.getInstance(mActivity).getValue(SpUtils.SETTING_NEWS, false)) {
            swNews.setChecked(true);
        }
        //2  设置自动播放语音状态
        if (SpUtils.getInstance(mActivity).getValue(SpUtils.SETTING_AUTOPLAY, false)) {
            swAutoPlay.setChecked(true);
        }
        //3  设置下载资源状态
        if (SpUtils.getInstance(mActivity).getValue(SpUtils.SETTING_DOWNLOAD, false)) {
            swDownLoad.setChecked(true);
        }

        //4  设置当前版本名
        int verCode = CommonUtils.getVerCode(mActivity);
        int serverCode = SpUtils.getInstance(mActivity).getValue(SpUtils.VERSION_CODE, -1);
        if (serverCode > verCode) {
            String serverName = SpUtils.getInstance(mActivity).getValue(SpUtils.VERSION_NAME, null);
            if (serverName != null) {
                tvVerCode.setText("有新版本 " + serverName);
            }
        } else {
            tvVerCode.setText("当前已是最新版");
        }

    }

    /**
     * 用户已经登录设置登录初始信息
     */
    private void initUserInfo() {
        user = BmobUser.getCurrentUser(mActivity, MyUser.class);
        if (user == null) {//用户未登录
            return;
        }
        loginState = true;
        String userName = user.getUsername();
        String nickName = user.getNickName();
        String userTag = user.getTag();
        String userIconUrl = user.getIconUrl();
        if (!TextUtils.isEmpty(nickName)) {
            tvName.setText(nickName);
        } else if (!TextUtils.isEmpty(userName)) {
            tvName.setText(userName);
        } else {
            loginState = false;
            return;
        }
        if (!TextUtils.isEmpty(userTag)) {
            tvTag.setText(userTag);
        }

        if (!TextUtils.isEmpty(userIconUrl)) {
            x.image().bind(icon, userIconUrl);
        } else {
            icon.setImageResource(R.mipmap.ic_launcher);
        }

        tvLogin.setVisibility(View.GONE);//隐藏登录提示
        rlLogin.setVisibility(View.VISIBLE);//显示用户信息
        btnExit.setEnabled(true);
    }


    /**
     * 控件的点击监听事件
     *
     * @param view
     */
    public void clickSettingItem(View view) {
        switch (view.getId()) {
            case R.id.tv_picture:
                if (loginState) {
                    toActivity(PictureActivity.class);
                } else {
                    OutputUtils.toastShort(mActivity, "请先登录");
                }
                break;
            case R.id.tv_collect:
                if (loginState) {
                    toActivity(CollectActivity.class);
                } else {
                    OutputUtils.toastShort(mActivity, "请先登录");
                }
                break;
            case R.id.tv_resource:
                toActivity(ResourceActivity.class);
                break;
            case R.id.tv_helper:
                break;
            case R.id.tv_feedback:
                toActivity(FeedbackActivity.class);
                break;
            case R.id.rl_update:
                OutputUtils.toastShort(mActivity, "rl_update");
                break;
            case R.id.tv_about:
                toActivity(AboutActivity.class);
                break;
            case R.id.btn_set_exit:
                rlLogin.setVisibility(View.GONE);
                tvLogin.setVisibility(View.VISIBLE);
                btnExit.setEnabled(false);
                loginState = false;
                BmobUser.logOut(mActivity);//退出登录
                break;
            case R.id.rl_set_user://进入个人信息界面
                if (loginState) {
                    toActivity(UserInfoActivity.class);
                } else {
                    mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class), 1);
                }
                break;
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == mActivity.RESULT_OK) {
            initUserInfo();
        }
    }

    /**
     * 启动Activity
     *
     * @param contextClass 要启动的Activity
     */
    private void toActivity(Class contextClass) {
        mActivity.startActivity(new Intent(mActivity, contextClass));
    }

    /**
     * switch的选择监听
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == swNews) {//新消息提醒开启
            if (isChecked) {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_NEWS, true);
            } else {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_NEWS, false);
            }
        } else if (buttonView == swAutoPlay) {//自动播放语音导览
            if (isChecked) {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_AUTOPLAY, true);
            } else {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_AUTOPLAY, false);
            }
        } else if (buttonView == swDownLoad) {//非WiFi环境下下载文件
            if (isChecked) {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_DOWNLOAD, true);
            } else {
                SpUtils.getInstance(mActivity).save(SpUtils.SETTING_DOWNLOAD, false);
            }
        }
    }
}
