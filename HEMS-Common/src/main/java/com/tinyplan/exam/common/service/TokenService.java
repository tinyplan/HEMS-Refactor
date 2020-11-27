package com.tinyplan.exam.common.service;

import java.io.UnsupportedEncodingException;

public interface TokenService {

    String generateToken(String userId, String roleId) throws UnsupportedEncodingException;

    Object getValue(String token);

    boolean setValue(String token, Object value);

    void deleteToken(String token);

    boolean checkToken(String token);

    void flushExpire(String key);

    void flushExpire(String key, long expire);

}
