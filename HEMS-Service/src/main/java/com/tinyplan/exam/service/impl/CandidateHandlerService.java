package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.properties.QiniuProperties;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.CandidateDetailVO;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.service.UserHandlerService;
import com.tinyplan.exam.service.factory.UserHandlerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CandidateHandlerService implements UserHandlerService, InitializingBean {

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Resource(name = "qiniuProperties")
    private QiniuProperties qiniuProperties;

    @Override
    public User getUser(String username) {
        return candidateMapper.getCandidateByUsername(username);
    }

    @Override
    public DetailVO getUserDetail(User user) {
        CandidateDetailVO detail = new CandidateDetailVO(candidateMapper.getCandidateDetail(user.getId()));
        detail.copyValueFromUser(user);
        detail.setAvatar(qiniuProperties.getDomain() + detail.getAvatar());
        return detail;
    }

    @Override
    public DetailVO convertDetail(Object rawData) {
        return (CandidateDetailVO) rawData;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserHandlerFactory.registerHandlerService(UserType.CANDIDATE, this);
    }
}
