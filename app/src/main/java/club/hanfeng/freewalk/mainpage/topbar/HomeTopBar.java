package club.hanfeng.freewalk.mainpage.topbar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.HomeTopBarAdapter;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnHomeTopBarSelectedListener;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class HomeTopBar extends BaseViewGroup implements View.OnClickListener {

    private ArrayList<OnHomeTopBarSelectedListener> onHomeTopBarSelectedListeners = new ArrayList<>();
    private ArrayList<String> items = new ArrayList<>();
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

        createListPopupWindow();
    }

    public void setOnCheckedIndex(int index) {
        if (index == 0) {
            tvTitle.setEnabled(true);
        } else {
            tvTitle.setEnabled(false);
        }
    }

    private void createListPopupWindow() {
        items.add("热门景点");
        items.add("游客服务中心");
        items.add("卫生间");
        listPopupWindow = new ListPopupWindow(getContext());
        listPopupWindow.setAdapter(new HomeTopBarAdapter(getContext(), items));
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        listPopupWindow.setAnchorView(titleContent);
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view.findViewWithTag(position)).setTextColor(Color.BLUE);
                listPopupWindow.dismiss();
                onHomeTopBarSelected(position);
            }
        });
    }

    private void onHomeTopBarSelected(int type) {
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
                break;
            case R.id.title:
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
