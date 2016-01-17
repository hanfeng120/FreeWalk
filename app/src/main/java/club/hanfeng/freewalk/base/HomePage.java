package club.hanfeng.freewalk.base;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class HomePage extends BasePage {

    public HomePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView tv = new TextView(mActivity);
        tv.setText("我是首页HomePage");
        flBasePage.addView(tv);
        Log.e("TAG","initHomePage");
    }

}
