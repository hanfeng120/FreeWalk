package club.hanfeng.freewalk.interfaces.studio;

import android.content.Context;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/1/27.
 */
public interface IStudioManager {

    void getAllStudios(Context context, FindListener findListener);

}
