/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger;

import com.boot.swagger.constant.ElasticSearchConstant;
import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.service.WeiBoDataService;
import com.boot.swagger.utils.BeanUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceRangeQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

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

    @Test
    public void filter() {
        Pageable page = new PageRequest(1, 20);
        GeoDistanceRangeQueryBuilder geoDistanceRangeQueryBuilder = new GeoDistanceRangeQueryBuilder("location");
        geoDistanceRangeQueryBuilder.queryName("location");
        //geoDistanceRangeQueryBuilder.lt(new GeoPoint(39.91291,116.30024)).gt(new GeoPoint(31.093493731,121.43676335));
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(geoDistanceRangeQueryBuilder)
                .withIndices(ElasticSearchConstant.WEI_BO_INDEX)
                .withTypes(ElasticSearchConstant.WEI_BO_TYPE)
                .withPageable(page)
                .build();
        Page<WeiBoData> result = elasticsearchTemplate.queryForPage(query, WeiBoData.class);
        for (WeiBoData weiBoData : result) {
            System.out.println(weiBoData.toString());
        }

    }

    /**
     * 附近的人
     */
    @Test
    public void people() {
        Double lat = 31.093493731;
        Double lon = 121.43676335;
        SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch(ElasticSearchConstant.WEI_BO_INDEX).setTypes(ElasticSearchConstant.WEI_BO_TYPE);
        srb.setFrom(0).setSize(100);//1000人
        GeoDistanceRangeQueryBuilder geoDistanceRangeQueryBuilder = new GeoDistanceRangeQueryBuilder("location");
        geoDistanceRangeQueryBuilder.point(lat, lon)
                .from("1m").to("1km").optimizeBbox("memory").geoDistance(GeoDistance.ARC);
        srb.setPostFilter(geoDistanceRangeQueryBuilder);
        // 获取距离多少公里 这个才是获取点与点之间的距离的
        GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
        sort.unit(DistanceUnit.METERS);
        sort.order(SortOrder.ASC);
        sort.point(lat, lon);
        srb.addSort(sort);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        Float usetime = searchResponse.getTookInMillis() / 1000f;
        System.out.println("附近的人(" + hits.getTotalHits() + "个)，耗时(" + usetime + "秒)：");
        WeiBoData weiBoData;
        for (SearchHit hit : searchHists) {
            weiBoData = new WeiBoData();
            weiBoData.setTitle(String.valueOf(hit.getSource().get("title")));
            weiBoData.setAddress(String.valueOf(hit.getSource().get("address")));
            weiBoData.setCheckinNum(Integer.valueOf(String.valueOf(hit.getSource().get("checkinNum"))));
            weiBoData.setPhotoNum(Integer.valueOf(String.valueOf(hit.getSource().get("photoNum"))));
            weiBoData.setCategoryName(String.valueOf(hit.getSource().get("categoryName")));
            weiBoData.setCity(String.valueOf(hit.getSource().get("city")));
            String name = (String) hit.getSource().get("title");
            Map<String, Object> geo = (Map<String, Object>) hit.getSource().get("location");
            System.out.println(geo);
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(geo.get("lat") + ""), Double.valueOf(geo.get("lon") + ""));
            BeanUtil.transMap2Bean(geo, geoPoint);
            weiBoData.setLocation(geoPoint);
            // 获取距离值，并保留两位小数点
            BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
            Map<String, Object> hitMap = hit.getSource();
            // 在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
            System.out.println(name + "的坐标：" + "" + "他距离眼泪八叉 鼻涕拉瞎" + hit.getSource().get("geoDistance") + DistanceUnit.METERS.toString());
        }
    }


}
