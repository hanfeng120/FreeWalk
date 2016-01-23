package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.MainPagerAdapter;
import club.hanfeng.freewalk.base.BasePage;
import club.hanfeng.freewalk.base.FindPage;
import club.hanfeng.freewalk.base.HomePage;
import club.hanfeng.freewalk.base.ServerPage;
import club.hanfeng.freewalk.base.UserPage;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.implement.main.OnBottomBarSelectedListener;
import club.hanfeng.freewalk.implement.main.OnPageChangeListener;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class MainPageContent extends BaseViewGroup {

    private ArrayList<OnPageChangeListener> onPageChangeListeners = new ArrayList<>();
    private List<BasePage> pageContains = new ArrayList<>();
    private BasePage homePage, serverPage, findPage, userPage;
    private ViewPager viewPager;

    public MainPageContent(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.main_page_content;
    }

    @Override
    public void onInitChildren() {
        homePage = new HomePage(getContext());
        serverPage = new ServerPage(getContext());
        findPage = new FindPage(getContext());
        userPage = new UserPage(getContext());

        pageContains.add(homePage);
        pageContains.add(serverPage);
        pageContains.add(findPage);
        pageContains.add(userPage);

        viewPager = (ViewPager) getRootView().findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getContext(), pageContains));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageContains.get(position).initData();
                for (OnPageChangeListener onPageChangeListener : onPageChangeListeners) {
                    onPageChangeListener.onPageChanged(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        onPageChangeListeners.add(onPageChangeListener);
    }

    public OnBottomBarSelectedListener getOnBottomBarSelectedListener() {
        return onBottomBarSelectedListener;
    }

    private OnBottomBarSelectedListener onBottomBarSelectedListener = new OnBottomBarSelectedListener() {
        @Override
        public void onSelected(int index) {
            viewPager.setCurrentItem(index);
        }
    };

}
