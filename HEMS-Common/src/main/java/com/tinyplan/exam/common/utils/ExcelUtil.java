package com.tinyplan.exam.common.utils;

import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.tinyplan.exam.entity.dto.ScoreDTO;
import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private static final Map<String, String> SITE_HEADER_ALIAS;
    private static final Map<String, String> SCORE_HEADER_ALIAS;

    static {
        SITE_HEADER_ALIAS = new HashMap<>();
        SITE_HEADER_ALIAS.put("楼号", "building");
        SITE_HEADER_ALIAS.put("层号", "floor");
        SITE_HEADER_ALIAS.put("教室号", "room");
        SITE_HEADER_ALIAS.put("容量", "capacity");

        SCORE_HEADER_ALIAS = new HashMap<>();
        SCORE_HEADER_ALIAS.put("准考证号", "candidateNo");
        SCORE_HEADER_ALIAS.put("考生姓名", "realName");
        SCORE_HEADER_ALIAS.put("考试成绩", "score");
    }

    public static List<SiteInfoDTO> readSiteExcel(File siteExcel) {
        if (!ExcelFileUtil.isXlsx(siteExcel)) {
            throw new BusinessException(ResultStatus.RES_INVALID_FILE_TYPE);
        }
        ExcelReader reader = new ExcelReader(siteExcel, "Sheet1");
        reader.setHeaderAlias(SITE_HEADER_ALIAS);
        return reader.read(0, 1, SiteInfoDTO.class);
    }

    public static List<ScoreDTO> readScoreExcel(File scoreExcel) {
        if (!ExcelFileUtil.isXlsx(scoreExcel)) {
            throw new BusinessException(ResultStatus.RES_INVALID_FILE_TYPE);
        }
        ExcelReader reader = new ExcelReader(scoreExcel, 0);
        reader.setHeaderAlias(SCORE_HEADER_ALIAS);
        String examNo = (String) reader.readRow(0).get(0);
        List<ScoreDTO> scoreList = reader.read(1, 2, ScoreDTO.class);
        for (ScoreDTO dto : scoreList) {
            dto.setExamNo(examNo);
        }
        return scoreList;
    }
}
