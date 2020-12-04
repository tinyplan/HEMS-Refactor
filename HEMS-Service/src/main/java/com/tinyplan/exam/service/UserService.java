package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.AdminDetailVO;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;

import java.io.UnsupportedEncodingException;

public interface UserService {

    // 考生专用
    void register(User user, CandidateDetail detail);

    TokenVO login(String username, String password, UserType type);

    DetailVO getUserInfo(String token);

    // 考生专用
    void updateUserInfo(String token, CandidateDetail newDetail);

    void logout(String token);

}
