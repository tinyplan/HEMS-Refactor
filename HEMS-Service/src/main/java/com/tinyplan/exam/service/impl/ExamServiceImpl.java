package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ExamMapper;
import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {

    @Resource(name = "examMapper")
    private ExamMapper examMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Override
    public Map<String, List<Exam>> getExam() {
        Map<String, List<Exam>> map = new HashMap<>();
        map.put("examInfo", examMapper.getExam());
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
    }
}
