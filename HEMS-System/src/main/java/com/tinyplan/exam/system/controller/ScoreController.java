package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.ScoreVO;
import com.tinyplan.exam.service.ScoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource(name = "scoreServiceImpl")
    private ScoreService scoreService;

    @PostMapping("/excel")
    @Authorization
    public ApiResult<List<ScoreVO>> uploadScore(HttpServletRequest request,
                                                @RequestParam("file") MultipartFile file) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, scoreService.uploadScore(request, file));
    }

}
