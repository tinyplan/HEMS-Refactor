package com.tinyplan.exam.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("hemsProperties")
@PropertySource("classpath:config/hems.properties")
public class HEMSProperties {

    @Value("${hems.cors:false}")
    private boolean cors;
    @Value("${hems.token.expire:3600}")
    private long tokenExpire;
    @Value("${hems.token.encrypt:false}")
    private boolean encrypt;
    @Value("${hems.debug:false}")
    private boolean debug;
    @Value("${hems.news.tmp}")
    private String newsTmpDir;
    @Value("${hems.default.news.cover}")
    private String newsCoverImg;
    @Value("${hems.certificate.tmp}")
    private String certificateTmpDir;

    public boolean isCors() {
        return cors;
    }

    public long getTokenExpire() {
        return tokenExpire;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getNewsTmpDir() {
        return newsTmpDir;
    }

    public String getNewsCoverImg() {
        return newsCoverImg;
    }

    public String getCertificateTmpDir() {
        return certificateTmpDir;
    }
}
