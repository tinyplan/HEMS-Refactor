package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.ExcelUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.SiteMapper;
import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.Site;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.pojo.type.SiteStatus;
import com.tinyplan.exam.entity.vo.SiteVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.ImageService;
import com.tinyplan.exam.service.SiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SiteServiceImpl implements SiteService {

    @Resource(name = "imageServiceImpl")
    private ImageService imageService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "siteMapper")
    private SiteMapper siteMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Override
    @Transactional
    public void uploadSite(HttpServletRequest request, MultipartFile excelFile) {
        String filePath = null;
        try {
            String filename = UUID.randomUUID() + "_" + excelFile.getOriginalFilename();
            filePath = imageService.saveToLocal(imageService.getFileTmpPath(request, ObjectType.EXCEL), filename, excelFile);
            List<SiteInfoDTO> siteInfoList = ExcelUtil.readSiteExcel(new File(filePath));
            List<Site> insertSiteList = new ArrayList<>(siteInfoList.size());
            for (SiteInfoDTO dto : siteInfoList) {
                Site oldSite = siteMapper.getSiteByRoom(dto.getRoom());
                if (oldSite != null) {
                    // 重复导入相同的教室, 若原来的教室被废弃使用, 重新使其变为可用
                    if (oldSite.getStatus().equals(SiteStatus.WASTE.getCode())) {
                        siteMapper.updateSiteStatus(null, dto.getRoom(), SiteStatus.AVAILABLE.getCode());
                    }
                } else {
                    Site site = dataInjectService.injectSite(dto);
                    site.setSiteId(PrefixUtil.generateId(PrefixUtil.getObjectPrefix(ObjectType.SITE), site.getRoom()));
                    insertSiteList.add(site);
                }
            }
            if (insertSiteList.size() != 0) {
                siteMapper.insertSites(insertSiteList);
            }
        }finally {
            if (filePath != null && !"".equals(filePath)){
                imageService.deleteLocal(new File(filePath));
            }
        }
    }

    @Override
    @Transactional
    public void addSite(Site site) {
        Site oldSite = siteMapper.getSiteByRoom(site.getRoom());
        if (oldSite != null) {
            if (oldSite.getStatus().equals(SiteStatus.WASTE.getCode())) {
                siteMapper.updateSiteStatus(site.getSiteId(), null, SiteStatus.AVAILABLE.getCode());
                return;
            } else {
                throw new BusinessException(ResultStatus.RES_SITE_CONFLICT);
            }
        }
        site.setSiteId(PrefixUtil.generateId(PrefixUtil.getObjectPrefix(ObjectType.SITE), site.getRoom()));
        site.setStatus(SiteStatus.AVAILABLE.getCode());
        siteMapper.insertSite(site);
    }

    @Override
    public List<SiteVO> querySite(String building, String floor) {
        List<Site> siteList = siteMapper.querySite(building, floor);
        List<SiteVO> result = new ArrayList<>(siteList.size());
        for (Site site : siteList) {
            result.add(new SiteVO(site));
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteSite(String siteId) {
        siteMapper.updateSiteStatus(siteId, null, SiteStatus.WASTE.getCode());
    }

    @Override
    @Transactional
    public void updateCapacity(String room, Integer capacity) {
        Integer waitingExamCount = examDetailMapper.getExamDetailCountByStatus(ExamStatus.BEFORE_EXAM.getCode());
        if (waitingExamCount != 0) {
            // 有考试正在开考, 无法修改
            throw new BusinessException(ResultStatus.RES_SITE_UPDATE_CAPACITY_FAIL);
        }
        siteMapper.updateSiteCapacity(room, capacity);
    }
}
