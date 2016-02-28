package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.scan.ScanActivity;
import club.hanfeng.freewalk.studio.StudioActivity;
import club.hanfeng.freewalk.utils.ShareUtils;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class FindPage extends BaseViewGroup {


    public FindPage(Context activity) {
        super(activity);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_findpage;
    }

    @Override
    public void onInitChildren() {
        setOnClickListener(R.id.tv_find_send);
        setOnClickListener(R.id.tv_find_travels);
        setOnClickListener(R.id.tv_find_scan);
        setOnClickListener(R.id.tv_find_shake);
        setOnClickListener(R.id.tv_find_share);
    }

    private void setOnClickListener(int id) {
        getRootView().findViewById(id).setOnClickListener(getOnClickListener());
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_find_send:
                    startActivity(StudioActivity.class);
                    break;
                case R.id.tv_find_travels:
                    break;
                case R.id.tv_find_scan:
                    getContext().startActivity(new Intent(getContext(), ScanActivity.class));
                    break;
                case R.id.tv_find_shake:
                    break;
                case R.id.tv_find_share:
                    ShareUtils.shareInfo(getContext());
                    break;
            }
        }
    };

    private void startActivity(Class activity) {
        getContext().startActivity(new Intent(getContext(), activity));
    }

}
