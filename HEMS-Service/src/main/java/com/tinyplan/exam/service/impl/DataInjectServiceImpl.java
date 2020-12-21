package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.type.StatusUtil;
import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import com.tinyplan.exam.entity.form.*;
import com.tinyplan.exam.entity.po.*;
import com.tinyplan.exam.entity.pojo.type.ApplyStatus;
import com.tinyplan.exam.entity.pojo.type.SiteStatus;
import com.tinyplan.exam.entity.vo.*;
import com.tinyplan.exam.service.DataInjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataInjectServiceImpl implements DataInjectService {

    @Override
    public User injectUser(RegisterForm form) {
        User user = new User();
        user.setAccountName(form.getUsername());
        // 这里先设置为原始密码
        user.setPassword(form.getPassword());
        // 只有学生可以注册
        user.setRoleId("r1003");
        return user;
    }

    @Override
    public CandidateDetail injectCandidateDetail(RegisterForm form) {
        CandidateDetail detail = new CandidateDetail();
        detail.setContact(form.getTelephone());
        detail.setEmail(form.getEmail());
        // 设置默认头像
        detail.setAvatar("default_admin_avatar.png");
        return detail;
    }

    @Override
    public CandidateDetail injectCandidateDetail(UpdateUserDetailForm form) {
        CandidateDetail detail = new CandidateDetail();
        detail.setRealName(form.getRealName());
        detail.setIdCard(form.getIdCard());
        detail.setGender("男".equals(form.getGender()) ? 1 : 0);
        detail.setContact(form.getContact());
        detail.setEmail(form.getEmail());
        detail.setEduBack(form.getEduBack());
        detail.setHomeAddress(form.getHomeAddress());
        return detail;
    }

    @Override
    public ExamDetail injectExamDetail(AddExamInfoForm form) {
        ExamDetail detail = new ExamDetail();
        detail.setExamId(form.getExamId());
        detail.setLevel(form.getLevel());
        detail.setExamName(form.getExamName());
        detail.setEnrollStart(form.getEnrollStart());
        detail.setEnrollEnd(form.getEnrollEnd());
        detail.setExamStart(form.getExamStart());
        detail.setExamEnd(form.getExamEnd());
        detail.setCapacity(form.getCapacity());
        // 设置初始剩余人数
        detail.setRemain(form.getCapacity());
        detail.setFee(form.getFee());
        detail.setPassLine(form.getPassLine());
        return detail;
    }

    @Override
    public ExamDetailVO injectExamDetailVO(ExamDetail examDetail) {
        ExamDetailVO detailVO = new ExamDetailVO();
        detailVO.setExamNo(examDetail.getExamNo());
        detailVO.setLevel(StatusUtil.getExamLevel(examDetail.getLevel()).getDescription());
        detailVO.setExamName(examDetail.getExamName());
        detailVO.setEnrollStart(examDetail.getEnrollStart());
        detailVO.setEnrollEnd(examDetail.getEnrollEnd());
        detailVO.setExamStart(examDetail.getExamStart());
        detailVO.setExamEnd(examDetail.getExamEnd());
        detailVO.setStatus(StatusUtil.getExamStatus(examDetail.getStatus()).getDescription());
        detailVO.setInterval(examDetail.getInterval());
        detailVO.setFee(examDetail.getFee());
        return detailVO;
    }

    @Override
    public EnrollApply injectEnrollApply(EnrollApplyForm form) {
        EnrollApply apply = new EnrollApply();
        apply.setCandidateId(form.getCandidateId());
        apply.setExamNo(form.getExamNo());
        apply.setDescription(form.getDescription());
        apply.setStatus(ApplyStatus.AUDIT.getCode());
        return apply;
    }

    @Override
    public SystemEnrollVO injectSystemEnrollVO(Enroll enroll, ExamDetail examDetail) {
        SystemEnrollVO result = new SystemEnrollVO();
        result.setCandidateId(enroll.getCandidateId());
        result.setRealName(enroll.getRealName());
        result.setGender(enroll.getGender() == 1 ? "男" : "女");
        result.setContact(enroll.getContact());
        result.setEmail(enroll.getEmail());
        result.setEduBack(enroll.getEduBack());
        result.setHomeAddress(enroll.getHomeAddress());

        result.setEnrollId(enroll.getEnrollId());
        result.setExamName(examDetail.getExamName());
        result.setLevel(StatusUtil.getExamLevel(examDetail.getLevel()).getDescription());
        result.setStatus(StatusUtil.getEnrollStatus(enroll.getStatus()).getDescription());
        return result;
    }

    @Override
    public Enroll injectEnroll(UpdateCandidateEnrollForm form) {
        Enroll enroll = new Enroll();
        enroll.setEnrollId(form.getEnrollId());
        enroll.setRealName(form.getRealName());
        if ("男".equals(form.getGender())) {
            enroll.setGender(1);
        } else if ("女".equals(form.getGender())) {
            enroll.setGender(0);
        }
        enroll.setContact(form.getConcat());
        enroll.setEmail(form.getEmail());
        enroll.setEduBack(form.getEduBack());
        enroll.setHomeAddress(form.getHomeAddress());
        return enroll;
    }

    @Override
    public EnrollApplyVO injectEnrollApplyVO(EnrollApply enrollApply, CandidateDetail candidateDetail) {
        EnrollApplyVO result = new EnrollApplyVO();
        result.setApplyId(enrollApply.getApplyId());
        result.setCandidateId(enrollApply.getCandidateId());
        result.setDescription(enrollApply.getDescription());
        result.setRealName(candidateDetail.getRealName());
        return result;
    }

    @Override
    public PortalEnrollVO injectPortalEnrollVO(Enroll enroll, ExamDetail examDetail) {
        PortalEnrollVO portalEnrollVO = new PortalEnrollVO();
        PortalCandidateInfoVO candidateInfoVO = new PortalCandidateInfoVO();
        candidateInfoVO.setEnrollId(enroll.getEnrollId());
        candidateInfoVO.setId(enroll.getCandidateId());
        candidateInfoVO.setRealName(enroll.getRealName());
        candidateInfoVO.setIdCard(enroll.getIdCard());
        candidateInfoVO.setCandidateNo(enroll.getEnrollId());
        candidateInfoVO.setGender(enroll.getGender() == 1 ? "男" : "女");
        candidateInfoVO.setContact(enroll.getContact());
        candidateInfoVO.setEmail(enroll.getEmail());
        candidateInfoVO.setEduBack(enroll.getEduBack());
        candidateInfoVO.setHomeAddress(enroll.getHomeAddress());
        candidateInfoVO.setStatus(StatusUtil.getEnrollStatus(enroll.getStatus()).getDescription());
        portalEnrollVO.setCandidateInfo(candidateInfoVO);

        portalEnrollVO.setExamInfo(this.injectExamDetailVO(examDetail));
        return portalEnrollVO;
    }

    @Override
    public ExamSessionVO injectExamSession(ExamDetail examDetail) {
        ExamSessionVO sessionVO = new ExamSessionVO();
        sessionVO.setExamNo(examDetail.getExamNo());
        sessionVO.setExamName(
                StatusUtil.getExamLevel(examDetail.getLevel()).getDescription() + examDetail.getExamName());
        sessionVO.setInterval(examDetail.getInterval());
        sessionVO.setStatus(StatusUtil.getExamStatus(examDetail.getStatus()).getDescription());

        String[] tmp1 = examDetail.getExamStart().split(" ");
        String[] tmp2 = examDetail.getExamEnd().split(" ");
        sessionVO.setExamDate(tmp1[0]);
        sessionVO.setExamStart(tmp1[1]);
        sessionVO.setExamEnd(tmp2[1]);
        return sessionVO;
    }

    @Override
    public Site injectSite(SiteInfoDTO dto) {
        Site site = new Site();
        site.setBuilding(dto.getBuilding().toUpperCase());
        site.setFloor(dto.getFloor());
        site.setRoom(dto.getRoom().toLowerCase());
        site.setCapacity(dto.getCapacity());
        site.setStatus(SiteStatus.AVAILABLE.getCode());
        return site;
    }

    public PortalScoreInfoVO injectPortalScoreInfoVO(Score score, ExamDetail examDetail) {
        PortalScoreInfoVO result = new PortalScoreInfoVO();
        result.setExamId(examDetail.getExamId());
        result.setExamNo(score.getExamNo());
        result.setEnrollId(score.getCandidateNo());
        result.setLevel(StatusUtil.getExamLevel(examDetail.getLevel()).getDescription());
        result.setExamName(examDetail.getExamName());
        result.setExamStart(examDetail.getExamStart());
        result.setExamEnd(examDetail.getExamEnd());
        result.setScore(score.getScore());
        result.setPass(score.getPass() == 1 ? "合格" : "不合格");
        return result;
    }
}
