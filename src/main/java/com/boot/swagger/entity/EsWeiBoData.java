package com.boot.swagger.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * ES 微博数据实体
 */
@Document(indexName = "weibo", type = "weibo")
public class EsWeiBoData extends BaseEntity {
    private static final long serialVersionUID = -6729948021187345071L;
    /**
     * POI序号
     */
    @Id
    private String id;
    /**
     * 地点名
     */
    private String title;
    /**
     * 地址
     */
    private String address;
    @GeoPointField()
    private GeoPoint location;
    /**
     * 城市
     */
    private String city;
    /**
     * POI类别代码
     */
    private String categoryName;
    /**
     * 签到次数，
     */
    private Integer checkinNum;
    /**
     * 照片次数
     */
    private Integer photoNum;

    public EsWeiBoData() {
    }

    public EsWeiBoData(String id, String title, String address, GeoPoint locations, String city,
                       String categoryName, Integer checkinNum, Integer photoNum) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.location = locations;
        this.city = city;
        this.categoryName = categoryName;
        this.checkinNum = checkinNum;
        this.photoNum = photoNum;
    }


    public String getId() {
        return id;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint locations) {
        this.location = locations;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCheckinNum() {
        return checkinNum;
    }

    public void setCheckinNum(Integer checkinNum) {
        this.checkinNum = checkinNum;
    }

    public Integer getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(Integer photoNum) {
        this.photoNum = photoNum;
    }

}
