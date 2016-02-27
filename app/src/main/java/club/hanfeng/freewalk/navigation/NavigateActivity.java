package club.hanfeng.freewalk.navigation;

import android.content.Intent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.homepage.HomePageManager;
import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.tabbar.TabBarConstants;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.scene.SceneActivity;
import cn.bmob.v3.listener.FindListener;

public class NavigateActivity extends BaseActivity implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.OnMapClickListener {

    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption optionClient;
    private Marker marker;
    private HashMap<String, String> idMaps = new HashMap<>();

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_navigate;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        setTitle(FreeWalkApplication.getCityName() + "·" + FreeWalkApplication.getSceneName());
        return true;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        mapView = (MapView) findViewById(R.id.amap);
        aMap = mapView.getMap();
        uiSettings = aMap.getUiSettings();
        initAMap();
    }

    @Override
    protected void initBottomBar() {

    }

    public void initData() {
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

    public void showPoi(String type) {
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
        aMap.clear();
        for (HomePagePoi poi : list) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.valueOf(poi.getLat()), Double.valueOf(poi.getLon())));
            markerOptions.title(poi.getName());
            marker = aMap.addMarker(markerOptions);
            marker.setTitle(poi.getName());
            idMaps.put(poi.getName(), poi.getId());
        }
    }

    private void initAMap() {
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);

        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setMyLocationEnabled(true);
        aMap.setLocationSource(this);
    }

    public void reLoadMap() {
        if (aMap != null) {
            aMap.reloadMap();
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getContext(), SceneActivity.class);
        intent.putExtra(SceneConstants.EXTRA_ID, idMaps.get(marker.getTitle()));
        startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    protected void refreshView(int viewId) {

    }

}
