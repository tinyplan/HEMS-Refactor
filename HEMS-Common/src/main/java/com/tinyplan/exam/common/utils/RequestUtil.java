package com.tinyplan.exam.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestUtil {
    public static final String[] TOKEN_HEADERS = {"x-token", "Authorization"};

    private static final String[] IP_HEADERS = {
            "x-forwarded-for",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    public static final String IP_UNKNOWN = "unknown";
    public static final String IP_LOCAL_IPV4 = "127.0.0.1";
    public static final String IP_LOCAL_IPV6 = "0:0:0:0:0:0:0:1";

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

    /**
     * 获取发送请求的ip地址
     * @param request 请求体
     * @return IP地址
     */
    public static String getIpAddress(HttpServletRequest request){
        String ip = null;
        // 代理转发的情况
        for (String header : IP_HEADERS) {
            ip = request.getHeader(header);
            // 判断IP是否为空
            if (!isEmptyIP(ip)) {
                break;
            }
        }
        // 本地IP
        if (isEmptyIP(ip)) {
            ip = request.getRemoteAddr();
            if (isLocalIP(ip)) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inetAddress.getHostAddress();
            }
        }
        // 多代理转发
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return isEmptyIP(ip) ? request.getRemoteAddr() : ip;
    }

    /**
     * 判断IP地址是否为空
     * @param ip ip地址
     */
    private static boolean isEmptyIP(String ip){
        return ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip);
    }

    private static boolean isLocalIP(String ip){
        return IP_LOCAL_IPV4.equals(ip) || IP_LOCAL_IPV6.equals(ip);
    }


}
