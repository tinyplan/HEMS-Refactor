package com.tinyplan.exam.common.service.impl;

import com.tinyplan.exam.common.constant.RedisKey;
import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.common.service.TokenService;
import com.tinyplan.exam.common.utils.EncryptUtil;
import com.tinyplan.exam.common.utils.JwtUtil;
import com.tinyplan.exam.common.utils.RedisUtil;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {
    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;
    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return token(使用AES对称加密方式)
     */
    public String generateToken(String userId, String roleId) {
        Map<String , String> map = new HashMap<>();
        map.put(JwtDataLoad.CLAIM_KEY_USER_ID, userId);
        map.put(JwtDataLoad.CLAIM_KEY_ROLE_ID, roleId);
        String token = JwtUtil.sign(map, hemsProperties.getTokenExpire());
        if (hemsProperties.isEncrypt()) {
            token = EncryptUtil.encryptByAES(token);
        }
        return token;
    }

    public Object getValue(String token) {
        return redisUtil.get(token);
    }

    /**
     * 设置token
     *
     * @param token token
     * @param value 值
     * @return 是否插入成功
     *
     * 会设置默认失效时间
     */
    public boolean setValue(String token, Object value) {
        return redisUtil.set(token, value, hemsProperties.getTokenExpire());
    }

    /**
     * 删除token
     *
     * @param token token
     */
    public void deleteToken(String token){
        // 解析token
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        redisUtil.delete(RedisKey.KEY_TOKEN + load.getUserId(), token);
    }

    /**
     * 检查token是否合法
     *
     * @param token token
     */
    public boolean checkToken(String token) {
        // 解析token
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        // 取出user:token:<token>中的token
        String keyValue = (String) redisUtil.get(RedisKey.KEY_TOKEN + load.getUserId());
        return keyValue != null && !"".equals(keyValue) && keyValue.equals(token) && getValue(token) != null;
    }

    /**
     * 检查是否重复登录
     *
     * @param userId 用户ID
     */
    public boolean checkReLogin(String userId){
        String keyValue = (String) redisUtil.get(RedisKey.KEY_TOKEN + userId);
        if (keyValue == null || "".equals(keyValue)) {
            // 未重复登录
            return false;
        } else {
            // 重复登录, 删除原值并更新(更新操作外部需要进行, 这里删除旧的token即可)
            // 删除 <oldToken>
            redisUtil.delete(keyValue);
            return true;
        }
    }

    public void flushExpire(String key) {
        this.flushExpire(key, hemsProperties.getTokenExpire());
    }

    public void flushExpire(String key, long expire) {
        redisUtil.flushExpire(key, expire);
    }
}
