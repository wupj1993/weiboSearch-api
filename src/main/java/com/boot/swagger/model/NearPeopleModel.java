/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.model;

import com.boot.swagger.entity.WeiBoData;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author：WPJ587 2017/3/25 22:44.
 **/

public class NearPeopleModel extends WeiBoData {
    private static final long serialVersionUID = 6405154496503986425L;
    /**
     * 距离
     */
    @ApiModelProperty("距离")
    private String distance;

    public NearPeopleModel() {
    }

    public NearPeopleModel(String id, String title, String address, GeoPoint location, String city, String categoryName, Integer checkinNum, Integer photoNum, String distance) {
        super(id, title, address, location, city, categoryName, checkinNum, photoNum);
        this.distance = distance;
    }

    public NearPeopleModel(WeiBoData weiBoData, String distance) {
        super(weiBoData.getId(), weiBoData.getTitle(), weiBoData.getAddress(), weiBoData.getLocation(),
                weiBoData.getCity(), weiBoData.getCategoryName(), weiBoData.getCheckinNum(), weiBoData.getPhotoNum());
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
