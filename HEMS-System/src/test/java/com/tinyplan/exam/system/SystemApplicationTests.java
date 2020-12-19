package com.tinyplan.exam.system;

import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.dao.CandidateMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class SystemApplicationTests {

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Test
    void contextLoads() {
        System.out.println(DateUtil.offsetDay(new Date(), 1));
    }

}
