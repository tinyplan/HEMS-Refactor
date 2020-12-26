package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.UpdateCandidateSiteForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.SystemArrangeVO;
import com.tinyplan.exam.service.ExamArrangeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/arrange")
public class ArrangeController {

    @Resource(name = "examArrangeServiceImpl")
    private ExamArrangeService examArrangeService;

    @PostMapping("")
    @Authorization
    public ApiResult<Object> examArrange(@RequestBody Map<String, String> dataBody) {
        examArrangeService.arrange(dataBody.get("examNo"));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 查询准考证信息
     *
     * @param key 准考证号 或 考生姓名
     */
    @GetMapping("/candidate")
    @Authorization
    public ApiResult<Pagination<SystemArrangeVO>> getArrangeInfo(@RequestParam("key") String key) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examArrangeService.getArrangeInfo(key));
    }

    @GetMapping("/site")
    @Authorization
    public ApiResult<Map<String, Object>> getArrangeInfo(@RequestParam("examName") String examName,
                                                         @RequestParam("site") String site) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                examArrangeService.getExamArrangeInfo(examName, site));
    }

    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateCandidateSite(@RequestBody UpdateCandidateSiteForm form) {
        examArrangeService.updateCandidateSite(form.getCandidateNo(), form.getSite());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
