package club.hanfeng.freewalk.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.user.RegistStep2Activity;

public class GuideActivity extends Activity {

    private ViewPager vpGuide;
    private List<ImageView> data;
    private LinearLayout llGuide;
    private List<View> pointerList;
    private Button btnEnter;
    private SharedPreferences mPrep;
    private int[] imageRes = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private boolean once;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mPrep = getSharedPreferences("config", MODE_PRIVATE);
        once = getIntent().getBooleanExtra("once", false);

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        llGuide = (LinearLayout) findViewById(R.id.ll_guide);
        btnEnter = (Button) findViewById(R.id.btn_guide_enter);
        data = new ArrayList<>();
        pointerList = new ArrayList<>();

        initData();

    }

    /**
     * 初始化数据
     * viewpager和pointer的数量根据imageRes的长度动态变化
     */
    public void initData() {
        for (int i = 0; i < imageRes.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageRes[i]);
            data.add(imageView);
        }
        for (int j = 0; j < imageRes.length; j++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.shape_circle_grey_guide);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            if (j > 0) {
                params.leftMargin = 30;
            }
            view.setLayoutParams(params);
            //System.out.println("LinearLayout" + llGuide);
            //Log.e("TAG",llGuide.toString());
            llGuide.addView(view);
            pointerList.add(view);
        }
        pointerList.get(0).setBackgroundResource(R.drawable.shape_circle_blue_guide);
        updateUI();
    }

    /**
     * 更新界面
     */
    public void updateUI() {
        MyGuidePagerAdapter adapter = new MyGuidePagerAdapter();
        vpGuide.setAdapter(adapter);
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (View v : pointerList) {
                    v.setBackgroundResource(R.drawable.shape_circle_grey_guide);
                }
                pointerList.get(position).setBackgroundResource(R.drawable.shape_circle_blue_guide);
                if (position == imageRes.length - 1) {
                    btnEnter.setVisibility(View.VISIBLE);
                } else {
                    btnEnter.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 按钮的监听事件
     *
     * @param view
     */
    public void enterMain(View view) {
        if (once) {
            finish();
        } else {
            mPrep.edit().putBoolean("isFirst", false).commit();
            startActivity(new Intent(this, RegistStep2Activity.class));
            finish();

        }
    }

    class MyGuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(data.get(position));
            return data.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(data.get(position));
        }
    }

}
