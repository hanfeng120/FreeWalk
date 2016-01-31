package club.hanfeng.freewalk.mainpage.content;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.AboutActivity;
import club.hanfeng.freewalk.activity.FeedbackActivity;
import club.hanfeng.freewalk.activity.ResourceActivity;
import club.hanfeng.freewalk.collection.CollectionActivity;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataCenter;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import club.hanfeng.freewalk.photo.PhotoActivity;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.user.UserConstants;
import club.hanfeng.freewalk.user.UserDetailActivity;
import club.hanfeng.freewalk.utils.CommonUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.BmobUser;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class UserPage extends BaseViewGroup implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Switch swNews, swAutoPlay, swDownLoad;
    private TextView tvVerCode, tvName, tvTag, tvLogin;
    private Button btnExit;
    private ImageView icon;
    private RelativeLayout rlLogin;

    private boolean loginState;//用户登录状态
    private MyUser user;
    private int taskId;

    public UserPage(Context activity) {
        super(activity);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_userpage;
    }

    @Override
    public void onInitChildren() {
        swNews = (Switch) getRootView().findViewById(R.id.sw_news);
        swAutoPlay = (Switch) getRootView().findViewById(R.id.sw_auto_play);
        swDownLoad = (Switch) getRootView().findViewById(R.id.sw_download);
        tvVerCode = (TextView) getRootView().findViewById(R.id.tv_setting_vercode);
        tvName = (TextView) getRootView().findViewById(R.id.tv_set_username);
        tvTag = (TextView) getRootView().findViewById(R.id.tv_set_usertag);
        tvLogin = (TextView) getRootView().findViewById(R.id.tv_set_user_login);
        rlLogin = (RelativeLayout) getRootView().findViewById(R.id.rl_set_user_login);
        btnExit = (Button) getRootView().findViewById(R.id.btn_set_exit);
        icon = (ImageView) getRootView().findViewById(R.id.img_set_icon);

        swNews.setOnCheckedChangeListener(this);
        swAutoPlay.setOnCheckedChangeListener(this);
        swDownLoad.setOnCheckedChangeListener(this);

        setListener();
        registerDataCenter();
    }

    private void setListener() {
        setOnClickListener(R.id.rl_set_user);
        setOnClickListener(R.id.tv_picture);
        setOnClickListener(R.id.tv_collect);
        setOnClickListener(R.id.tv_resource);
        setOnClickListener(R.id.tv_helper);
        setOnClickListener(R.id.tv_feedback);
        setOnClickListener(R.id.rl_update);
        setOnClickListener(R.id.tv_about);
        setOnClickListener(R.id.btn_set_exit);
    }

    private void setOnClickListener(int id) {
        getRootView().findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onDataChange(int key) {
        if (key == MainPageConstants.TASK_ID_USERPAGE) {
            refreshData();
        }
    }

    @Override
    public void onRequestLoadData(int key) {
        if (key == MainPageConstants.TASK_ID_USERPAGE) {
            refreshData();
        }
    }

    private void refreshData() {
        setState();
        initUserInfo();
    }

    private void registerDataCenter() {
        DataCenter.getInstance().registerListener(taskId, this);
    }

    /**
     * step 1 : 设置当前应用状态
     */
    private void setState() {
        //1  设置新消息状态
        if (SpUtils.getInstance(getContext()).getValue(SpUtils.SETTING_NEWS, false)) {
            swNews.setChecked(true);
        }
        //2  设置自动播放语音状态
        if (SpUtils.getInstance(getContext()).getValue(SpUtils.SETTING_AUTOPLAY, false)) {
            swAutoPlay.setChecked(true);
        }
        //3  设置下载资源状态
        if (SpUtils.getInstance(getContext()).getValue(SpUtils.SETTING_DOWNLOAD, false)) {
            swDownLoad.setChecked(true);
        }

        //4  设置当前版本名
        int verCode = CommonUtils.getVerCode(getContext());
        int serverCode = SpUtils.getInstance(getContext()).getValue(SpUtils.VERSION_CODE, -1);
        if (serverCode > verCode) {
            String serverName = SpUtils.getInstance(getContext()).getValue(SpUtils.VERSION_NAME, null);
            if (serverName != null) {
                tvVerCode.setText("有新版本 " + serverName);
            }
        } else {
            tvVerCode.setText("当前已是最新版");
        }

    }

    /**
     * step 2 : 用户已经登录设置登录初始信息
     */
    private void initUserInfo() {
        user = BmobUser.getCurrentUser(getContext(), MyUser.class);
        if (user == null) {
            loginState = false;
            tvLogin.setVisibility(View.VISIBLE);
            rlLogin.setVisibility(View.GONE);
            btnExit.setEnabled(false);
            return;
        } else {
            loginState = true;
            String userName = user.getUsername();
            String nickName = user.getNickName();
            String userTag = user.getTag();
            String userIconUrl = user.getPortraitUrl();
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

    }

    public void setTaskId(int taskIdUserpage) {
        this.taskId = taskIdUserpage;
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> dataRefreshTasks = new ArrayList<>();
        dataRefreshTasks.add(new DataRefreshTask(taskId, 0));
        return dataRefreshTasks;
    }

    private void startActivity(Class contextClass) {
        getContext().startActivity(new Intent(getContext(), contextClass));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == swNews) {
            if (isChecked) {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_NEWS, true);
            } else {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_NEWS, false);
            }
        } else if (buttonView == swAutoPlay) {
            if (isChecked) {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_AUTOPLAY, true);
            } else {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_AUTOPLAY, false);
            }
        } else if (buttonView == swDownLoad) {
            if (isChecked) {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_DOWNLOAD, true);
            } else {
                SpUtils.getInstance(getContext()).save(SpUtils.SETTING_DOWNLOAD, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_picture:
                if (loginState) {
                    startActivity(PhotoActivity.class);
                } else {
                    OutputUtils.toastShort(getContext(), "请先登录");
                }
                break;
            case R.id.tv_collect:
                if (loginState) {
                    startActivity(CollectionActivity.class);
                } else {
                    OutputUtils.toastShort(getContext(), "请先登录");
                }
                break;
            case R.id.tv_resource:
                startActivity(ResourceActivity.class);
                break;
            case R.id.tv_helper:
                break;
            case R.id.tv_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.rl_update:
                OutputUtils.toastShort(getContext(), "rl_update");
                break;
            case R.id.tv_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.btn_set_exit:
                BmobUser.logOut(getContext());
                refreshData();
                break;
            case R.id.rl_set_user:
                if (loginState) {
                    startActivity(UserDetailActivity.class);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra(UserConstants.FROM_USERPAGE, true);
                    ((Activity) getContext()).startActivityForResult(intent, MainPageConstants.REQUEST_USER_PAGE);
                }
                break;
        }
    }
}
