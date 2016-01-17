package club.hanfeng.freewalk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.bean.MyUser;
import club.hanfeng.freewalk.utils.Constants;
import club.hanfeng.freewalk.utils.FreeWalkUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.SpUtils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class RegisterActivity extends AppCompatActivity {

    private static final int WHAT_UPDATE_SMS = 1;
    private EditText etName, etPhone, etPwd, etCpwd, etSmsCode;
    private Button btnSms, btnRegister;
    private int smsTime = 60;
    private ProgressDialog dialog;
    private int smsId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_UPDATE_SMS:
                    smsTime = 59;
                    btnSms.setText(smsTime + "s");
                    if (smsTime == 0) {
                        handler.removeMessages(WHAT_UPDATE_SMS);
                        btnSms.setText("重新获取验证码");
                        btnSms.setClickable(true);
                    } else {
                        handler.sendEmptyMessageDelayed(WHAT_UPDATE_SMS, 1000);

                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bmob.initialize(this, Constants.BMOB_ID);

        etName = (EditText) findViewById(R.id.et_register_nicheng);
        etPhone = (EditText) findViewById(R.id.et_register_phone);
        etPwd = (EditText) findViewById(R.id.et_register_pwd);
        etCpwd = (EditText) findViewById(R.id.et_register_cpwd);
        etSmsCode = (EditText) findViewById(R.id.et_register_smscode);
        btnSms = (Button) findViewById(R.id.btn_register_sms);
        btnRegister = (Button) findViewById(R.id.btn_register_regist);


    }

    /**
     * 获得手机验证码
     *
     * @param view
     */
    public void getSmsCode(View view) {
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            OutputUtils.toastShort(this, "请输入手机号");
        } else {
            btnSms.setClickable(false);
            btnRegister.setClickable(true);
            handler.sendEmptyMessage(WHAT_UPDATE_SMS);
            BmobSMS.requestSMSCode(this, etPhone.getText().toString(), "register", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {

                }
            });
        }
    }

    /**
     * 注册Button点击事件
     *
     * @param view
     */
    public void register(View view) {
        if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etSmsCode.getText().toString()) || TextUtils.isEmpty(etPhone.getText().toString()) || TextUtils.isEmpty(etPwd.getText().toString()) || TextUtils.isEmpty(etCpwd.getText().toString())) {
            OutputUtils.toastShort(this, "还有没填写的哦");
        } else if (!etPwd.getText().toString().equals(etCpwd.getText().toString())) {
            OutputUtils.toastShort(this, "两次密码不一致");
            etPwd.setText(null);
            etCpwd.setText(null);
        } else {
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在注册...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            getDataFromServer();
        }
    }

    /**
     * 验证验证码是否正确
     */
    private void getDataFromServer() {

        String phoneNum = etPhone.getText().toString();
        String smsCode = etSmsCode.getText().toString();

        BmobSMS.verifySmsCode(this, phoneNum, smsCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {//验证码正确
                    register();
                } else {
                    dialog.dismiss();
                    OutputUtils.toastShort(RegisterActivity.this,"验证码错误");
                    handler.removeMessages(WHAT_UPDATE_SMS);
                    btnSms.setText("重新获取验证码");
                    btnSms.setClickable(true);
                }
            }
        });


    }

    /**
     * 在Bmob数据库中注册用户信息
     */
    private void register() {
        String userName = etName.getText().toString().toLowerCase();
        String phoneNum = etPhone.getText().toString();
        String pwd = FreeWalkUtils.md5(etPwd.getText().toString());

        MyUser user = new MyUser();
        user.setUsername(userName);
        user.setPassword(pwd);
        user.setMobilePhoneNumber(phoneNum);
        user.setMobilePhoneNumberVerified(true);

        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //注册成功
                dialog.dismiss();
                OutputUtils.toastShort(RegisterActivity.this,"注册成功");
                toMainUI();
            }

            @Override
            public void onFailure(int i, String s) {
                //注册失败
                dialog.dismiss();
                OutputUtils.toastShort(RegisterActivity.this,"注册失败，请重试");
            }
        });


    }

    /**
     * 打开登录界面
     * @param view
     */
    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * 进入主界面
     */
    private void toMainUI() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
