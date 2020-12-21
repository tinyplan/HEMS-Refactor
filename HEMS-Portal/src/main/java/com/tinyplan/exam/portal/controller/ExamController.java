package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalScoreInfoVO;
import com.tinyplan.exam.service.ExamService;
import com.tinyplan.exam.service.ScoreService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ExamController {

    @Resource(name = "examServiceImpl")
    private ExamService examService;

    @Resource(name = "scoreServiceImpl")
    private ScoreService scoreService;

    @GetMapping("/exam")
    @Authorization
    public ApiResult<Map<String, List<List<Exam>>>> getExam() {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, examService.getExam());
    }

    @GetMapping("/exam/score")
    public ApiResult<Pagination<PortalScoreInfoVO>> getScore(HttpServletRequest request,
                                                             @Param("pageSize") Integer pageSize) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS,
                scoreService.getScoreForPortal(RequestUtil.getToken(request), pageSize));
    }

}
