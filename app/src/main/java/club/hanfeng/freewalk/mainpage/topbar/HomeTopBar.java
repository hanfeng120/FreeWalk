package club.hanfeng.freewalk.mainpage.topbar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.HomeTopBarAdapter;
import club.hanfeng.freewalk.core.tabbar.data.TabBarType;
import club.hanfeng.freewalk.core.homepage.HomePageManager;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnHomeTopBarSelectedListener;
import club.hanfeng.freewalk.scene.SceneListActivity;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class HomeTopBar extends BaseViewGroup implements View.OnClickListener {

    private ArrayList<OnHomeTopBarSelectedListener> onHomeTopBarSelectedListeners = new ArrayList<>();
    private ListPopupWindow listPopupWindow;
    private RelativeLayout titleContent;

    private TextView tvTitle;

    public HomeTopBar(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.home_topbar;
    }

    @Override
    public void onInitChildren() {
        TextView tvScene = (TextView) getRootView().findViewById(R.id.topbar_left);
        tvTitle = (TextView) getRootView().findViewById(R.id.title);
        ImageView ivMoer = (ImageView) getRootView().findViewById(R.id.topbar_right);
        titleContent = (RelativeLayout) getRootView().findViewById(R.id.title_content);

        tvScene.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivMoer.setOnClickListener(this);

        getTabBars();
    }

    public void setOnCheckedIndex(int index) {
        if (index == 0) {
            tvTitle.setEnabled(true);
        } else {
            tvTitle.setEnabled(false);
        }
    }

    private void getTabBars() {
        HomePageManager.getInstance().getHomeTabBars(getContext(), new FindListener<TabBarType>() {
            @Override
            public void onSuccess(List<TabBarType> list) {
                createListPopupWindow(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void createListPopupWindow(List<TabBarType> list) {
        final HomeTopBarAdapter adapter = new HomeTopBarAdapter(getContext(), list);
        listPopupWindow = new ListPopupWindow(getContext());
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        listPopupWindow.setAnchorView(titleContent);
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onHomeTopBarSelected(adapter.getItem(position).getId());
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();
                listPopupWindow.dismiss();
            }
        });
    }

    private void onHomeTopBarSelected(String type) {
        for (OnHomeTopBarSelectedListener listener : onHomeTopBarSelectedListeners) {
            listener.onTopBarSelected(type);
        }
    }

    public void setOnHomeTopBarSelectedListener(OnHomeTopBarSelectedListener onHomeTopBarSelectedListener) {
        onHomeTopBarSelectedListeners.add(onHomeTopBarSelectedListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_left:
                getContext().startActivity(new Intent(getContext(), SceneListActivity.class));
                break;
            case R.id.title:
                if (listPopupWindow == null) {
                    getTabBars();
                    return;
                }
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                } else {
                    listPopupWindow.show();
                }
                break;
            case R.id.topbar_right:
                break;
        }
    }

}
