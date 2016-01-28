package club.hanfeng.freewalk.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class UserInfoActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvSex, tvTag;
    private ImageView imgIcon, imgTdCode;
    private MyUser user;
    private String nickName, iconUrl, codeUrl, sex, tag, userName;
    private boolean phoneVerified;
    private AlertDialog editDialog;
    private AlertDialog sexDialog, phoneDialog;
    private EditText editText;
    private ProgressDialog pbDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tvName = (TextView) findViewById(R.id.tv_ui_name);
        tvPhone = (TextView) findViewById(R.id.tv_ui_phone);
        tvSex = (TextView) findViewById(R.id.tv_ui_sex);
        tvTag = (TextView) findViewById(R.id.tv_ui_tag);
        imgIcon = (ImageView) findViewById(R.id.img_ui_icon);
        imgTdCode = (ImageView) findViewById(R.id.img_ui_tdcode);

        pbDialog = new ProgressDialog(this);
        pbDialog.setMessage("更新中...");

        initData();//初始化数据

    }

    /**
     *
     */
    private void initData() {

        user = BmobUser.getCurrentUser(this, MyUser.class);
        if (user == null) {
            OutputUtils.toastShort(this, "登录信息错误，请重新登录");
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        userName = user.getUsername();
        nickName = user.getNickName();
        iconUrl = user.getPortraitUrl();
        codeUrl = user.getCodeUrl();
        phoneVerified = user.getMobilePhoneNumberVerified();
        sex = user.getSex();
        tag = user.getTag();

        if (TextUtils.isEmpty(iconUrl)) {
            imgIcon.setImageResource(R.mipmap.ic_launcher);
        } else {
            x.image().bind(imgIcon, iconUrl);
        }
        if (TextUtils.isEmpty(codeUrl)) {
            imgTdCode.setImageResource(R.mipmap.ic_launcher);
        } else {
            x.image().bind(imgTdCode, codeUrl);
        }
        if (TextUtils.isEmpty(nickName)) {
            tvName.setText(userName);
        } else {
            tvName.setText(nickName);
        }
        if (TextUtils.isEmpty(sex)) {
            tvSex.setText("未设置");
        } else {
            tvSex.setText(sex);
        }
        if (TextUtils.isEmpty(tag)) {
            tvTag.setText(null);
        } else {
            tvTag.setText(tag);
        }
        if (phoneVerified) {
            tvPhone.setText("已绑定");
        } else {
            tvPhone.setText("点此绑定手机号");
        }

    }

    /**
     * 更换绑定手机号
     */
    private void updatePhoneNumDialog() {
        View view = View.inflate(this, R.layout.dialog_update_phone, null);
        final Button btnSms = (Button) view.findViewById(R.id.btn_dialog_upn_sms);
        final EditText etPhone = (EditText) view.findViewById(R.id.et_dialog_upn_phone);
        final EditText etSmsCode = (EditText) view.findViewById(R.id.et_dialog_upn_smscode);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    OutputUtils.toastShort(UserInfoActivity.this, "填完在提交啦");
                } else {
                    btnSms.setClickable(false);
                    //发送手机验证码
                    BmobSMS.requestSMSCode(UserInfoActivity.this, phoneNum, "register", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {

                        }
                    });
                }
            }
        });

        phoneDialog = new AlertDialog.Builder(this)
                .setTitle("绑定新的手机号")
                .setView(view)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pbDialog.show();
                        getDataFromServer(etPhone.getText().toString(), etSmsCode.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    /**
     * 更改个人信息对话框
     *
     * @param listener
     * @param title
     * @param text
     */
    private void initEditDialog(AlertDialog.OnClickListener listener, String title, String text) {
        LinearLayout linearLayout = new LinearLayout(this);
        editText = new EditText(this);
        editText.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = 15;
        params.rightMargin = 15;
        editText.setLayoutParams(params);
        linearLayout.setPadding(40, 0, 40, 0);
        linearLayout.addView(editText, params);
        editDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(linearLayout)
                .setPositiveButton("更新", listener)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 验证验证码是否正确
     */
    private void getDataFromServer(final String phoneNum, String smsCode) {

        BmobSMS.verifySmsCode(this, phoneNum, smsCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {//验证码正确
                    MyUser myUser = new MyUser();
                    myUser.setMobilePhoneNumber(phoneNum);
                    myUser.update(UserInfoActivity.this, user.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            pbDialog.dismiss();
                            OutputUtils.toastShort(UserInfoActivity.this, "更新成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            pbDialog.dismiss();
                            OutputUtils.toastShort(UserInfoActivity.this, "不好意思失败了，再来一次吧");
                        }
                    });
                } else {
                    pbDialog.dismiss();
                    OutputUtils.toastShort(UserInfoActivity.this, "验证码错误,再试一次啦");
                }
            }
        });


    }

    /**
     * 更改性别对话框
     */
    private void updateSexDialog() {
        int index = -1;
        if (!TextUtils.isEmpty(sex)) {
            index = "男".equals(sex) ? 0 : 1;
        }
        sexDialog = new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(new String[]{"男", "女"}, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyUser myUser = new MyUser();
                        final String sex = which == 0 ? "男" : "女";
                        myUser.setSex(sex);
                        myUser.update(UserInfoActivity.this, user.getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                tvSex.setText(sex);
                                OutputUtils.toastShort(UserInfoActivity.this, "更新成功");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                OutputUtils.toastShort(UserInfoActivity.this, "不好意思失败了，再来一次吧");
                            }
                        });
                        sexDialog.dismiss();
                    }
                })
                .show();
    }

    private AlertDialog.OnClickListener editNickNameListener;
    private AlertDialog.OnClickListener editTagListener;
    private AlertDialog.OnClickListener editPhoneNumListener;


    public void clickItem(View view) {
        switch (view.getId()) {
            case R.id.rl_ui_name:
                editNickNameListener = new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String content = editText.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            OutputUtils.toastShort(UserInfoActivity.this, "填完再提交啦...");
                        } else {
                            MyUser myUser = new MyUser();
                            myUser.setUsername(content);
                            myUser.update(UserInfoActivity.this, user.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    tvName.setText(content);
                                    OutputUtils.toastShort(UserInfoActivity.this, "更新成功");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    OutputUtils.toastShort(UserInfoActivity.this, "不好意思失败了，再来一次吧");
                                }
                            });
                        }
                    }
                };
                initEditDialog(editNickNameListener, "更改名字", user.getUsername());
                break;
            case R.id.rl_ui_phone:
                updatePhoneNumDialog();
                break;
            case R.id.rl_ui_sex:
                updateSexDialog();
                break;
            case R.id.rl_ui_tag:
                editTagListener = new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String content = editText.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            OutputUtils.toastShort(UserInfoActivity.this, "填完再提交啦...");
                        } else {
                            MyUser myUser = new MyUser();
                            myUser.setTag(content);
                            myUser.update(UserInfoActivity.this, user.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    tvTag.setText(content);
                                    OutputUtils.toastShort(UserInfoActivity.this, "更新成功");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    OutputUtils.toastShort(UserInfoActivity.this, "不好意思失败了，再来一次吧");
                                }
                            });
                        }
                    }
                };
                initEditDialog(editTagListener, "个性签名", user.getTag());
                break;
            case R.id.img_ui_icon:
                break;
            case R.id.img_ui_tdcode:
                break;
        }
    }

}
