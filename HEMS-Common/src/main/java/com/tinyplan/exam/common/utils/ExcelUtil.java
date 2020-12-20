package com.tinyplan.exam.common.utils;

import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private static final Map<String, String> SITE_HEADER_ALIAS;

    static {
        SITE_HEADER_ALIAS = new HashMap<>();
        SITE_HEADER_ALIAS.put("楼号", "building");
        SITE_HEADER_ALIAS.put("层号", "floor");
        SITE_HEADER_ALIAS.put("教室号", "room");
        SITE_HEADER_ALIAS.put("容量", "capacity");
    }

    public static List<SiteInfoDTO> readSiteExcel(File siteExcel){
        if (!ExcelFileUtil.isXlsx(siteExcel)) {
            throw new BusinessException(ResultStatus.RES_INVALID_FILE_TYPE);
        }
        ExcelReader reader = new ExcelReader(siteExcel, "Sheet1");
        reader.setHeaderAlias(SITE_HEADER_ALIAS);
        return reader.read(0, 1, SiteInfoDTO.class);
    }
}
