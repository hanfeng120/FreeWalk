package club.hanfeng.freewalk.download;

import android.content.Context;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class VoiceListPage extends BaseViewGroup {

    public VoiceListPage(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.voice_list_page;
    }
}
