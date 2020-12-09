package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.service.EnrollService;
import org.springframework.stereotype.Service;

@Service
public class EnrollServiceImpl implements EnrollService {

    /**
     * 考生报名
     *
     * @param examId 报考的考试
     * @param detail 考生报考填写的详细信息
     * @return 此次报名生成的报名序列号
     */
    @Override
    public String candidateEnroll(String examId, CandidateDetail detail) {
        // 查询是否有examId对应的正在报名的考试

        // 检查该考生是否有考试资格(成绩表)

        // 插入报名表中

        return null;
    }
}
