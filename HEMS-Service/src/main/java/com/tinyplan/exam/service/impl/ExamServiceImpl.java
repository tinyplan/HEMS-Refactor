package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.common.utils.PaginationUtil;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ExamMapper;
import com.tinyplan.exam.entity.dto.UpdateJobOrder;
import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.ExamLevel;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.vo.ExamDetailVO;
import com.tinyplan.exam.entity.vo.ExamSessionVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.ExamService;
import com.tinyplan.exam.service.schedule.ExamStatusJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import static com.tinyplan.exam.common.utils.CommonUtil.later;

@Service
public class ExamServiceImpl implements ExamService {

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "examStatusJobServiceImpl")
    private ExamStatusJobService examStatusJobService;

    @Resource(name = "examMapper")
    private ExamMapper examMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Override
    public Map<String, List<List<Exam>>> getExam() {
        Map<String, List<List<Exam>>> map = new HashMap<>();
        List<List<Exam>> result = new ArrayList<>();
        for (ExamLevel level : ExamLevel.values()) {
            result.add(examMapper.getExamByLevel(level.getCode()));
        }
        map.put("examInfo", result);
        return map;
    }

    @Override
    public Map<String, Object> getExamByLevel(Integer level) {
        List<Exam> examList = examMapper.getExamByLevel(level);
        String[] nameList = new String[examList.size()];
        Map<String, Exam> detailList = new HashMap<>();
        for (int i = 0; i < examList.size(); i++) {
            String examName = examList.get(i).getExamName();
            nameList[i] = examName;
            detailList.put(examName, examList.get(i));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("nameList", nameList);
        result.put("detailList", detailList);
        return result;
    }

    @Override
    @Transactional
    public void addExamDetail(ExamDetail detail) {
        // 判断有无存活中的相同考试
        List<ExamDetail> livedExamDetailList = examDetailMapper.getLivedExamDetail(detail.getExamId());
        if (livedExamDetailList.size() != 0) {
            throw new BusinessException(ResultStatus.RES_EXAM_DETAIL_HAS_LIVED_EXAM);
        }
        // 生成该场考试的序列号
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String maxId = String.valueOf(CommonUtil.checkMaxId(examDetailMapper.getMaxId()) + 1);
        detail.setExamNo(String.join("_", detail.getExamId(), date, maxId));
        // 设置考试时长
        Date examStart = DateUtil.parse(detail.getExamStart());
        Date examEnd = DateUtil.parse(detail.getExamEnd());
        detail.setInterval((int)DateUtil.between(examStart, examEnd, DateUnit.MINUTE, false));
        // 设置考试状态
        detail.setStatus(ExamStatus.BEFORE_ENROLL.getCode());
        // 插入表中
        examDetailMapper.insertExamDetail(detail);
        // TODO 设置4个定时任务
        examStatusJobService.addJobs(detail);
    }

    @Override
    public Pagination<ExamDetailVO> getExam(Integer pageSize) {
        List<ExamDetailVO> result = new ArrayList<>();
        List<ExamDetail> allLivedExamList = examDetailMapper.getAllLivedExamDetail();
        for (ExamDetail examDetail : allLivedExamList) {
            result.add(dataInjectService.injectExamDetailVO(examDetail));
        }
        Pagination<ExamDetailVO> pagination = new Pagination<>();
        pagination.setTotal(result.size());
        pagination.setTableData(PaginationUtil.getLogicPagination(result, pageSize));
        return pagination;
    }

    @Override
    public void updateExamStatus(String examNo, Integer status) {
        ExamDetail examDetail = examDetailMapper.getExamDetailByNo(examNo);
        // 考试状态只能向后改动, 且只能改动一位
        if (status - examDetail.getStatus() == 1) {
            examDetailMapper.updateExamStatus(examNo, status);
        } else {
            throw new BusinessException(ResultStatus.RES_UPDATE_ILLEGAL_EXAM_STATUS);
        }
    }

    @Override
    public List<ExamSessionVO> getAllExamSession() {
        List<ExamDetail> examDetailList =
                examDetailMapper.getExamDetailBeforeQueryStatus(ExamStatus.BEFORE_EXAM.getCode());
        List<ExamSessionVO> sessionVOList = new ArrayList<>(examDetailList.size());
        for (ExamDetail examDetail : examDetailList) {
            sessionVOList.add(dataInjectService.injectExamSession(examDetail));
        }
        return sessionVOList;
    }

    @Override
    public void updateExamSession(String examNo, String examStart, String examEnd) {
        ExamDetail examDetail = examDetailMapper.getExamDetailByNo(examNo);
        if (examDetail == null) {
            throw new BusinessException(ResultStatus.RES_NOT_EXIST_EXAM);
        }
        if (examDetail.getStatus() >= ExamStatus.BEFORE_EXAM.getCode()) {
            throw new BusinessException(ResultStatus.RES_UPDATE_EXAM_FAIL);
        }
        Date enrollEnd = DateUtil.parse(examDetail.getEnrollEnd());
        Date examStartDate = DateUtil.parse(examStart);
        Date examEndDate = DateUtil.parse(examEnd);
        if (!(later(new Date(), examStartDate) && later(enrollEnd, examStartDate) && later(examStartDate, examEndDate))) {
            throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
        }
        int interval = (int) DateUtil.between(examStartDate, examEndDate, DateUnit.MINUTE, false);

        ExamDetail newDetail = new ExamDetail();
        newDetail.setExamNo(examNo);
        newDetail.setExamStart(examStart);
        newDetail.setExamEnd(examEnd);
        newDetail.setInterval(interval);
        // 更新考试信息
        Integer result = examDetailMapper.updateExamDetail(newDetail);
        // 更新定时任务执行的时间
        result += examStatusJobService.updateJob(
                new UpdateJobOrder(examNo, examDetail.getExamStart(), examStart, examDetail.getExamEnd(), examEnd));
        if (result != 3) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR);
        }
    }
}
