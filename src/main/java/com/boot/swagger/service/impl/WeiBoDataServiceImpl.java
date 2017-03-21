/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service.impl;

import com.boot.swagger.constant.ElasticSearchConstant;
import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.model.WeiBoGeoParams;
import com.boot.swagger.repository.WeiBoDataRepository;
import com.boot.swagger.service.WeiBoDataService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author：WPJ587 2017/3/19 14:42.
 **/
@Service
public class WeiBoDataServiceImpl implements WeiBoDataService {
    @Autowired
    private WeiBoDataRepository weiBoDataRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public WeiBoData selectById(String id) {
        if (id == null) {
            return null;
        }
        return weiBoDataRepository.findOne(id);
    }

    @Override
    public Page<WeiBoData> findByPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<WeiBoData> findByCity(String city, Pageable page) {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("city", city);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withIndices(ElasticSearchConstant.WEI_BO_INDEX)
                .withTypes(ElasticSearchConstant.WEI_BO_TYPE)
                .withQuery(matchQueryBuilder)
                .withPageable(page)
                .build();
        return elasticsearchTemplate.queryForPage(query, WeiBoData.class);
    }

    @Override
    public List<WeiBoData> findNearbyPeople(WeiBoGeoParams weiBoGeoParams) {
        List<WeiBoData> resultData = new ArrayList<>();
        SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch(ElasticSearchConstant.WEI_BO_INDEX).setTypes(ElasticSearchConstant.WEI_BO_TYPE);
        srb.setFrom(weiBoGeoParams.getFromNum()).setSize(weiBoGeoParams.getSize());
        GeoDistanceRangeQueryBuilder geoDistanceRangeQueryBuilder = new GeoDistanceRangeQueryBuilder("location");
        geoDistanceRangeQueryBuilder.point(weiBoGeoParams.getLatitude(), weiBoGeoParams.getLongitude())
                .from("1m").to("1km").optimizeBbox("memory").geoDistance(GeoDistance.ARC);
        srb.setPostFilter(geoDistanceRangeQueryBuilder);
        // 获取距离多少公里 这个才是获取点与点之间的距离的
        GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(ElasticSearchConstant.LOCATION);
        sort.unit(DistanceUnit.METERS);
        sort.order(SortOrder.ASC);
        sort.point(weiBoGeoParams.getLatitude(), weiBoGeoParams.getLongitude());
        srb.addSort(sort);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        WeiBoData weiBoData = null;
        for (SearchHit hit : searchHists) {
            weiBoData = new WeiBoData();
            weiBoData.setTitle(String.valueOf(hit.getSource().get("title")));
            weiBoData.setAddress(String.valueOf(hit.getSource().get("address")));
            weiBoData.setCheckinNum(Integer.valueOf(String.valueOf(hit.getSource().get("checkinNum"))));
            weiBoData.setPhotoNum(Integer.valueOf(String.valueOf(hit.getSource().get("photoNum"))));
            weiBoData.setCategoryName(String.valueOf(hit.getSource().get("categoryName")));
            weiBoData.setCity(String.valueOf(hit.getSource().get("city")));
            Object object = hit.getSource().get("location");
            // 获取距离值，并保留两位小数点
            BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
            Map<String, Object> hitMap = hit.getSource();
            // 在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
            //  System.out.println(name + "的坐标：" + object + "他距离眼泪八叉 鼻涕拉瞎" + hit.getSource().get("geoDistance") + DistanceUnit.METERS.toString());
        }
        return null;
    }
}
