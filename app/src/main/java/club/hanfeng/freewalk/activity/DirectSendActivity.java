package club.hanfeng.freewalk.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.adapter.DirectRecyclerAdapter;
import club.hanfeng.freewalk.bean.DirectSendBean;
import club.hanfeng.freewalk.interfaces.OnRecyclerItemClickListener;

public class DirectSendActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private RecyclerView recycleView;
    private DirectRecyclerAdapter adapter;

    private ArrayList<DirectSendBean> listData;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_send);

        initActionBar();
        initView();
        initData();
        setListener();

    }

    private void setListener() {
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(DirectSendActivity.this, PictureViewerActivity.class));
//            }
//        });
        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                startActivity(new Intent(DirectSendActivity.this, PictureViewerActivity.class));
            }
        });
    }

    private void initData() {
        getDataFromNet();
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.rv_direct);
    }

    public void getDataFromNet() {

        listData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i == 4 || i == 0 || i == 7) {
                listData.add(new DirectSendBean("", "昨天", "", "", "今天的北京，天很蓝，很不错，这样的天气让心心旷神怡，很喜欢这个时候的北京。希望空气能够一直这样好，每天都有蓝天白云和温暖的阳光。这样的日子很舒服，很喜欢", "", "故宫", 2, 3));
            }else if (i == 5 || i == 3 || i == 9) {
                listData.add(new DirectSendBean("", "昨天", "", "", "今天的北京，天很蓝", "", "故宫", 2, 3));
            } else {
                listData.add(new DirectSendBean("", "昨天", "", "", "", "", "故宫", 2, 3));
            }
        }


        parseData();
    }

    private void parseData() {
        adapter = new DirectRecyclerAdapter(this, listData);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
