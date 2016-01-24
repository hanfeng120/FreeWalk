package club.hanfeng.freewalk.interfaces.studio;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import club.hanfeng.freewalk.activity.SceneActivity;

/**
 * Created by HanFeng on 2015/12/2.
 */
public interface OnRecyclerItemClickListener {

    void onItemClick(View view, int position, long id);

}
