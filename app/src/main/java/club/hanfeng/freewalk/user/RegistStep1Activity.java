package club.hanfeng.freewalk.user;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class RegistStep1Activity extends BaseActivity {

    private EditText phoneNum;

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_regist_step1;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initTopBar() {
        setTitle("注册");
    }

    @Override
    protected void initContent() {
        phoneNum = (EditText) findViewById(R.id.et_phone_num);

        findViewById(R.id.btn_regist).setOnClickListener(getOnClickListener());
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
        if (checkNum(phoneNum.getText().toString())) {
            requestSmsCode();
        } else {
            OutputUtils.toastShort(this, "输入有误哦!");
        }
    }

    private boolean checkNum(String num) {
        return num.matches("^1[345678]\\d{9}$");
    }

    private void requestSmsCode() {
        BmobSMS.requestSMSCode(this, phoneNum.getText().toString(), "register", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    Intent intent = new Intent(RegistStep1Activity.this, RegistStep2Activity.class);
                    intent.putExtra(UserConstants.PHONE_NUM, phoneNum.getText().toString());
                    startActivity(intent);
                } else {
                    OutputUtils.toastShort(RegistStep1Activity.this, "网络有点问题哦，再试一次看看");
                }
            }
        });
    }
}
