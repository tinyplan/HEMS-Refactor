package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.service.UserHandlerService;
import com.tinyplan.exam.service.factory.UserHandlerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class InvigilatorHandlerService implements UserHandlerService, InitializingBean {

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public DetailVO getUserDetail(User user) {
        return null;
    }

    @Override
    public DetailVO convertDetail(Object rawData) {
        return null;
    }

    @Override
    public void updatePassword(String userId, String newPassword) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserHandlerFactory.registerHandlerService(UserType.INVIGILATOR, this);
    }
}
