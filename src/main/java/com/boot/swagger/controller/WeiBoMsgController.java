/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.controller;

import com.boot.swagger.dto.BaseResult;
import com.boot.swagger.service.WeiBoDataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author：WPJ587 2017/3/17 22:10.
 **/
@RestController
@RequestMapping("/weibo")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = BaseResult.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
@Api(basePath = "/weibo", value = "EsWeiBoData", description = "微博签到信息", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeiBoMsgController extends BaseController {
    @Autowired
    private WeiBoDataService esWeiBoDataService;

    @ResponseBody
    @PostMapping("/citys")
    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "获取签到信息", notes = "根据城市查询签到信息", httpMethod = "POST", response = BaseResult.class)
    public BaseResult listWeiBoDataByCity(@ApiParam(required = true, name = "city", value = "城市名字")
                                          @RequestParam(name = "city", required = true) String city,
                                          @ApiParam(required = false, name = "size", value = "每页大小", defaultValue = "20")
                                          @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
                                          @ApiParam(required = false, name = "page", value = "当前页数", defaultValue = "1")
                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {

        return buildSuccessResultInfo(esWeiBoDataService.findByCity(city, new PageRequest(page, size)));

    }

    @ResponseBody
    @GetMapping("/id/{id}")
    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "获取签到信息", notes = "根据数据id查询签到信息", httpMethod = "GET", response = BaseResult.class)
    public BaseResult getWeiBoDataById(@ApiParam(required = true, name = "id", value = "数据id号")
                                       @PathVariable(name = "id", required = true) String id) {
        return buildSuccessResultInfo(esWeiBoDataService.selectById(id));
    }

    @ResponseBody
    public BaseResult getWeiboDataByPage(Pageable pageable) {
        return null;
    }
}
