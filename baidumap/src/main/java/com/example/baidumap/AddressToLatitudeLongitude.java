package com.example.baidumap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wanglei on 2017/6/20.
 * 根据百度地图API，根据地址得到经纬度
 */
public class AddressToLatitudeLongitude {
    private String address = "哈尔滨";//地址
    private double Latitude = 45.7732246332393;//纬度
    private double Longitude = 126.65771685544611;//经度

    public AddressToLatitudeLongitude(String addr_str) {
        this.address = addr_str;
    }

    /*
     *根据地址得到地理坐标
     */
    public void getLatAndLngByAddress() {
        String addr = "";
        String lat = "";
        String lng = "";
        try {
            addr = java.net.URLEncoder.encode(address, "UTF-8");//编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
                + "address=%s&ak=生成的ak&mcode=对应的安全码&output=json", addr);
        Log.d("panzqww", "-----url = " + url);
        URL myURL = null;
        URLConnection httpsConn = null;
        String[] location = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();//建立连接
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(//传输数据
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    System.out.println(data);
                    location = getLocation(data);
                }
                insr.close();
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (location == null || location.length < 2)
        {
            return;
        }
        this.Latitude = Double.parseDouble(location[0]);
        this.Longitude = Double.parseDouble(location[1]);
    }

    public Double getLatitude() {
        return this.Latitude;
    }

    public Double getLongitude() {
        return this.Longitude;
    }

    /*public static void main(String[] args) {
        AddressToLatitudeLongitude at = new AddressToLatitudeLongitude("安徽省亳州市亳州一中");
        at.getLatAndLngByAddress();
        System.out.println(at.getLatitude() + " " + at.getLongitude());
    }*/

    private String[] getLocation(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Log.d("panzqww", "-------- data = " + data + " status = " + jsonObject.getInt("status"));
        //这里的data为以下的json格式字符串,因为简单，所以就不使用json解析了，直接字符串处理
        //{"status":0,"result":{"location":{"lng":115.30448193078216,"lat":28.36523180795649},"precise":0,"confidence":14,"comprehension":100,"level":"区县"}} status = 0
        if (!(jsonObject.getInt("status") == 0)) {
            Log.d("panzqww", "==获取数据有误 = ");
            return null;
        }
        String subJson1 = jsonObject.getString("result");
        String subJson2 = new JSONObject(jsonObject.getString("result")).getString("location");
        JSONObject locationJson = new JSONObject(subJson2);
        Log.d("panzqww", "subJson1 = " + subJson1);
        Log.d("panzqww", "subJson2 = " + subJson2);
        String lat = locationJson.getString("lat");
        String lng = locationJson.getString("lng");
        return new String[]{lat, lng};
    }
}