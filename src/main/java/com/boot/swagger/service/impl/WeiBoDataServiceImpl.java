/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service.impl;

import com.boot.swagger.constant.ElasticSearchConstant;
import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.model.NearPeopleModel;
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
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

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
    public List<NearPeopleModel> findNearbyPeople(WeiBoGeoParams weiBoGeoParams) {
        List<NearPeopleModel> resultData = new ArrayList<>();
        SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch(ElasticSearchConstant.WEI_BO_INDEX).setTypes(ElasticSearchConstant.WEI_BO_TYPE);
        srb.setFrom(weiBoGeoParams.getFromNum()).setSize(weiBoGeoParams.getSize());
        GeoDistanceRangeQueryBuilder geoDistanceRangeQueryBuilder = new GeoDistanceRangeQueryBuilder(ElasticSearchConstant.LOCATION);
        geoDistanceRangeQueryBuilder.point(weiBoGeoParams.getLatitude(), weiBoGeoParams.getLongitude())
                .from(weiBoGeoParams.getFrom()).to(weiBoGeoParams.getTo()).optimizeBbox("memory").geoDistance(GeoDistance.ARC);
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
        NearPeopleModel weiBoData = null;
        for (SearchHit hit : searchHists) {
            weiBoData = new NearPeopleModel();
            weiBoData.setId(String.valueOf(hit.getSource().get("id") == null ? "" : hit.getSource().get("id")));
            weiBoData.setTitle(String.valueOf(hit.getSource().get("title") == null ? "" : hit.getSource().get("title")));
            weiBoData.setAddress(String.valueOf(hit.getSource().get("address") == null ? "" : hit.getSource().get("address")));
            weiBoData.setCheckinNum(Integer.valueOf(String.valueOf(hit.getSource().get("checkinNum") == null ? "" : String.valueOf(hit.getSource().get("checkinNum")))));
            weiBoData.setPhotoNum(Integer.valueOf(String.valueOf(hit.getSource().get("photoNum") == null ? "" : String.valueOf(hit.getSource().get("photoNum")))));
            weiBoData.setCategoryName(String.valueOf(hit.getSource().get("categoryName") == null ? "" : hit.getSource().get("categoryName")));
            weiBoData.setCity(String.valueOf(hit.getSource().get("city") == null ? "" : hit.getSource().get("city")));
            Map<String, Object> geo = (Map<String, Object>) hit.getSource().get("location");
            double lat;
            double lon;
            try {
                lat = Double.parseDouble(geo.get("lat") == null ? "0" : String.valueOf(geo.get("lat")));
                lon = Double.parseDouble(geo.get("lon") == null ? "0" : String.valueOf(geo.get("lon")));
            } catch (Exception e) {
                // 类型转换异常就全部设置为0
                lat = 0D;
                lon = 0D;
            }
            GeoPoint geoPoint = new GeoPoint(lat, lon);
            weiBoData.setLocation(geoPoint);
            // 获取距离值，并保留两位小数点
            BigDecimal geoDis = null;
            if (hit.getSortValues() != null) {
                geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
                weiBoData.setDistance(geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
            }
            resultData.add(weiBoData);
        }
        return resultData;
    }
}
