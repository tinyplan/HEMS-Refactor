package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.ExamService;
import com.tinyplan.exam.service.ValidatorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource(name = "examServiceImpl")
    private ExamService examService;

    @Resource(name = "validatorServiceImpl")
    private ValidatorService validatorService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @GetMapping("/name")
    @Authorization
    public ApiResult<Map<String, Object>> getExamByLevel(@RequestParam("level") Integer level){
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examService.getExamByLevel(level));
    }

    @PostMapping("")
    @Authorization
    public ApiResult<Object> addExamDetail(@RequestBody AddExamInfoForm form){
        if (!validatorService.check(form)) {
            throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
        }
        examService.addExamInfo(dataInjectService.injectExamDetail(form));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
