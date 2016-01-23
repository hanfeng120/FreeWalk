package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.DirectSendActivity;
import club.hanfeng.freewalk.activity.HotMapActivity;
import club.hanfeng.freewalk.activity.TravelsActivity;
import club.hanfeng.freewalk.framework.BaseViewGroup;

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
        setOnClickListener(R.id.tv_find_map);
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
    };

    private void startActivity(Class activity) {
        getContext().startActivity(new Intent(getContext(), activity));
    }

}
