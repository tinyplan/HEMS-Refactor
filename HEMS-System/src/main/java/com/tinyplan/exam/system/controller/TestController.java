package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.SysLog;
import com.tinyplan.exam.service.impl.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource(name = "testService")
    private TestService testService;

    @GetMapping("/test")
    @SysLog()
    public String test(){
        return testService.testRedis();
    }

}
