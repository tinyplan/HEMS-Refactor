package com.tinyplan.exam.entity.pojo;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

/**
 * JWT 中自定义的数据负载部分
 */
public class JwtDataLoad {
    public static final String CLAIM_KEY_USER_ID = "userId";
    public static final String CLAIM_KEY_ROLE_ID = "roleId";
    private final String userId;
    private final String roleId;

    public JwtDataLoad(Map<String, Claim> claimMap) {
        this.userId = claimMap.get(CLAIM_KEY_USER_ID).asString();
        this.roleId = claimMap.get(CLAIM_KEY_ROLE_ID).asString();
    }

    public String getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

}
