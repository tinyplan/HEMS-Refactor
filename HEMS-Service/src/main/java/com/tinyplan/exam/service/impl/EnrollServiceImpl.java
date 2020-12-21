package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.JwtUtil;
import com.tinyplan.exam.common.utils.PaginationUtil;
import com.tinyplan.exam.dao.EnrollMapper;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ScoreMapper;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.Score;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.EnrollStatus;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalEnrollVO;
import com.tinyplan.exam.entity.vo.SystemEnrollVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.EnrollService;
import com.tinyplan.exam.service.schedule.EnrollStatusJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EnrollServiceImpl implements EnrollService {

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "enrollStatusJobServiceImpl")
    private EnrollStatusJobService enrollStatusJobService;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Resource(name = "scoreMapper")
    private ScoreMapper scoreMapper;

    @Resource(name = "enrollMapper")
    private EnrollMapper enrollMapper;

    /**
     * 考生报名
     *
     * @param examId          报考的考试
     * @param candidateDetail 考生报考填写的详细信息
     * @return 此次报名生成的报名序列号
     */
    @Override
    public String candidateEnroll(String examId, CandidateDetail candidateDetail) {
        // 查询是否有examId对应的正在报名的考试
        ExamDetail examDetail = examDetailMapper.getExamDetailByIdAndStatus(examId, ExamStatus.DURING_ENROLL.getCode());
        if (examDetail == null) {
            throw new BusinessException(ResultStatus.RES_EXAM_NOT_DURING_ENROLL);
        }
        // 检查该场考试是否人数已满
        if (examDetail.getRemain() <= 0) {
            throw new BusinessException(ResultStatus.RES_EXAM_CAPACITY_OVERFLOW);
        }
        // 检查该考生是否有考试资格(成绩表)
        boolean hasQualification = false;
        if (examDetail.getLevel() != 1) {
            // 查询该考生通过的考试
            List<Score> scoreList = scoreMapper.getScoreByCandidateId(candidateDetail.getId(), 1);
            for (Score score : scoreList) {
                if (score.getLevel() > examDetail.getLevel()) {
                    hasQualification = true;
                    break;
                }
            }
        } else {
            // 报考初级考试不用判断, 直接通过
            hasQualification = true;
        }
        if (hasQualification) {
            // 查询报名信息中报名成功且还未考试完成的(这里通过报名的状态进行判断, 为报名信息新增一种状态)
            List<Enroll> enrollList = enrollMapper.getLivedEnrollByCandidateId(candidateDetail.getId());
            String examNo = examDetail.getExamNo();
            // 检查冲突(报考同一场考试/考试时间冲突)
            for (Enroll enroll : enrollList) {
                if (examNo.equals(enroll.getExamNo())) {
                    // 报考了同一场考试
                    throw new BusinessException(ResultStatus.RES_ENROLL_SAME_EXAM);
                }
                ExamDetail curDetail = examDetailMapper.getExamDetailByNo(enroll.getExamNo());
                Date examStart = DateUtil.parse(curDetail.getExamStart(), "yyyy-MM-dd HH:mm");
                Date examEnd = DateUtil.parse(curDetail.getExamEnd(), "yyyy-MM-dd HH:mm");
                Date enrollExamStart = DateUtil.parse(examDetail.getExamStart(), "yyyy-MM-dd HH:mm");
                Date enrollExamEnd = DateUtil.parse(examDetail.getExamEnd(), "yyyy-MM-dd HH:mm");
                // 判断时间是否合适
                boolean isSuit = (DateUtil.between(enrollExamEnd, examStart, DateUnit.MINUTE, false) > 0) &&
                        (DateUtil.between(examEnd, enrollExamStart, DateUnit.MINUTE, false) > 0);
                if (!isSuit) {
                    throw new BusinessException(ResultStatus.RES_ENROLL_TIME_CONFLICT);
                }
            }
            // 无冲突, 插入表中
            // o(≧口≦)o 终于判断完了, 好长
            Enroll enroll = new Enroll();
            // 生成报名ID(考试序号 + 考生ID)
            String enrollId = String.join("_", examDetail.getExamNo(), candidateDetail.getId());
            enroll.setEnrollId(enrollId);
            enroll.setExamNo(examDetail.getExamNo());
            enroll.setStatus(EnrollStatus.WAITING_PAY.getCode());
            // 添加报考信息
            enrollMapper.insertEnroll(enroll, candidateDetail);
            // 考试剩余人数减1
            Integer remain = examDetailMapper.getExamRemain(examNo);
            // 再次检查人数是否已满
            if (remain <= 0) {
                throw new BusinessException(ResultStatus.RES_EXAM_CAPACITY_OVERFLOW);
            }
            examDetailMapper.updateExamRemain(examNo, remain - 1);
            enrollStatusJobService.addJobs(enroll.getEnrollId(), examDetail.getEnrollEnd());
            return enrollId;
        } else {
            throw new BusinessException(ResultStatus.RES_ENROLL_HAVE_NOT_EXAM_QUALIFICATION);
        }
    }

    @Override
    public void payFees(String enrollId) {
        enrollMapper.updateEnrollStatus(enrollId, EnrollStatus.ENROLL_SUCCESS.getCode());
    }

    @Override
    public Pagination<SystemEnrollVO> getEnrollForSystemByCondition(Integer pageSize, String type, String content) {
        // 查询出来的报名信息
        List<Enroll> enrollList = null;
        // 符合信息的考试信息
        List<ExamDetail> enrollingList = null;
        Integer examDetailStatus = ExamStatus.DURING_ENROLL.getCode();
        switch (type) {
            case "realName":
                // 获取正在报名的考试列表
                // enrollingList = examDetailMapper.getExamDetailByStatus(examDetailStatus);
                enrollingList = examDetailMapper.getExamDetailByCondition(null, null, examDetailStatus);
                enrollList = enrollMapper.getEnrollByCondition(enrollingList, content, null);
                break;
            case "examName":
                enrollingList = examDetailMapper.getExamDetailByCondition(content, null, examDetailStatus);
                if (enrollingList == null || enrollingList.size() == 0) {
                    break;
                }
                enrollList = enrollMapper.getEnrollByCondition(enrollingList, null, null);
                break;
            case "level":
                enrollingList = examDetailMapper.getExamDetailByCondition(null, Integer.valueOf(content), examDetailStatus);
                if (enrollingList == null || enrollingList.size() == 0) {
                    break;
                }
                enrollList = enrollMapper.getEnrollByCondition(enrollingList, null, null);
                break;
            case "status":
                enrollingList = examDetailMapper.getExamDetailByCondition(null, null, examDetailStatus);
                enrollList = enrollMapper.getEnrollByCondition(enrollingList, null, Integer.valueOf(content));
                break;
            default:
                enrollList = new ArrayList<>();
        }
        Pagination<SystemEnrollVO> pagination = new Pagination<>();
        // 若考试情况为空, 直接返回空信息
        if (enrollingList == null || enrollingList.size() == 0) {
            pagination.setTotal(0);
            pagination.setTableData(PaginationUtil.getLogicPagination(new ArrayList<>(), pageSize));
        } else {
            List<SystemEnrollVO> systemEnrollVOList = new ArrayList<>(enrollList.size());
            for (Enroll enroll : enrollList) {
                systemEnrollVOList.add(dataInjectService.injectSystemEnrollVO(enroll,
                        examDetailMapper.getExamDetailByNo(enroll.getExamNo())));
            }
            pagination.setTotal(enrollList.size());
            pagination.setTableData(PaginationUtil.getLogicPagination(systemEnrollVOList, pageSize));
        }
        return pagination;
    }

    @Override
    public void updateCandidateEnroll(Enroll enroll) {
        enrollMapper.updateCandidateEnroll(enroll);
    }

    @Override
    public Pagination<PortalEnrollVO> getEnrollForPortalWithPagination(Integer pageSize, String candidateId, Integer status) {
        List<Enroll> enrollList = null;
        if (EnrollStatus.ENROLL_SUCCESS.getCode().equals(status)){
            // 查询报名成功的信息
            enrollList = enrollMapper.getSuccessEnrollByCandidateId(candidateId);
        } else {
            enrollList = enrollMapper.getEnrollByCandidateId(candidateId, status);
        }
        List<PortalEnrollVO> dataList = new ArrayList<>(enrollList.size());
        for (Enroll enroll : enrollList) {
            dataList.add(dataInjectService.injectPortalEnrollVO(
                    enroll, examDetailMapper.getExamDetailByNo(enroll.getExamNo())));
        }
        Pagination<PortalEnrollVO> pagination = new Pagination<>();
        pagination.setTableData(PaginationUtil.getLogicPagination(dataList, pageSize));
        pagination.setTotal(pagination.getTableData().size());
        return pagination;
    }

    @Override
    public PortalEnrollVO getEnrollForPortal(String token, String enrollId) {
        JwtDataLoad dataLoad = new JwtDataLoad(JwtUtil.verify(token));
        Enroll enroll = enrollMapper.getEnrollByCandidateIdAndEnrollId(dataLoad.getUserId(), enrollId);
        return dataInjectService.injectPortalEnrollVO(enroll, examDetailMapper.getExamDetailByNo(enroll.getExamNo()));
    }
}
