package club.hanfeng.freewalk.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.bean.MyUser;
import club.hanfeng.freewalk.constants.Constants;
import club.hanfeng.freewalk.utils.FreeWalkUtils;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone, etPwd;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this, Constants.BMOB_ID);

        etPhone = (EditText) findViewById(R.id.et_login_phone);
        etPwd = (EditText) findViewById(R.id.et_login_pwd);

        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中...");

    }

    public void login(View view) {
        String username = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            OutputUtils.toastShort(this, "还有没填写的哦");
        } else {
            dialog.show();
            pwd = FreeWalkUtils.md5(pwd);
            getDataFromServer(username, pwd);
        }
    }

    /**
     * 查询数据库验证用户信息是否正确
     */
    private void getDataFromServer(String username, String pwd) {

        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(pwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                setResult(RESULT_OK);
                OutputUtils.toastShort(LoginActivity.this, "登录成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                dialog.dismiss();
                setResult(RESULT_CANCELED);
                OutputUtils.toastShort(LoginActivity.this, "用户名或密码错误");
                etPwd.setText(null);
            }
        });

    }

    /**
     * 打开注册界面
     *
     * @param view
     */
    public void register(View view) {
        startActivity(new Intent(this, RegistStep1Activity.class));
    }

}
