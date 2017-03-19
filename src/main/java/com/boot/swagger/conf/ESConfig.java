/*
 * Copyright (c) 2017 wupj e-mail:wpjlovehome@gmail.com.
 */

package com.boot.swagger.conf;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Name：ESConfig
 * Time：2016/11/30 22:42
 * author：WPJ587
 * description：es配置
 **/
@Configuration
public class ESConfig {
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }

    @Bean
    public Client client() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "pj").build();
        TransportClient client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("master"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("slave1"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("slave2"), 9300));
        return client;
    }
}

