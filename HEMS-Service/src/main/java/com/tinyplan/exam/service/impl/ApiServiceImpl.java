package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.api.CertificateApiClient;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateBack;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateFront;
import com.tinyplan.exam.service.ApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class ApiServiceImpl implements ApiService {

    @Resource(name = "certificateApiClient")
    private CertificateApiClient certificateApiClient;

    @Override
    public CertificateFront certificateFront(File front) {
        return certificateApiClient.transFront(certificateApiClient.recognizeFront(front));
    }

    @Override
    public CertificateBack certificateBack(File back) {
        return certificateApiClient.transBack(certificateApiClient.recognizeBack(back));
    }
}
