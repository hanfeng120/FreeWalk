package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class HomePage extends BaseViewGroup {

    public HomePage(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_homepage;
    }

    @Override
    public void onInitChildren() {
    }

}
