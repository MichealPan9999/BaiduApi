package com.example.panzq.baiduapi;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        init();
    }

    private void init(){
        //描述地图将要发生的变化，使用工厂类MapStatusUpdateFactory创建，设置级别
        //为18，进去就是18了，默认是12
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(20);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        //是否显示缩放按钮
        //mMapView.showZoomControls(false);
        //经纬度(纬度，经度) 我们这里设置深圳世界之窗的位置
        LatLng latlng = new LatLng(22.5422870000, 113.9804440000);
        MapStatusUpdate mapStatusUpdate_circle = MapStatusUpdateFactory.newLatLng(latlng);
        mBaiduMap.setMapStatus(mapStatusUpdate_circle);

        //显示指南针
        mBaiduMap.getUiSettings().setCompassEnabled(true);
        //显示位置
        //mBaiduMap.getUiSettings().setCompassPosition(new Point(0, 0));
        // 设置地图类型
        //mBaiduMap.setMapType(mBaiduMap.MAP_TYPE_NORMAL);
        // 卫星图
        mBaiduMap.setMapType(mBaiduMap.MAP_TYPE_SATELLITE);
        // 交通图是否打开
        mBaiduMap.setTrafficEnabled(true);
        //drawCircle();
        //drawText();
        drawMark();
    }

    // 绘制圆
    private void drawCircle() {
        // 1.创建自己
        CircleOptions circleOptions = new CircleOptions();
        // 2.设置数据 以世界之窗为圆心，1000米为半径绘制
        circleOptions.center(new LatLng(22.5422870000, 113.9804440000))//中心
                .radius(1000)  //半径
                .fillColor(0x60FF0000)//填充圆的颜色
                .stroke(new Stroke(10, 0x600FF000));  //边框的宽度和颜色
        //把绘制的圆添加到百度地图上去
        mBaiduMap.addOverlay(circleOptions);
    }

    // 绘制文字
    private void drawText() {
        TextOptions textOptions = new TextOptions();
        textOptions.fontColor(Color.RED) //设置字体颜色
                .text("自定义文字覆盖物")  //设置显示文本
                .position(new LatLng(22.5422870000, 113.9804440000))   //设置显示坐标
                .fontSize(50) //设置文本大小
                .typeface(Typeface.SERIF)  //设置字体 Android的字体就三种，对称的，不对称的，等宽的
                .rotate(30);  //设置旋转角度
        //把绘制的圆添加到百度地图上去
        mBaiduMap.addOverlay(textOptions);
    }
    // 绘制mark覆盖物
    private void drawMark() {
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.logo); // 描述图片
        markerOptions.position(new LatLng(22.5422870000, 113.9804440000)) // 设置位置
                .icon(bitmap) // 加载图片
                .draggable(true) // 支持拖拽
                .title("世界之窗旁边的草房"); // 显示文本
        //把绘制的圆添加到百度地图上去
        mBaiduMap.addOverlay(markerOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
