package com.tinyplan.exam.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.ResultStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    /**
     * 签名
     *
     * @param claims 自定义信息列表
     * @param expire 有效时间(分钟)
     * @return JWT字符串
     */
    public static String sign(Map<String, String> claims, long expire) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Date signDate = new Date();
        Date expireDate = Date.from(LocalDateTimeUtil.of(signDate).plusMinutes(expire).atZone(ZoneId.systemDefault()).toInstant());

        JWTCreator.Builder builder = JWT.create()
                .withHeader(header)
                .withClaim("sub", "Access Token");

        for (Map.Entry<String, String> entry : claims.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }

        builder.withIssuedAt(signDate).withExpiresAt(expireDate);
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(EncryptUtil.SECRET_KEY);
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "令牌生成失败");
        }
        return builder.sign(algorithm);
    }

    /**
     * 解析
     *
     * @param token token
     * @return 负载列表
     */
    public static Map<String, Claim> verify(String token) {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(EncryptUtil.SECRET_KEY)).build();
        } catch (UnsupportedEncodingException e) {
            // token解析失败，当做非法请求
            throw new BusinessException(ResultStatus.RES_ILLEGAL_REQUEST);
        }
        DecodedJWT jwt = verifier.verify(EncryptUtil.decryptByAES(token));
        return jwt.getClaims();
    }

    /**
     * 获取自定义的数据负载
     *
     * @param request 请求体
     */
    public static JwtDataLoad getDataLoad(HttpServletRequest request) {
        return new JwtDataLoad(JwtUtil.verify(RequestUtil.getToken(request)));
    }

}
