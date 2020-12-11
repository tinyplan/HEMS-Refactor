package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.UpdateCandidateEnrollForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.SystemEnrollVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.EnrollService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/enroll")
public class EnrollController {

    @Resource(name = "enrollServiceImpl")
    private EnrollService enrollService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    /**
     * 查询报考信息
     *      返回的报考(报名)信息都是该考试正在报名中
     * @param pageSize 页面容量
     * @param queryType 查询类型
     * @param queryContent 查询内容
     */
    @GetMapping("")
    @Authorization
    public ApiResult<Pagination<SystemEnrollVO>> getEnrollDetail(@RequestParam("pageSize") Integer pageSize,
                                                                 @RequestParam("type") String queryType,
                                                                 @RequestParam("key") String queryContent) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                enrollService.getEnrollForSystemByCondition(pageSize, queryType, queryContent));
    }

    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateCandidateEnroll(@RequestBody UpdateCandidateEnrollForm form) {
        enrollService.updateCandidateEnroll(dataInjectService.injectEnroll(form));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
