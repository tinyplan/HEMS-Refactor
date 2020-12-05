package com.tinyplan.exam.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("aliyunProperties")
@PropertySource("classpath:config/aliyun.properties")
public class AliyunProperties {

    @Value("${aliyun.certificate.key}")
    private String certificateKey;
    @Value("${aliyun.certificate.secret}")
    private String certificateSecret;

    public String getCertificateKey() {
        return certificateKey;
    }

    public String getCertificateSecret() {
        return certificateSecret;
    }
}
