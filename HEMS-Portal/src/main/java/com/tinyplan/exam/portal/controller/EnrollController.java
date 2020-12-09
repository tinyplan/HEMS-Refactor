package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.EnrollForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.service.EnrollService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EnrollController {

    @Resource(name = "enrollServiceImpl")
    private EnrollService enrollService;

    @PostMapping("/enroll")
    @Authorization
    public ApiResult<Map<String, String>> enroll(@RequestBody EnrollForm form){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("enrollId", enrollService.candidateEnroll(form.getExamId(), form.getCandidateInfo()));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, resultMap);
    }

}
