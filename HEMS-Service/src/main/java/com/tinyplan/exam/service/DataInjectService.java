package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import com.tinyplan.exam.entity.form.*;
import com.tinyplan.exam.entity.po.*;
import com.tinyplan.exam.entity.vo.*;

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

    PortalEnrollVO injectPortalEnrollVO(Enroll enroll, ExamDetail examDetail);

    ExamSessionVO injectExamSession(ExamDetail examDetail);

    Site injectSite(SiteInfoDTO dto);

    PortalScoreInfoVO injectPortalScoreInfoVO(Score score, ExamDetail examDetail);

    Invigilator injectInvigilator(AddInvigilatorForm form);

    Invigilator injectInvigilator(UpdateInvigilatorForm form);

}
