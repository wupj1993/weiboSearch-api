/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger;

import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.service.WeiBoDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author：WPJ587 2017/3/19 15:28.
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataApp.class)
public class EsWeiBoDataServiceTest {
    @Autowired
    WeiBoDataService esWeiBoDataService;

    @Test
    public void select() {
        WeiBoData esWeiBoData = esWeiBoDataService.selectById("B2094654D164A5FC4693");
        System.out.println(esWeiBoData);
    }
}
