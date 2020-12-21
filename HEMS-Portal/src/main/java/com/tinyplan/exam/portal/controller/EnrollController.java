package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.form.EnrollApplyForm;
import com.tinyplan.exam.entity.form.EnrollForm;
import com.tinyplan.exam.entity.form.PayForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalEnrollVO;
import com.tinyplan.exam.service.ApplyService;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.EnrollService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/enroll")
public class EnrollController {

    @Resource(name = "enrollServiceImpl")
    private EnrollService enrollService;

    @Resource(name = "applyServiceImpl")
    private ApplyService applyService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

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

    /**
     * 提交报名信息修改申请
     */
    @PostMapping("/apply")
    @Authorization
    public ApiResult<Object> submitApply(@RequestBody EnrollApplyForm form){
        applyService.addEnrollApply(dataInjectService.injectEnrollApply(form));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 获取报名信息(分页)
     *
     * @param pageSize 页面容量
     * @param candidateId 考生ID
     */
    @GetMapping("/pages")
    @Authorization
    public ApiResult<Pagination<PortalEnrollVO>> getEnroll(@RequestParam("pageSize") Integer pageSize,
                                                           @RequestParam("candidateId") String candidateId){
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                enrollService.getEnrollForPortalWithPagination(pageSize, candidateId, null));
    }

    @GetMapping("/type")
    @Authorization
    public ApiResult<Pagination<PortalEnrollVO>> getEnroll(@RequestParam("pageSize") Integer pageSize,
                                                           @RequestParam("candidateId") String candidateId,
                                                           @RequestParam("code") Integer code){
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                enrollService.getEnrollForPortalWithPagination(pageSize, candidateId, code));
    }

    /**
     * 获取报名信息
     *
     * @param request 请求体
     * @param enrollId 报名序号
     */
    @GetMapping("")
    @Authorization
    public ApiResult<PortalEnrollVO> getEnroll(HttpServletRequest request,
                                               @RequestParam("enrollId") String enrollId){
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                enrollService.getEnrollForPortal(RequestUtil.getToken(request), enrollId));
    }
}
