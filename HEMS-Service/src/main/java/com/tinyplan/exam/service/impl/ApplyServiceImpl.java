package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.common.utils.PaginationUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.dao.EnrollApplyMapper;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.EnrollApply;
import com.tinyplan.exam.entity.pojo.type.ApplyStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.vo.EnrollApplyVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.ApplyService;
import com.tinyplan.exam.service.DataInjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "enrollApplyMapper")
    private EnrollApplyMapper enrollApplyMapper;

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Override
    public void addEnrollApply(EnrollApply enrollApply) {
        String prefix = PrefixUtil.getObjectPrefix(ObjectType.ENROLL_APPLY);
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String maxId = String.valueOf(CommonUtil.checkMaxId(enrollApplyMapper.getMaxId()) + 1);
        enrollApply.setApplyId(PrefixUtil.generateId(prefix, date, maxId));
        enrollApplyMapper.insertEnrollApply(enrollApply);
    }

    @Override
    public Pagination<EnrollApplyVO> getAllEnrollApply(Integer pageSize) {
        List<EnrollApply> enrollApplyList = enrollApplyMapper.getEnrollApplyByStatus(ApplyStatus.AUDIT.getCode());
        List<EnrollApplyVO> dataList = new ArrayList<>(enrollApplyList.size());
        for (EnrollApply apply : enrollApplyList) {
            dataList.add(dataInjectService.injectEnrollApplyVO(apply, candidateMapper.getCandidateDetail(apply.getCandidateId())));
        }
        Pagination<EnrollApplyVO> pagination = new Pagination<>();
        pagination.setTotal(enrollApplyList.size());
        pagination.setTableData(PaginationUtil.getLogicPagination(dataList, pageSize));
        return pagination;
    }

    @Override
    public void acceptEnrollApply(String applyId) {
        EnrollApply enrollApply = new EnrollApply();
        enrollApply.setApplyId(applyId);
        enrollApply.setFeedback("申请已通过");
        enrollApply.setStatus(ApplyStatus.PASS.getCode());
        enrollApplyMapper.updateEnrollApply(enrollApply);
    }

    @Override
    public void rejectEnrollApply(String applyId, String feedback) {
        EnrollApply enrollApply = new EnrollApply();
        enrollApply.setApplyId(applyId);
        enrollApply.setFeedback(feedback);
        enrollApply.setStatus(ApplyStatus.REJECT.getCode());
        enrollApplyMapper.updateEnrollApply(enrollApply);
    }
}
