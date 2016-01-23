package club.hanfeng.freewalk.framework;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.implement.view.IDataRefreshTask;
import club.hanfeng.freewalk.implement.view.IView;
import club.hanfeng.freewalk.implement.view.OnDataChangeListener;

public abstract class BaseViewGroup implements IView, OnDataChangeListener {
    private static final int MSG_REFRESH_DATA = 0;

    private final Context context;

    private ViewGroup rootView;

    private OnClickListener onClickListener;

    protected final ArrayList<IView> children = new ArrayList<IView>();

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_REFRESH_DATA) {
                onRequestLoadData(msg.arg1);
                Message newMsg = Message.obtain(msg);
                sendMessageDelayed(newMsg, msg.arg2);
            }
        }
    };

    public BaseViewGroup(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        if (context == null) {
            throw new RuntimeException("context not found");
        }
        return context;
    }

    @Override
    public void setRootView(View rootView) {
        this.rootView = (ViewGroup) rootView;
    }

    @Override
    public void createRootView(ViewGroup parent) {
        int rootViewResId = getRootViewResId();
        if (rootViewResId == 0) {
            throw new RuntimeException("root view res id not found");
        }
        View rootView = LayoutInflater.from(getContext()).inflate(rootViewResId, parent, false);
        setRootView(rootView);
    }

    @Override
    public int getRootViewResId() {
        return 0;
    }

    @Override
    public ViewGroup getRootView() {
        if (rootView == null) {
            throw new RuntimeException("root view not found");
        }
        return rootView;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public void onRequestLoadData(int key) {

    }

    @Override
    public void onInitChildren() {

    }

    @Override
    public void init() {
        onInitChildren();
        View view = getRootView();
        if (view != null) {
            view.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {

                @Override
                public void onViewAttachedToWindow(View v) {
                    requestLoadData();
                    onAttachedToWindow(v);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                    onDestroy();
                }
            });
        }
    }

    public void onAttachedToWindow(View v) {
    }

    private synchronized void requestLoadData() {
        List<IDataRefreshTask> tasks = getDataRefreshTasks();
        if (tasks != null && tasks.size() > 0) {
            handler.removeCallbacksAndMessages(null);
            for (IDataRefreshTask task : tasks) {
                requestLoadData(task.getTaskId(), task.getRefreshPeriod());
            }
        }
    }

    @Override
    public final void requestLoadData(int taskId) {
        requestLoadData(taskId, 0);
    }

    private void requestLoadData(int taskId, int refreshPeriod) {
        DataCenter.getInstance().registerListener(taskId, BaseViewGroup.this);
        onRequestLoadData(taskId);
        if (refreshPeriod != 0) {
            Message msg = Message.obtain(handler, MSG_REFRESH_DATA, taskId, refreshPeriod);
            handler.sendMessageDelayed(msg, refreshPeriod);
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        List<IDataRefreshTask> tasks = getDataRefreshTasks();
        if (tasks != null && tasks.size() > 0) {
            for (IDataRefreshTask task : tasks) {
                DataCenter.getInstance().unregisterListener(task.getTaskId());
            }
        }
    }

    @Override
    public void onPause() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        requestLoadData();
    }

    @Override
    public void onRefresh(int viewId) {

    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        return null;
    }

    @Override
    public void onDataChange(int key) {
    }

    @Override
    public boolean handbleBackKeyPress() {
        // TODO Auto-generated method stub
        return false;
    }
}
