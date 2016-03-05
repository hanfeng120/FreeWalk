package club.hanfeng.freewalk.navigation;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
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
import club.hanfeng.freewalk.core.navigation.NavigationConstants;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.tabbar.TabBarConstants;
import club.hanfeng.freewalk.scene.SceneActivity;
import club.hanfeng.freewalk.utils.map.AMapUtils;
import cn.bmob.v3.listener.FindListener;

public class NavigateActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.OnMapClickListener {

    private static final String GEOFENCE_BROADCAST_ACTION = "club.hanfeng.freewalk.geofence.broadcast";

    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    private OnLocationChangedListener locationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption optionClient;
    private HashMap<String, String> idMaps = new HashMap<>();
    private Marker marker;

    private List<HomePagePoi> list;
    private boolean isFirst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        initBar();
        initView(savedInstanceState);
        initAMap();
        initData();

    }

    private void initBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(FreeWalkApplication.getCityName() + " · " + FreeWalkApplication.getSceneName());
    }

    private void initView(Bundle savedInstanceState) {
        MapsInitializer.sdcardDir = AMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.amap);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        uiSettings = aMap.getUiSettings();
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

    public void initData() {
        HomePageManager.getInstance().getScenes(this, TabBarConstants.TYPE_ID_ALL_SCENE, new FindListener<HomePagePoi>() {
            @Override
            public void onSuccess(List<HomePagePoi> list) {
                NavigateActivity.this.list = list;
                registerGeoFence();
                showAllPoi(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void showPoi(String type) {
        HomePageManager.getInstance().getScenes(this, type, new FindListener<HomePagePoi>() {
            @Override
            public void onSuccess(List<HomePagePoi> list) {
                NavigateActivity.this.list = list;
                registerGeoFence();
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

    public void reLoadMap() {
        if (aMap != null) {
            aMap.reloadMap();
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this);
            optionClient = new AMapLocationClientOption();

            //设置是否返回地址信息（默认返回地址信息）
            optionClient.setNeedAddress(true);
            //设置是否只定位一次,默认为false
            optionClient.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            optionClient.setWifiActiveScan(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            optionClient.setMockEnable(true);
            //设置定位间隔,单位毫秒,默认为2000ms
            optionClient.setInterval(2000);
            //设置为高精度定位模式
            optionClient.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            locationClient.setLocationOption(optionClient);
            locationClient.setLocationListener(this);
            locationClient.startLocation();
        }
    }

    private void registerGeoFence() {
        if (list != null && locationClient != null && isFirst) {
            for (int i = 0; i < list.size(); i++) {
                Intent intent = new Intent(GEOFENCE_BROADCAST_ACTION);
                intent.putExtra(NavigationConstants.TYPE_ID, list.get(i).getId());
                intent.putExtra(NavigationConstants.TYPE_VOICE_PATH, list.get(i).getVoicePath());
                intent.putExtra(SceneConstants.EXTRA_TITLE, getTitle());
                PendingIntent mPendingIntent;
                mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                locationClient.addGeoFenceAlert(list.get(i).getId(), Double.valueOf(list.get(i).getLat()), Double.valueOf(list.get(i).getLon()), 20, -1, mPendingIntent);
            }
            isFirst = false;
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(amapLocation);
            registerGeoFence();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, SceneActivity.class);
        intent.putExtra(SceneConstants.EXTRA_ID, idMaps.get(marker.getTitle()));
        startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
