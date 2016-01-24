package club.hanfeng.freewalk.mainpage;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnBottomBarSelectedListener;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class MainPageBottomBar extends BaseViewGroup implements RadioGroup.OnCheckedChangeListener {

    private ArrayList<OnBottomBarSelectedListener> bottomBarSelectedListeners = new ArrayList<>();
    private RadioGroup radioGroup;

    public MainPageBottomBar(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.main_page_bottom_bar;
    }

    @Override
    public void onInitChildren() {
        radioGroup = (RadioGroup) getRootView().findViewById(R.id.rg_main);
        radioGroup.setOnCheckedChangeListener(this);
        onChange(0);
    }

    public void setOnBottomBarSelectedListeners(OnBottomBarSelectedListener onBottomBarSelectedListener) {
        bottomBarSelectedListeners.add(onBottomBarSelectedListener);
    }

    private void onBottomBarSelected(int index) {
        for (OnBottomBarSelectedListener onBottomBarSelectedListener : bottomBarSelectedListeners) {
            onBottomBarSelectedListener.onBottomBarSelected(index);
        }
    }

    private void onChange(int index) {
        ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                onBottomBarSelected(0);
                break;
            case R.id.rb_server:
                onBottomBarSelected(1);
                break;
            case R.id.rb_find:
                onBottomBarSelected(2);
                break;
            case R.id.rb_user:
                onBottomBarSelected(3);
                break;
        }
    }
}
