package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.Site;
import com.tinyplan.exam.entity.vo.SiteVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SiteService {

    void uploadSite(HttpServletRequest request, MultipartFile excelFile);

    void addSite(Site site);

    List<SiteVO> querySite(String building, String floor);

    void deleteSite(String siteId);

    void updateCapacity(String room, Integer capacity);

}
