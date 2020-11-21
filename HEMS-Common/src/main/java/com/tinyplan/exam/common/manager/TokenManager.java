package com.tinyplan.exam.common.manager;

import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.common.utils.EncryptUtil;
import com.tinyplan.exam.common.utils.JWTUtil;
import com.tinyplan.exam.common.utils.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component("tokenManager")
public class TokenManager {

    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;
    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return token(若选择加密, 则会使用AES对称加密方式)
     */
    public String generateToken(String userId) throws UnsupportedEncodingException {
        Map<String , String> map = new HashMap<>();
        map.put("userId", userId);
        String token = JWTUtil.sign(map, hemsProperties.getTokenExpire());
        if (hemsProperties.isEncrypt()) {
            token = EncryptUtil.encryptByAES(token);
        }
        return token;
    }

    public Object getValue(String token) {
        return redisUtil.get(token);
    }

    public boolean setValue(String token, Object value) {
        return redisUtil.set(token, value);
    }

    /**
     * 删除token
     * @param token
     */
    public void deleteToken(String token){
        redisUtil.delete(token);
    }

    /**
     * 检查token是否存在
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        return getValue(token) != null;
    }

    public void flushExpire(String key) {
        this.flushExpire(key, hemsProperties.getTokenExpire());
    }

    public void flushExpire(String key, long expire) {
        redisUtil.flushExpire(key, expire);
    }
}
