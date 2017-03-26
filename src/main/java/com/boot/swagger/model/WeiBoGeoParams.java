/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用于微博数据附近人查询的实体类
 *
 * @author：WPJ587 2017/3/21 23:21.
 **/

public class WeiBoGeoParams extends BaseModel {
    private static final long serialVersionUID = -7034239700225347527L;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private Double longitude;
    /**
     * 距离从多少开始 eg: 1m 从一米开始
     */
    @ApiModelProperty(value = "距离开始 eg: 1m 从一米开始")
    private String from;
    /**
     * 距离到哪里结束 eg 1km 一千米结束
     */
    @ApiModelProperty(value = "距离结束 eg: 1km 一千米结束")
    private String to;
    /**
     * 起始人数 默认从0开始
     */
    @ApiModelProperty(value = "从第几个人开始")
    private Integer fromNum = 0;

    /**
     * 多少人 默认获取100人
     */
    @ApiModelProperty(value = "一共要多少人")
    private Integer size = 100;

    public WeiBoGeoParams() {
    }

    public WeiBoGeoParams(Double latitude, Double longitude, String from, String to, Integer fromNum, Integer size) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.from = from;
        this.to = to;
        this.fromNum = fromNum;
        this.size = size;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getFromNum() {
        return fromNum;
    }

    public void setFromNum(Integer fromNum) {
        this.fromNum = fromNum;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
