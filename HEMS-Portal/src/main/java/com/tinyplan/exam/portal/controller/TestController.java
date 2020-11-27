package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.service.impl.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource(name = "testService")
    private TestService testService;

    @GetMapping("/test")
    public String test(){
        return testService.test();
    }

}
