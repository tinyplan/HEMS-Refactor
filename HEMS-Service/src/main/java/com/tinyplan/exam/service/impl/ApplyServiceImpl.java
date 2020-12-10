package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.dao.EnrollApplyMapper;
import com.tinyplan.exam.entity.po.EnrollApply;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.service.ApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource(name = "enrollApplyMapper")
    private EnrollApplyMapper enrollApplyMapper;

    @Override
    public void addEnrollApply(EnrollApply enrollApply) {
        String prefix = PrefixUtil.getObjectPrefix(ObjectType.ENROLL_APPLY);
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String maxId = String.valueOf(CommonUtil.checkMaxId(enrollApplyMapper.getMaxId()) + 1);
        enrollApply.setApplyId(PrefixUtil.generateId(prefix, date, maxId));
        enrollApplyMapper.insertEnrollApply(enrollApply);
    }

}
