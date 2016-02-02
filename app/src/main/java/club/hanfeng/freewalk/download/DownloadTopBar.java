package club.hanfeng.freewalk.download;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class DownloadTopBar extends BaseViewGroup {

    private ViewPager viewPager;
    private RadioButton rbManager, rbList;

    public DownloadTopBar(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.download_top_bar;
    }

    @Override
    public void onInitChildren() {
        getRootView().findViewById(R.id.menu_back).setOnClickListener(getOnClickListener());
        rbManager = (RadioButton) getRootView().findViewById(R.id.download_manager);
        rbList = (RadioButton) getRootView().findViewById(R.id.download_list);
        ((RadioGroup) getRootView().findViewById(R.id.radiogroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.download_manager:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.download_list:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    public void initWithTitle(String[] title, ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbManager.setChecked(true);
                } else {
                    rbList.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
