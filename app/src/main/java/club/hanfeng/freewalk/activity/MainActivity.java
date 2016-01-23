package club.hanfeng.freewalk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.MainPagerAdapter;
import club.hanfeng.freewalk.base.BasePage;
import club.hanfeng.freewalk.base.FindPage;
import club.hanfeng.freewalk.base.HomePage;
import club.hanfeng.freewalk.base.ServerPage;
import club.hanfeng.freewalk.base.UserPage;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.SpUtils;

/**
 * Hello World
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private static final int REQUEST_USERPAGE = 1;
    private static final int REQUEST_FINDPAGE = 2;
    private static final int WHAT_UPDATE = 1;//应用更新
    private static final int WHAT_EXIT = 2;
    private RadioGroup radioGroup;
    private RadioButton rbHome, rbServer, rbFind, rbUser;
    private ViewPager viewPager;
    private List<BasePage> list;
    private List<RadioButton> listRB;
    private UserPage userPage;
    private FindPage findPage;
    private boolean isExit = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_UPDATE:

                    break;
                case WHAT_EXIT:
                    isExit = false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpUtils.getInstance(this).save(SpUtils.SCENIC_ID, "32030001");

        initView();
        initData();
        update();
    }

    /**
     * 检查服务器应用版本，保存到SP中
     */
    private void update() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                SpUtils.getInstance(MainActivity.this).save(SpUtils.VERSION_CODE, 2);
                SpUtils.getInstance(MainActivity.this).save(SpUtils.VERSION_NAME, "Ver 2.1");
                handler.sendEmptyMessage(WHAT_UPDATE);
            }
        }.start();
    }

    /**
     *
     */
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main);
        rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbServer = (RadioButton) findViewById(R.id.rb_server);
        rbFind = (RadioButton) findViewById(R.id.rb_find);
        rbUser = (RadioButton) findViewById(R.id.rb_user);
    }

    public void initData() {
        list = new ArrayList<>();
        list.add(new HomePage(this));
        list.add(new ServerPage(this));
        findPage = new FindPage(this);
        list.add(findPage);
        userPage = new UserPage(this);
        list.add(userPage);
        listRB = new ArrayList<>();
        listRB.add(rbHome);
        listRB.add(rbServer);
        listRB.add(rbFind);
        listRB.add(rbUser);

        viewPager.setAdapter(new MainPagerAdapter(this, list));
        viewPager.setOnPageChangeListener(this);
        list.get(0).initData();
        rbHome.setChecked(true);

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USERPAGE && resultCode == RESULT_OK) {
            userPage.onResult(requestCode, resultCode, data);
        } else if (requestCode == REQUEST_FINDPAGE && resultCode == RESULT_OK) {
            findPage.onResult(requestCode, resultCode, data);
        }
    }

    /**
     * 点击事件处理
     *
     * @param view
     */
    public void clickSettingItem(View view) {
        userPage.clickSettingItem(view);
    }

    /**
     * findPage页面点击事件的处理
     *
     * @param view
     */
    public void clickFindItem(View view) {
        findPage.clickFindItem(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    boolean hasLoad0 = false;
    boolean hasLoad1 = false;
    boolean hasLoad2 = false;
    boolean hasLoad3 = false;

    @Override
    public void onPageSelected(int position) {
        Log.e("TAG", "onPageSelected()" + position);
        switch (position) {
            case 0:
                if (!hasLoad0) {
                    hasLoad0 = true;
                    list.get(position).initData();
                    Log.e("TAG", "onPageSelected()0000");
                }
                listRB.get(position).setChecked(true);
                break;
            case 1:
                if (!hasLoad1) {
                    hasLoad1 = true;
                    list.get(position).initData();
                    Log.e("TAG", "onPageSelected()1111");
                }
                listRB.get(position).setChecked(true);
                break;
            case 2:
                if (!hasLoad2) {
                    hasLoad2 = true;
                    list.get(position).initData();
                    Log.e("TAG", "onPageSelected()2222");
                }
                listRB.get(position).setChecked(true);
                break;
            case 3:
                if (!hasLoad3) {
                    hasLoad3 = true;
                    list.get(position).initData();
                    Log.e("TAG", "onPageSelected()3333");
                }
                listRB.get(position).setChecked(true);
                break;
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_server:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_find:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_user:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isExit) {
                    finish();
                } else {
                    OutputUtils.toastShort(this, "再按一次退出应用");
                    isExit = true;
                    handler.sendEmptyMessageDelayed(WHAT_EXIT, 2000);
                }
                break;
        }
        return true;
    }
}
