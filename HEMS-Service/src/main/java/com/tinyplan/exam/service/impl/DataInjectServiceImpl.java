package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.form.UpdateUserDetailForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.service.DataInjectService;
import org.springframework.stereotype.Service;

@Service
public class DataInjectServiceImpl implements DataInjectService {

    @Override
    public User injectUser(RegisterForm form) {
        User user = new User();
        user.setAccountName(form.getUsername());
        // 这里先设置为原始密码
        user.setPassword(form.getPassword());
        // 只有学生可以注册
        user.setRoleId("r1003");
        return user;
    }

    @Override
    public CandidateDetail injectCandidateDetail(RegisterForm form) {
        CandidateDetail detail = new CandidateDetail();
        detail.setContact(form.getTelephone());
        detail.setEmail(form.getEmail());
        // 设置默认头像
        detail.setAvatar("default_admin_avatar.png");
        return detail;
    }

    @Override
    public CandidateDetail injectCandidateDetail(UpdateUserDetailForm form) {
        CandidateDetail detail = new CandidateDetail();
        detail.setRealName(form.getRealName());
        detail.setGender("男".equals(form.getGender()) ? 1 : 0);
        detail.setContact(form.getContact());
        detail.setEmail(form.getEmail());
        detail.setEduBack(form.getEduBack());
        detail.setHomeAddress(form.getHomeAddress());
        return detail;
    }
}