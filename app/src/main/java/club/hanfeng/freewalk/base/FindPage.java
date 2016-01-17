package club.hanfeng.freewalk.base;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.DirectSendActivity;
import club.hanfeng.freewalk.activity.HotMapActivity;
import club.hanfeng.freewalk.activity.TravelsActivity;
import club.hanfeng.freewalk.utils.OutputUtils;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class FindPage extends BasePage {


    public FindPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_findpage, null);
        flBasePage.addView(view);
    }

    public void clickFindItem(View view) {
        switch (view.getId()) {
            case R.id.tv_find_send:
                startActivity(DirectSendActivity.class);
                break;
            case R.id.tv_find_travels:
                startActivity(TravelsActivity.class);
                break;
            case R.id.tv_find_map:
                startActivity(HotMapActivity.class);
                break;
            case R.id.tv_find_scan:
                break;
            case R.id.tv_find_shake:
                break;
            case R.id.tv_find_share:

                break;
        }
    }

    private void startActivity(Class activity) {
        mActivity.startActivity(new Intent(mActivity, activity));
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == mActivity.RESULT_OK) {//扫描二维码
            OutputUtils.toastShort(mActivity, data.getStringExtra("result"));
        }
    }



}
