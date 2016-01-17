package club.hanfeng.freewalk.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class NoCacheViewPage extends ViewPager {
    public NoCacheViewPage(Context context) {
        super(context);
    }

    public NoCacheViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
}
