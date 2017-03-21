/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service;

import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.model.WeiBoGeoParams;
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
     * @param weiBoGeoParams 查询的实体类
     * @return
     */
    List<WeiBoData> findNearbyPeople(WeiBoGeoParams weiBoGeoParams);

}
