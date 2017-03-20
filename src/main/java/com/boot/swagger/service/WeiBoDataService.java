/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service;

import com.boot.swagger.entity.WeiBoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author：WPJ587 2017/3/19 14:41.
 **/

public interface WeiBoDataService {
    /**
     * 根据id获取微博签到数据
     *
     * @param id
     * @return
     */
    WeiBoData selectById(String id);

    /**
     * 分页获取
     *
     * @param pageRequest 分页数据
     * @return
     */
    Page<WeiBoData> findByPage(PageRequest pageRequest);

    /**
     * 根据城市进行全匹配
     *
     * @param city 城市名字
     * @param page 分页信息
     * @return
     */
    Page<WeiBoData> findByCity(String city, Pageable page);

    /**
     * 获取附近人的信息
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param from      距离从多少开始 eg: 1m 从一米开始
     * @param to        距离到哪里结束 eg 1km 一千米结束
     * @return
     */
    List<WeiBoData> findNearbyPeople(Double latitude, Double longitude, String from, String to);

}
