package com.tinyplan.exam.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    /**
     * 签名
     *
     * @param claims 自定义信息列表
     * @param expire 有效时间(分钟)
     * @return JWT字符串
     * @throws UnsupportedEncodingException
     */
    public static String sign(Map<String, String> claims, long expire) throws UnsupportedEncodingException {
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
        return builder.sign(Algorithm.HMAC256(EncryptUtil.SECRET_KEY));
    }

    /**
     * 解析
     *
     * @param token token
     * @return 负载列表
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Claim> verify(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(EncryptUtil.SECRET_KEY)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

}
