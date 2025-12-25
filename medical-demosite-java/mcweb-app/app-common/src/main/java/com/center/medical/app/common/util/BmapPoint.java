/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.util;

/**
 * 用于构造百度地图中的经纬度点
 *
 * @author zhengtian
 * @date 2013-8-5 下午02:54:41
 */
public class BmapPoint {
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;

    public BmapPoint() {

    }

    public BmapPoint(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BmapPoint) {
            BmapPoint bmapPoint = (BmapPoint) obj;
            return (bmapPoint.getLng() == lng && bmapPoint.getLat() == lat) ? true : false;
        } else {
            return false;
        }
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
