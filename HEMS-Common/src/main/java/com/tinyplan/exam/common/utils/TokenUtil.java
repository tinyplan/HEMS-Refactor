package com.tinyplan.exam.common.utils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static final String[] TOKEN_HEADERS = {"x-token", "Authorization"};

    /**
     * 获取token
     * @param request 请求体
     * @return token
     */
    public static String getToken(HttpServletRequest request){
        String token = null;
        for (String header : TOKEN_HEADERS) {
            token = request.getHeader(header);
            // 获取到值之后就直接返回
            if (token != null) {
                break;
            }
        }
        return token;
    }


}
