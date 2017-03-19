/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service;

import com.boot.swagger.entity.WeiBoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
}
