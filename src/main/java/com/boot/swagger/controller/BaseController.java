/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boot.swagger.dto.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class BaseController {
    public Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    SerializerFeature[] feature =
            { SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteMapNullValue };

    /**
     * @description: 构造成功返回结果
     * @param resultData  : 需要返回的数据，可选
     * @return
     */
    protected BaseResult buildSuccessResultInfo(Object resultData)
    {
        LOGGER.debug(String.format("enter function, %s", resultData));
        BaseResult resultVo = new BaseResult();
        resultVo.setResultData(resultData);
        resultVo.setResultMessage("success");
        resultVo.setResultCode(HttpStatus.OK);
        return  resultVo;
      //  return JSON.toJSONString(resultVo, feature);
    }

    /**
     * @description: 构造失败返回结果
     * @param resultCode
     *            :任意非0数字，代表失败
     * @param failedMsg
     *            ：失败信息
     * @return
     */

    protected BaseResult buildFailedResultInfo(HttpStatus resultCode, String failedMsg)
    {
        BaseResult resultVo = new BaseResult(resultCode, failedMsg);
       // return JSON.toJSONString(resultVo, feature);
        return resultVo;
    }
}