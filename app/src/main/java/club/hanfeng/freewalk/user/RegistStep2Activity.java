package club.hanfeng.freewalk.user;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.MainActivity;
import club.hanfeng.freewalk.bean.MyUser;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.utils.FreeWalkProgress;
import club.hanfeng.freewalk.utils.FreeWalkUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by HanFeng on 2016/1/17.
 */
public class RegistStep2Activity extends BaseActivity {

    private static final int WHAT_UPDATE_SMS = 1;
    private EditText etNickName, etPwd, etSmsCode;
    private TextView tvSmsCode, tvProtocol;
    private int smsTime = 60;
    private String phoneNum;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_UPDATE_SMS:
                    smsTime--;
                    tvSmsCode.setText(smsTime + "s");
                    if (smsTime == 0) {
                        smsTime = 60;
                        handler.removeMessages(WHAT_UPDATE_SMS);
                        tvSmsCode.setText("重新获取验证码");
                        tvSmsCode.setClickable(true);
                    } else {
                        handler.sendEmptyMessageDelayed(WHAT_UPDATE_SMS, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_regist_step2;
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
    protected void initIntentData() {
        phoneNum = getIntent().getStringExtra(UserConstants.PHONE_NUM);
    }

    @Override
    protected void initContent() {
        etNickName = (EditText) findViewById(R.id.et_nickname);
        etPwd = (EditText) findViewById(R.id.et_password);
        etSmsCode = (EditText) findViewById(R.id.et_smscode);
        tvSmsCode = (TextView) findViewById(R.id.tv_smscode);
        tvSmsCode.setOnClickListener(getOnClickListener());
        tvProtocol = (TextView) findViewById(R.id.tv_protocol);
        tvProtocol.setOnClickListener(getOnClickListener());
        findViewById(R.id.btn_regist).setOnClickListener(getOnClickListener());

    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        handler.sendEmptyMessage(WHAT_UPDATE_SMS);
    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:
                if (TextUtils.isEmpty(etNickName.getText().toString()) || TextUtils.isEmpty(etSmsCode.getText().toString()) || TextUtils.isEmpty(etPwd.getText().toString())) {
                    OutputUtils.toastShort(this, "还有没填写的哦");
                } else {
                    FreeWalkProgress.show(getContext(), "正在注册");
                    checkSmsCode();
                }
                break;
            case R.id.tv_smscode:
                handler.sendEmptyMessage(WHAT_UPDATE_SMS);
                requestSmsCode();
                break;
        }
    }

    private void requestSmsCode() {
        BmobSMS.requestSMSCode(this, phoneNum, "register", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                    }
                }
        );
    }

    /**
     * 验证验证码是否正确
     */

    private void checkSmsCode() {
        String smsCode = etSmsCode.getText().toString();

        BmobSMS.verifySmsCode(this, phoneNum, smsCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    checkUserInfo();
                } else {
                    FreeWalkProgress.dismiss(getContext());
                    OutputUtils.toastShort(RegistStep2Activity.this, "验证码错误");
                    handler.removeMessages(WHAT_UPDATE_SMS);
                    tvSmsCode.setText("重新获取验证码");
                    tvSmsCode.setClickable(true);
                }
            }
        });
    }

    private void checkUserInfo() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", phoneNum);
        query.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                if (list.size() > 0) {
                    OutputUtils.toastShort(getContext(), "该用户已经注册");
                } else {
                    regist();
                }
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    private void regist() {
        MyUser user = new MyUser();
        user.setUsername(phoneNum);
        user.setPassword(FreeWalkUtils.md5(etPwd.getText().toString()));
        user.setNickName(etNickName.getText().toString());
        user.setMobilePhoneNumber(phoneNum);
        user.setMobilePhoneNumberVerified(true);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                FreeWalkProgress.dismiss(RegistStep2Activity.this);
                startActivity(new Intent(getContext(), MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                FreeWalkProgress.dismiss(getContext());
                OutputUtils.toastShort(RegistStep2Activity.this, "注册失败，请重试");
            }
        });
    }
}
