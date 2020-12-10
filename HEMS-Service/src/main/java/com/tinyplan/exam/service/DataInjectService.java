package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.form.EnrollApplyForm;
import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.form.UpdateUserDetailForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.EnrollApply;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.vo.ExamDetailVO;

public interface DataInjectService {

    User injectUser(RegisterForm form);

    CandidateDetail injectCandidateDetail(UpdateUserDetailForm form);

    CandidateDetail injectCandidateDetail(RegisterForm form);

    ExamDetail injectExamDetail(AddExamInfoForm form);

    ExamDetailVO injectExamDetailVO(ExamDetail examDetail);

    EnrollApply injectEnrollApply(EnrollApplyForm form);

}
