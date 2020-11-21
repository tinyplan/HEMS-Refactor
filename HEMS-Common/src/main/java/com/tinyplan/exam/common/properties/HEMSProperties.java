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

    public boolean isCors() {
        return cors;
    }

    public long getTokenExpire() {
        return tokenExpire;
    }

    public boolean isEncrypt() {
        return encrypt;
    }
}
