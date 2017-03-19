/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service.impl;

import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.repository.WeiBoDataRepository;
import com.boot.swagger.service.WeiBoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：WPJ587 2017/3/19 14:42.
 **/
@Service
public class WeiBoDataServiceImpl implements WeiBoDataService {
    @Autowired
    private WeiBoDataRepository weiBoDataRepository;

    @Override
    public WeiBoData selectById(String id) {
        if (id == null) {
            return null;
        }
        return weiBoDataRepository.findOne(id);
    }
}
