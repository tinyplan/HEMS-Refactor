package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.form.UpdateUserDetailForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.User;

public interface DataInjectService {

    User injectUser(RegisterForm form);

    CandidateDetail injectCandidateDetail(UpdateUserDetailForm form);

    CandidateDetail injectCandidateDetail(RegisterForm form);

    ExamDetail injectExamDetail(AddExamInfoForm form);

}
