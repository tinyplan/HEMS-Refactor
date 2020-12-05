package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.AdminDetailVO;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface UserService {

    // 考生专用
    void register(User user, CandidateDetail detail);

    TokenVO login(String username, String password, UserType type);

    DetailVO getUserInfo(String token);

    // 考生专用
    void updateUserInfo(String token, CandidateDetail newDetail);

    void updatePassword(String token, String oldPassword, String newPassword);

    void certificate(HttpServletRequest request, MultipartFile front, MultipartFile back, String realName, String idCard);

    void logout(String token);

}
