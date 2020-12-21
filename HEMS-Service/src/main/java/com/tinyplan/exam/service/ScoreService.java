package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalScoreInfoVO;
import com.tinyplan.exam.entity.vo.ScoreVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ScoreService {

    List<ScoreVO> uploadScore(HttpServletRequest request, MultipartFile excelFile);

    Pagination<PortalScoreInfoVO> getScoreForPortal(String token, Integer pageSize);

}
