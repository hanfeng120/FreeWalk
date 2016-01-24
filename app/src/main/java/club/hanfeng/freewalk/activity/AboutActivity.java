package club.hanfeng.freewalk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.guide.GuideActivity;
import club.hanfeng.freewalk.utils.CommonUtils;

public class AboutActivity extends AppCompatActivity {

    private TextView tvVerName,tvRights;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initActionBar();
        initView();
        initData();

    }

    private void initData() {
        String verName = getResources().getString(R.string.app_name) + "  " + CommonUtils.getVerName(this);//获取当前应用名称和版本名
        tvVerName.setText(verName);
    }

    private void initView() {
        tvVerName = (TextView) findViewById(R.id.tv_about_name);
        tvRights = (TextView) findViewById(R.id.tv_about_rights);
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("关于");
    }

    public void clickAboutItem(View view) {
        switch (view.getId()) {
            case R.id.tv_about_grade://去评分
                break;
            case R.id.tv_about_guide:
                Intent intent = new Intent(this, GuideActivity.class);
                intent.putExtra("once", true);
                startActivity(intent);
                break;
            case R.id.tv_about_introduce:
                break;
            case R.id.tv_about_rights:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
