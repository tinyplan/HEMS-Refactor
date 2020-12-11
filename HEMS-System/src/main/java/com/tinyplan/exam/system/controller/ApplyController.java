package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.UpdateApplyStatusForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.EnrollApplyVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.ApplyService;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Resource(name = "applyServiceImpl")
    private ApplyService applyService;

    @GetMapping("")
    @Authorization
    public ApiResult<Pagination<EnrollApplyVO>> getAllApply(@RequestParam("pageSize") Integer pageSize) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, applyService.getAllEnrollApply(pageSize));
    }

    @PatchMapping("/accept")
    @Authorization
    public ApiResult<Object> acceptApply(@RequestBody UpdateApplyStatusForm form) {
        applyService.acceptEnrollApply(form.getApplyId());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @PatchMapping("/refuse")
    @Authorization
    public ApiResult<Object> rejectApply(@RequestBody UpdateApplyStatusForm form) {
        applyService.rejectEnrollApply(form.getApplyId(), form.getFeedback());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
