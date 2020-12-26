package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalScoreInfoVO;
import com.tinyplan.exam.service.ExamArrangeService;
import com.tinyplan.exam.service.ExamService;
import com.tinyplan.exam.service.ScoreService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExamController {

    @Resource(name = "examServiceImpl")
    private ExamService examService;

    @Resource(name = "scoreServiceImpl")
    private ScoreService scoreService;

    @Resource(name = "examArrangeServiceImpl")
    private ExamArrangeService examArrangeService;

    @GetMapping("/exam")
    @Authorization
    public ApiResult<Map<String, List<List<Exam>>>> getExam() {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examService.getExam());
    }

    @GetMapping("/exam/score")
    @Authorization
    public ApiResult<Pagination<PortalScoreInfoVO>> getScore(HttpServletRequest request,
                                                             @RequestParam("pageSize") Integer pageSize,
                                                             @RequestParam("candidateNo") String candidateNo) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                scoreService.getScoreForPortal(RequestUtil.getToken(request), candidateNo, pageSize));
    }

    @GetMapping("/ticket")
    public ApiResult<Map<String, Object>> getArrangeInfo(@RequestParam("realName") String realName,
                                                         @RequestParam("idCard") String idCard) {
        Map<String, Object> result = new HashMap<>();
        result.put("tableData", examArrangeService.getArrangeForPortal(realName, idCard));
        return new ApiResult<>(ResultStatus.RES_SUCCESS, result);
    }

}
