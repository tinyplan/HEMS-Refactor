package com.tinyplan.exam.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.ExcelUtil;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.entity.dto.SiteInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RunWith(SpringRunner.class)
// @SpringBootTest
class SystemApplicationTests {

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Test
    void contextLoads() {
        System.out.println(DateUtil.offsetDay(new Date(), 1));
    }

    @Test
    void readExcel() {
        File excel = new File("C:\\Users\\34054\\Desktop\\test\\考点导入.xlsx");
        System.out.println(ExcelUtil.readSiteExcel(excel));
    }

}
