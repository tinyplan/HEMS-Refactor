package com.tinyplan.exam.common.utils.api;

import cn.hutool.core.codec.Base64;
import com.alibaba.cloudapi.sdk.client.ApacheHttpClient;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateBack;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateFront;

import java.io.File;

public class CertificateApiClient extends ApacheHttpClient {
    public static final String HOST = "dm-51.data.aliyun.com";
    public static final String PATH = "/rest/160601/ocr/ocr_idcard.json";
    public static final String CONFIG_SIDE_FRONT = "face";
    public static final String CONFIG_SIDE_BACK = "back";

    @Override
    public void init(HttpClientBuilderParams params) {
        params.setScheme(Scheme.HTTPS);
        params.setHost(HOST);
        super.init(params);
    }

    public ApiResponse recognizeFront(File front){
        return syncRequest(front, CONFIG_SIDE_FRONT);
    }

    public ApiResponse recognizeBack(File back){
        return syncRequest(back, CONFIG_SIDE_BACK);
    }

    /**
     * 同步方法
     *
     * @param image 身份证图片
     * @param side 正反面
     * @return 返回体
     */
    private ApiResponse syncRequest(File image, String side) {
        ApiRequest request = new ApiRequest(HttpMethod.POST_BODY , PATH);
        // 设置参数
        JSONObject body = new JSONObject();
        body.put("image", Base64.encode(image));
        JSONObject configure = new JSONObject();
        configure.put("side", side);
        body.put("configure", configure.toJSONString());
        request.setBody(body.toString().getBytes());
        return sendSyncRequest(request);
    }

    /**
     * 将返回消息转换为对象
     *
     * @param response 返回体
     * @return 身份证正面
     */
    public CertificateFront transFront(ApiResponse response) {
        String dataBody = new String(response.getBody() , SdkConstant.CLOUDAPI_ENCODING);
        if (response.getCode() != 200) {
            throw new BusinessException(ResultStatus.RES_API_CALL_FAILED);
        }
        CertificateFront front = JSON.parseObject(dataBody, CertificateFront.class);
        if (!front.isSuccess()) {
            throw new BusinessException(ResultStatus.RES_CERTIFICATE_RECOGNIZE_FAILED);
        }
        front.setBirth(this.formatDate(front.getBirth()));
        return front;
    }

    /**
     * 将返回消息转换为对象
     *
     * @param response 返回体
     * @return 身份证背面
     */
    public CertificateBack transBack(ApiResponse response) {
        String dataBody = new String(response.getBody() , SdkConstant.CLOUDAPI_ENCODING);
        if (response.getCode() != 200) {
            throw new BusinessException(ResultStatus.RES_API_CALL_FAILED);
        }
        CertificateBack back = JSON.parseObject(dataBody, CertificateBack.class);
        if (!back.isSuccess()) {
            throw new BusinessException(ResultStatus.RES_CERTIFICATE_RECOGNIZE_FAILED);
        }
        back.setStart_date(this.formatDate(back.getStart_date()));
        back.setEnd_date(this.formatDate(back.getEnd_date()));
        return back;
    }

    /**
     * 格式化时间 (20201002 --> 2020-10-02)
     *
     * @param date 未格式化的时间
     * @return 格式化之后的时间
     */
    private String formatDate(String date) {
        String year = date.substring(0, 4);
        String mouth = date.substring(4, 6);
        String day = date.substring(6);
        return String.join("-", year, mouth, day);
    }
}
