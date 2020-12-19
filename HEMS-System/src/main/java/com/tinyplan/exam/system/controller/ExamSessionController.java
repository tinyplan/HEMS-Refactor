package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.UpdateExamSessionForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.ExamSessionVO;
import com.tinyplan.exam.service.ExamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/session")
public class ExamSessionController {

    @Resource(name = "examServiceImpl")
    private ExamService examService;

    /**
     * 获取所有场次信息
     */
    @GetMapping("")
    @Authorization
    public ApiResult<List<ExamSessionVO>> getAllExamSession(){
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examService.getAllExamSession());
    }

    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateExamSession(@RequestBody UpdateExamSessionForm form){
        examService.updateExamSession(form.getExamNo(), form.getExamStart(), form.getExamEnd());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
