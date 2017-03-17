/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.controller;

import com.boot.swagger.dto.BaseResult;
import com.boot.swagger.entity.EsWeiBoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author：WPJ587 2017/3/17 22:10.
 **/
@RestController
@RequestMapping("/people")
@Api(basePath = "/people", value = "EsWeiBoData", description = "个人信息",produces = MediaType.APPLICATION_JSON_VALUE)
public class PeopleMsgController extends BaseController{
    @ResponseBody
    @RequestMapping(value = "/id/{idCard}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息", notes = "根据用户身份证", httpMethod = "GET", response = BaseResult.class)
    public BaseResult getPeopleByIdCard(@PathVariable("idCard") String idCard){
        return buildSuccessResultInfo(new EsWeiBoData());
    }
}
