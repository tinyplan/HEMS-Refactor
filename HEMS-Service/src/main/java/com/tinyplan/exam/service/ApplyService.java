package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.EnrollApply;
import com.tinyplan.exam.entity.vo.EnrollApplyVO;
import com.tinyplan.exam.entity.vo.Pagination;

public interface ApplyService {

    void addEnrollApply(EnrollApply enrollApply);

    Pagination<EnrollApplyVO> getAllEnrollApply(Integer pageSize);

    void acceptEnrollApply(String applyId);

    void rejectEnrollApply(String applyId, String feedback);

}
