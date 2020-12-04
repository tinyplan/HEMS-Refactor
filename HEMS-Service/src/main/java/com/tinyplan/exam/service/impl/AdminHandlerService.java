package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.properties.QiniuProperties;
import com.tinyplan.exam.dao.AdminMapper;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.AdminDetailVO;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.service.UserHandlerService;
import com.tinyplan.exam.service.factory.UserHandlerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminHandlerService implements UserHandlerService, InitializingBean {

    @Resource(name = "adminMapper")
    private AdminMapper adminMapper;

    @Resource(name = "qiniuProperties")
    private QiniuProperties qiniuProperties;

    @Override
    public User getUser(String username) {
        return adminMapper.getAdminByUsername(username);
    }

    @Override
    public DetailVO getUserDetail(User user) {
        AdminDetailVO detail = new AdminDetailVO();
        detail.copyValueFromUser(user);
        detail.setAvatar(qiniuProperties.getDomain() + detail.getAvatar());
        return detail;
    }

    @Override
    public DetailVO convertDetail(Object rawData) {
        return (AdminDetailVO) rawData;
    }

    @Override
    public void updatePassword(String userId, String newPassword) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserHandlerFactory.registerHandlerService(UserType.EDU_ADMIN, this);
        UserHandlerFactory.registerHandlerService(UserType.SYSTEM_ADMIN, this);
    }
}
