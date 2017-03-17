/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.controller;

import com.boot.swagger.dto.BaseResult;
import com.boot.swagger.entity.EsWeiBoData;
import io.swagger.annotations.*;
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
@Api(basePath = "/weibo", value = "EsWeiBoData", description = "微博签到信息",produces = MediaType.APPLICATION_JSON_VALUE)
public class WeiBoMsgController extends BaseController {
    @ResponseBody
    @GetMapping("/city/{city}")
    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,value = "获取敲到信息", notes = "根据城市查询签到信息", httpMethod = "GET", response = BaseResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResult.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResult listWeiBoDataByCity(@ApiParam(required = true, name = "city", value = "城市名字")
                                              @PathVariable(name = "city",required = true)String city) {
        //TODO 查询
        EsWeiBoData esWeiBoData;
        List<EsWeiBoData> esWeiBoDataList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            esWeiBoData=new EsWeiBoData();
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
}
