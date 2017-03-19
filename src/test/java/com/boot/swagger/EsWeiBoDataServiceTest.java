/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger;

import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.service.WeiBoDataService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;

/**
 * @author：WPJ587 2017/3/19 15:28.
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataApp.class)
public class EsWeiBoDataServiceTest {
    @Autowired
    WeiBoDataService esWeiBoDataService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void select() {
//        WeiBoData esWeiBoData = esWeiBoDataService.selectById("B2094654D164A5FC4693");
//        System.out.println(esWeiBoData);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices("test-index")
//                .withTypes("test-type")
//                .withPageable(new PageRequest(0,1))
//                .build();
//        String scrollId = elasticsearchTemplate.scan(searchQuery,1000,false);
//        List<WeiBoData> sampleEntities = new ArrayList<WeiBoData>();
//        boolean hasRecords = true;
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("city", "上海");
        SearchQuery query = new NativeSearchQueryBuilder()
                .withIndices("weibo")
                .withTypes("weibo")
                .withQuery(matchQueryBuilder)
                .withPageable(new PageRequest(1, 2))
                .build();
        Page<WeiBoData> data = elasticsearchTemplate.queryForPage(query, WeiBoData.class);
        Iterator<WeiBoData> iterator = data.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

    public void demo(String index, String type, String app, Pageable page) {

    }
}
