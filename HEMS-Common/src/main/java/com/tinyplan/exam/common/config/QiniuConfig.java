package com.tinyplan.exam.common.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.tinyplan.exam.common.properties.QiniuProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置类
 */
@Configuration
public class QiniuConfig {

    @Bean("qiniuAuth")
    public Auth qiniuAuth(QiniuProperties qiniuProperties) {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    @Bean("qiniuConfiguration")
    public com.qiniu.storage.Configuration qiniuConfiguration() {
        com.qiniu.storage.Configuration config = new com.qiniu.storage.Configuration(Region.huanan());
        config.useHttpsDomains = false;
        return config;
    }

    @Bean("uploadManager")
    public UploadManager uploadManager(com.qiniu.storage.Configuration configuration) {
        return new UploadManager(configuration);
    }

    @Bean("bucketManager")
    public BucketManager bucketManager(Auth auth, com.qiniu.storage.Configuration configuration) {
        return new BucketManager(auth, configuration);
    }

}
