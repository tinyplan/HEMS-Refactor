package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.SystemEnrollVO;

public interface EnrollService {

    String candidateEnroll(String examId, CandidateDetail detail);

    void payFees(String enrollId);

    Pagination<SystemEnrollVO> getEnrollForSystemByCondition(Integer pageSize, String type, String content);

    void updateCandidateEnroll(Enroll enroll);

}
