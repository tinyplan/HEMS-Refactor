package com.tinyplan.exam.common.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("qiniuProperties")
@PropertySource("classpath:config/qiniu.properties")
public class QiniuProperties {
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.domain}")
    private String domain;

    public String getBucket() {
        return bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getDomain() {
        return domain;
    }
}
