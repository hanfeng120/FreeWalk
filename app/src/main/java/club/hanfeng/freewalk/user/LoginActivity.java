package club.hanfeng.freewalk.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.MainPageActivity;
import club.hanfeng.freewalk.utils.FreeWalkProgress;
import club.hanfeng.freewalk.utils.FreeWalkUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    private EditText etPhone, etPwd;
    private TextView tvRegister, tvForget;

    @Override
    protected void initIntentData() {
        if (getIntent().getBooleanExtra(UserConstants.FROM_USERPAGE, false)) {
            initBackBar();
        }
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
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
        etPhone = (EditText) findViewById(R.id.et_phone_num);
        etPwd = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.tv_register).setOnClickListener(getOnClickListener());
        findViewById(R.id.tv_forget).setOnClickListener(getOnClickListener());
        findViewById(R.id.btn_login).setOnClickListener(getOnClickListener());
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

    private void checkUserInfo(String username, String pwd) {
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(pwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                FreeWalkProgress.dismiss(getContext());
                setResult(RESULT_OK);
                startActivity(new Intent(getContext(), MainPageActivity.class));
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                FreeWalkProgress.dismiss(getContext());
                OutputUtils.toastShort(LoginActivity.this, "用户名或密码错误");
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                    OutputUtils.toastShort(this, "还有没填写的哦");
                } else {
                    FreeWalkProgress.show(getContext(), "正在登陆...");
                    pwd = FreeWalkUtils.md5(pwd);
                    checkUserInfo(username, pwd);
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(getContext(), RegistStep1Activity.class));
                break;
            case R.id.tv_forget:
                break;
        }
    }
}
