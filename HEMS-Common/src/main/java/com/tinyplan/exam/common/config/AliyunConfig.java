package com.tinyplan.exam.common.config;

import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.tinyplan.exam.common.properties.AliyunProperties;
import com.tinyplan.exam.common.utils.api.CertificateApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class AliyunConfig {

    @Resource(name = "aliyunProperties")
    private AliyunProperties aliyunProperties;

    @Bean("certificateApiClient")
    public CertificateApiClient certificateApiClient(){
        CertificateApiClient client = new CertificateApiClient();
        HttpClientBuilderParams builderParams = new HttpClientBuilderParams();
        builderParams.setAppKey(aliyunProperties.getCertificateKey());
        builderParams.setAppSecret(aliyunProperties.getCertificateSecret());
        client.init(builderParams);
        return client;
    }


}
