package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.dao.ExamMapper;
import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.service.ExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class ExamController {

    @Resource(name = "examServiceImpl")
    private ExamService examService;

    @GetMapping("/exam")
    @Authorization
    public ApiResult<Map<String, List<List<Exam>>>> getExam(){
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examService.getExam());
    }

}
