package net.xwdoor.selectarea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.xwdoor.selectarea.R;
import net.xwdoor.selectarea.base.BaseActivity;
import net.xwdoor.selectarea.db.DbManager;
import net.xwdoor.selectarea.entity.Location;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/24 024 16:54.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SelectAreaActivity extends BaseActivity {
    public static final int LOAD_PROVINCE = 0;
    public static final int LOAD_CITY = 1;
    public static final int LOAD_DISTRICT = 2;

    private Button btnProvince;
    private Button btnCity;
    private Button btnDistrict;
    private ArrayList<String> mProvinceList;
    private ListView lvList;
    private int mode;
    private Location mLocation;
    private ArrayList<String> mCityList;
    private ArrayList<String> mDistrictList;

    public static void startAct(Context context, Location location) {
        Intent intent = new Intent(context, SelectAreaActivity.class);
        if (location != null) {
            intent.putExtra("location", location);
        }
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {
        mLocation = (Location) getIntent().getSerializableExtra("location");
        if (mLocation != null) {
            btnProvince.setText(mLocation.province);
            btnCity.setText(mLocation.city);
            btnDistrict.setText(mLocation.district);
        }
        mProvinceList = DbManager.getInstance(this).getProvince();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_area);
        btnProvince = (Button) findViewById(R.id.btn_province);
        btnCity = (Button) findViewById(R.id.btn_city);
        btnDistrict = (Button) findViewById(R.id.btn_district);
        lvList = (ListView) findViewById(R.id.lv_list);

        btnProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProvince();
            }
        });
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCity(btnProvince.getText().toString());
            }
        });
        btnDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDistrict(btnCity.getText().toString());
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mode) {
                    case LOAD_PROVINCE://省份

                        loadCity(mProvinceList.get(position));
                        break;
                    case LOAD_CITY://城市

                        loadDistrict(mCityList.get(position));
                        break;
                    case LOAD_DISTRICT://区域

                        mLocation.district = mDistrictList.get(position);
                        btnDistrict.setText(mLocation.district);
                        showToast(mLocation.toString());
                        break;
                }
            }
        });

        loadProvince();
    }

    private void loadProvince() {
        if (mLocation == null) {
            mLocation = new Location();
        }
        mode = LOAD_PROVINCE;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, mProvinceList);
        lvList.setAdapter(adapter);
    }

    private void loadCity(String province) {
        if (mLocation == null) {
            showToast("请选择省份");
            return;
        }
        mLocation.province = province;
        btnProvince.setText(province);
        mode = LOAD_CITY;
        mCityList = DbManager.getInstance(this).getCitis(province);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, mCityList);
        lvList.setAdapter(adapter);
    }

    private void loadDistrict(String city) {
        if (mLocation == null) {
            showToast("请选择城市");
            return;
        }
        mLocation.city = city;
        btnCity.setText(city);
        mode = LOAD_DISTRICT;
        mDistrictList = DbManager.getInstance(this).getDistrict(city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, mDistrictList);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }
}