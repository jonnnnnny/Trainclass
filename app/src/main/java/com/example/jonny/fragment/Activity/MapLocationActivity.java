package com.example.jonny.fragment.Activity;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.R;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/25.
 */
public class MapLocationActivity extends Activity implements LocationSource,AMapLocationListener{
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;
    private OnLocationChangedListener mListener;
    public static final LatLng CHENGDU = new LatLng(30.676541,104.101025);
    String phone;
    int loc,status;
    Course course;
    private ImageButton iback;
    static final CameraPosition UESTC=new CameraPosition.Builder().target(CHENGDU).zoom(15).bearing(0).tilt(30).build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
	    /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        Bundle bundle=getIntent().getExtras();
        course=(Course) bundle.getSerializable("course");
        phone=bundle.getString("phone");
        status=bundle.getInt("status");

        init();
        iback=(ImageButton)findViewById(R.id.ib_mapback);
       /* iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(MapLocationActivity.this,DetailActivity.class);
                   Bundle bundle1=new Bundle();
                   bundle1.putSerializable("course",(Serializable)course);
                   bundle1.putInt("status",status);
                   bundle1.putString("phone",phone);
                   intent.putExtras(bundle1);
                    startActivity(intent);



            }
        });*/
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {

            aMap = mapView.getMap();

            // setUpMap();

            Marker marker=aMap.addMarker(new MarkerOptions()
                    .position(CHENGDU)
                    .draggable(true)
                    .title("电子科技大学沙河校区")

            );
            marker.showInfoWindow();


            CameraUpdate cameraupdate=CameraUpdateFactory.newLatLngZoom(CHENGDU,18);
            aMap.moveCamera(cameraupdate);


        }
    }


    private void setUpMap() {
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null!=mapLocationClient){
            mapLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener!=null&& aMapLocation!=null){
            if (aMapLocation!=null && aMapLocation.getErrorCode()==0){
                mListener.onLocationChanged(aMapLocation);
            }else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener=onLocationChangedListener;
        if (mapLocationClient==null){
            mapLocationClient=new AMapLocationClient(this);
            mapLocationClientOption=new AMapLocationClientOption();
            mapLocationClient.setLocationListener(this);
            mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mapLocationClient.setLocationOption(mapLocationClientOption);
            mapLocationClient.startLocation();

        }

    }

    @Override
    public void deactivate() {
        mListener=null;
        if (mapLocationClient!=null){
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient=null;

    }
}

