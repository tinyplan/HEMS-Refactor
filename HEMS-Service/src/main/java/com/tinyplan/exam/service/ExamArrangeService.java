package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalArrangeVO;
import com.tinyplan.exam.entity.vo.SystemArrangeVO;

import java.util.List;
import java.util.Map;

public interface ExamArrangeService {

    void arrange(String examNo);

    Pagination<SystemArrangeVO> getArrangeInfo(String key);

    List<String> getExamArrangeSite(String examName);

    Map<String, Object> getExamArrangeInfo(String examName, String siteName);

    void updateCandidateSite(String candidateNo, String siteName);

    List<PortalArrangeVO> getArrangeForPortal(String realName, String idCard);

}
