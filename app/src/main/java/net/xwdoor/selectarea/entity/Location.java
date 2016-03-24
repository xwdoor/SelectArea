package net.xwdoor.selectarea.entity;

import java.io.Serializable;

/**
 * Created by XWdoor on 2016/3/24 024 17:10.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Location implements Serializable {
    public Location() {
    }

    public Location(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String province;
    public String city;
    public String district;

    @Override
    public String toString() {
        return province + " " + city + " " + district;
    }
}
