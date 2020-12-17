package com.tinyplan.exam.common.service;

public interface TokenService {

    String generateToken(String userId, String roleId);

    Object getValue(String token);

    boolean setValue(String token, Object value);

    void deleteToken(String token);

    boolean checkToken(String token);

    boolean checkReLogin(String userId);

    void flushExpire(String key);

    void flushExpire(String key, long expire);

}
