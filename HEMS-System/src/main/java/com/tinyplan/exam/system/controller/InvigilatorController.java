package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.AddInvigilatorForm;
import com.tinyplan.exam.entity.form.UpdateInvigilatorForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.InvigilatorVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.InvigilatorService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/invigilator")
public class InvigilatorController {

    @Resource(name = "invigilatorServiceImpl")
    private InvigilatorService invigilatorService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @GetMapping("")
    @Authorization
    public ApiResult<Pagination<InvigilatorVO>> getAllInvigilator(@RequestParam("pageSize") Integer pageSize) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, invigilatorService.getAllInvigilator(pageSize));
    }

    @PostMapping("")
    @Authorization
    public ApiResult<Object> addInvigilator(@RequestBody AddInvigilatorForm form) {
        invigilatorService.addInvigilator(dataInjectService.injectInvigilator(form));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @PostMapping("/excel")
    @Authorization
    public ApiResult<Object> uploadInvigilator(HttpServletRequest request, @Param("file") MultipartFile file) {
        invigilatorService.uploadInvigilator(request, file);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateInvigilator(@RequestBody UpdateInvigilatorForm form) {
        invigilatorService.updateInvigilator(dataInjectService.injectInvigilator(form));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @DeleteMapping("")
    @Authorization
    public ApiResult<Object> deleteInvigilator(@RequestParam("invigilatorId") String invigilatorId) {
        invigilatorService.deleteInvigilator(invigilatorId);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
