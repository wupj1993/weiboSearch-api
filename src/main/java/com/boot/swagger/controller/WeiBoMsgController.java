/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.controller;

import com.boot.swagger.dto.BaseResult;
import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.service.WeiBoDataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：WPJ587 2017/3/17 22:10.
 **/
@RestController
@RequestMapping("/weibo")

@Api(basePath = "/weibo", value = "WeiBoData", description = "微博签到信息", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeiBoMsgController extends BaseController {
    @Autowired
    private WeiBoDataService esWeiBoDataService;
    @ResponseBody
    @GetMapping("/city/{city}")
    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,value = "获取敲到信息", notes = "根据城市查询签到信息", httpMethod = "GET", response = BaseResult.class)
    public BaseResult listWeiBoDataByCity(@ApiParam(required = true, name = "city", value = "城市名字")
                                          @PathVariable(name = "city",required = true)String city) {
        //TODO 查询
        WeiBoData esWeiBoData;
        List<WeiBoData> esWeiBoDataList = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            esWeiBoData = new WeiBoData();
            esWeiBoData.setId(""+i);
            esWeiBoData.setPhotoNum(i);
            esWeiBoData.setAddress("福建");
            esWeiBoData.setCategoryName("师范大学");
            esWeiBoData.setLocation(new GeoPoint(10.222,20.433));
            esWeiBoData.setCheckinNum(i+5);
            esWeiBoData.setTitle("我在这里签到");
            esWeiBoDataList.add(esWeiBoData);
        }
        return buildSuccessResultInfo(esWeiBoDataList);

    }

    @ResponseBody
    @GetMapping("/id/{id}")
    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "获取敲到信息", notes = "根据数据id查询签到信息", httpMethod = "GET", response = BaseResult.class)
    public BaseResult getWeiBoDataById(@ApiParam(required = true, name = "id", value = "数据id号")
                                       @PathVariable(name = "id", required = true) String id) {

        return buildSuccessResultInfo(esWeiBoDataService.selectById(id));
    }
}
