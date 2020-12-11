package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.form.*;
import com.tinyplan.exam.entity.po.*;
import com.tinyplan.exam.entity.vo.EnrollApplyVO;
import com.tinyplan.exam.entity.vo.ExamDetailVO;
import com.tinyplan.exam.entity.vo.SystemEnrollVO;

public interface DataInjectService {

    User injectUser(RegisterForm form);

    CandidateDetail injectCandidateDetail(UpdateUserDetailForm form);

    CandidateDetail injectCandidateDetail(RegisterForm form);

    ExamDetail injectExamDetail(AddExamInfoForm form);

    ExamDetailVO injectExamDetailVO(ExamDetail examDetail);

    EnrollApply injectEnrollApply(EnrollApplyForm form);

    SystemEnrollVO injectSystemEnrollVO(Enroll enroll, ExamDetail examDetail);

    Enroll injectEnroll(UpdateCandidateEnrollForm form);

    EnrollApplyVO injectEnrollApplyVO(EnrollApply enrollApply, CandidateDetail candidateDetail);

}
