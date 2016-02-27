package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import club.hanfeng.freewalk.navigation.NavigateActivity;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class HomePage extends BaseViewGroup {

    private ImageView ivInfo;
    private Button btnNavigate;

    public HomePage(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_homepage;
    }

    @Override
    public void onInitChildren() {
        ivInfo = (ImageView) getRootView().findViewById(R.id.introduce);
        btnNavigate = (Button) getRootView().findViewById(R.id.navigate);

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NavigateActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> task = new ArrayList<>();
        task.add(new DataRefreshTask(MainPageConstants.TASK_ID_SCENE_CHANGED, 0));
        return task;
    }

    @Override
    public void onDataChange(int key) {
        if (key == MainPageConstants.TASK_ID_SCENE_CHANGED) {
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setImageScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(ivInfo, FreeWalkApplication.getSceneMapUrl(), builder.build());
        }
    }

}
