package com.tinyplan.exam.common;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/*@SpringBootTest*/
class CommonApplicationTests {

    @Test
    void contextLoads() {
        String date = "20200202";
        String year = date.substring(0, 4);
        String mouth = date.substring(4, 6);
        String day = date.substring(6);
        System.out.println(String.join("-", year, mouth, day));
    }

    @Test
    void test(){
        Date now = new Date();
        Date end = DateUtil.parse("2020-12-05");
        System.out.println(DateUtil.between(now, end, DateUnit.DAY, false));
    }

}
