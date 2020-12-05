package com.tinyplan.exam.portal;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.tinyplan.exam.common.utils.api.CertificateApiClient;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateBack;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateFront;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
class PortalApplicationTests {

    @Resource(name = "certificateApiClient")
    private CertificateApiClient certificateApiClient;

    @Test
    void contextLoads() throws UnsupportedEncodingException {
        /*File image = new File("C:\\Users\\34054\\Desktop\\test\\front.jpg");
        HttpPost httpPost = new HttpPost("https://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Authorization", "APPCODE " + "bb3b2a5d060245e6aed95844e2cd3037");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("image", SecureUtil.md5(image)));
        params.add(new BasicNameValuePair("configure", "{'side': 'back'}"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        ObjectMapper data = new ObjectMapper();
        HttpClient client = null;
        String json = null;
        try {
            client = HttpClients.createDefault();
            HttpResponse response = client.execute(httpPost);
            json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
            ObjectMapper om = new ObjectMapper();
            Map map = om.readValue(json, Map.class);
            System.out.println(map.get("data"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    void test() throws IOException {
        File image = new File("C:\\Users\\34054\\Desktop\\test\\front.jpg");
        ApiResponse response = certificateApiClient.recognizeFront(image);
        System.out.println(getResultString(response));
        CertificateFront front = certificateApiClient.transFront(response);
        System.out.println(front);

        File image1 = new File("C:\\Users\\34054\\Desktop\\test\\back.jpg");
        ApiResponse response1 = certificateApiClient.recognizeBack(image1);
        System.out.println(getResultString(response1));
        CertificateBack back = certificateApiClient.transBack(response1);
        System.out.println(back);
    }

    private static String getResultString(ApiResponse response) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("Response from backend server").append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        result.append("ResultCode:").append(SdkConstant.CLOUDAPI_LF).append(response.getCode()).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        if(response.getCode() != 200){
            result.append("Error description:").append(response.getHeaders().get("X-Ca-Error-Message")).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        }

        result.append("ResultBody:").append(SdkConstant.CLOUDAPI_LF).append(new String(response.getBody() , SdkConstant.CLOUDAPI_ENCODING));

        return result.toString();
    }

}
