package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.SiteForm;
import com.tinyplan.exam.entity.po.Site;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.SiteVO;
import com.tinyplan.exam.service.SiteService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Resource(name = "siteServiceImpl")
    private SiteService siteService;

    @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Authorization
    public ApiResult<Object> uploadSiteInfo(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {
        siteService.uploadSite(request, file);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @PostMapping("")
    @Authorization
    public ApiResult<Object> addSite(@RequestBody SiteForm form) {
        Site site = new Site();
        site.setBuilding(form.getBuilding());
        site.setFloor(form.getFloor());
        site.setRoom(form.getClassroom());
        site.setCapacity(form.getCapacity());
        siteService.addSite(site);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @DeleteMapping("")
    @Authorization
    public ApiResult<Object> deleteSite(@RequestParam("siteId") String siteId) {
        siteService.deleteSite(siteId);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @GetMapping("")
    @Authorization
    public ApiResult<List<SiteVO>> querySiteByCondition(@RequestParam("building") String building,
                                                        @RequestParam("floor") String floor) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, siteService.querySite(building, floor));
    }

    /**
     * 更新容量
     *
     * @param form 表单(只有classroom和capacity可用)
     */
    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateSiteCapacity(@RequestBody SiteForm form) {
        siteService.updateCapacity(form.getClassroom(), form.getCapacity());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
