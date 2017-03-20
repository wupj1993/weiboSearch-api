/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.service.impl;

import com.boot.swagger.constant.ElasticSearchConstant;
import com.boot.swagger.entity.WeiBoData;
import com.boot.swagger.repository.WeiBoDataRepository;
import com.boot.swagger.service.WeiBoDataService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<WeiBoData> findNearbyPeople(Double latitude, Double longitude, String from, String to) {
        return null;
    }
}
