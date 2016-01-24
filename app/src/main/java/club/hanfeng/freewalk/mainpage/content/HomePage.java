package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.homepage.HomePageManager;
import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.tabbar.TabBarConstants;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnHomeTopBarSelectedListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class HomePage extends BaseViewGroup implements OnHomeTopBarSelectedListener, LocationSource, AMapLocationListener {

    private AMap aMap;
    private UiSettings uiSettings;
    private OnLocationChangedListener locationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption optionClient;

    public HomePage(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_homepage;
    }

    @Override
    public void onInitChildren() {

    }

    public void setAMap(AMap aMap) {
        this.aMap = aMap;
        this.uiSettings = aMap.getUiSettings();
        initAMap();
        initData();
    }

    private void initData() {
        HomePageManager.getInstance().getScenes(getContext(), TabBarConstants.TYPE_ID_ALL_SCENE, new FindListener<HomePagePoi>() {
            @Override
            public void onSuccess(List<HomePagePoi> list) {
                showAllPoi(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onTopBarSelected(String type) {
        HomePageManager.getInstance().getScenes(getContext(), type, new FindListener<HomePagePoi>() {
            @Override
            public void onSuccess(List<HomePagePoi> list) {
                showAllPoi(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void showAllPoi(List<HomePagePoi> list) {

    }

    private void initAMap() {
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);

        aMap.setMyLocationEnabled(true);
        aMap.setLocationSource(this);
    }

    public void reLoadMap() {
        if (aMap != null) {
            aMap.reloadMap();
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getContext());
            optionClient = new AMapLocationClientOption();
            //设置定位监听
            locationClient.setLocationListener(this);
            //设置为高精度定位模式
            optionClient.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            locationClient.setLocationOption(optionClient);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        locationChangedListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(aMapLocation);
        }
    }

}
