package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.type.StatusUtil;
import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.form.EnrollApplyForm;
import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.form.UpdateUserDetailForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.EnrollApply;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.type.ApplyStatus;
import com.tinyplan.exam.entity.vo.ExamDetailVO;
import com.tinyplan.exam.service.DataInjectService;
import org.springframework.stereotype.Service;

import javax.crypto.spec.DESedeKeySpec;

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
}
