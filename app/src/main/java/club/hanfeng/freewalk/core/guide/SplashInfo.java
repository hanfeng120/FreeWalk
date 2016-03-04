package club.hanfeng.freewalk.core.guide;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhaoxunyi on 2016/3/4.
 */
public class SplashInfo extends BmobObject {

    private boolean isOpen;

    private String message;

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
