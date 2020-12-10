package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.EnrollForm;
import com.tinyplan.exam.entity.form.PayForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.EnrollService;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/enroll")
public class EnrollController {

    @Resource(name = "enrollServiceImpl")
    private EnrollService enrollService;

    /**
     * 考生报名
     *
     * @param form 提交的报名表单
     */
    @PostMapping("")
    @Authorization
    public ApiResult<Map<String, String>> enroll(@RequestBody EnrollForm form) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("enrollId", enrollService.candidateEnroll(form.getExamId(), form.getCandidateInfo()));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, resultMap);
    }

    /**
     * 模拟考试费用的缴纳，请求成功则缴费成功
     */
    @PatchMapping("/pay")
    @Authorization
    public ApiResult<Object> pay(@RequestBody PayForm form) {
        enrollService.payFees(form.getEnrollId());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
