package net.xwdoor.selectarea.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import net.xwdoor.selectarea.R;
import net.xwdoor.selectarea.base.BaseActivity;
import net.xwdoor.selectarea.db.DbManager;

public class MainActivity extends BaseActivity {

    private TextView tvLocation;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Button btnLocation;

    @Override
    protected void initVariables() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //开启gps定位
        mLocationOption.setGpsFirst(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        showLog("area", aMapLocation.toString());
                        tvLocation.setText(aMapLocation.getCity() + " " + aMapLocation.getDistrict());
                    } else {
                        tvLocation.setText("错误代码 " + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo());
                    }
                }
                mLocationClient.stopLocation();
                btnLocation.setText("重新定位");
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        RelativeLayout rlLocation = (RelativeLayout) findViewById(R.id.rl_location);
//        tvLocation = (TextView) findViewById(R.id.tv_locate);
        btnLocation = (Button) findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });
        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAreaActivity.startAct(MainActivity.this,null);
            }
        });
        startLocation();
    }

    /**
     * 定位
     */
    private void startLocation() {
        btnLocation.setText("正在定位...");
        tvLocation.setText("正在定位...");
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void loadData() {
        DbManager.getInstance(this).copyDbFile(DbManager.DB_NAME);
    }

}
