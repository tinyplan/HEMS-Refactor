package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.pojo.aliyun.CertificateBack;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateFront;

import java.io.File;

public interface ApiService {

    CertificateFront certificateFront(File front);

    CertificateBack certificateBack(File back);

}
