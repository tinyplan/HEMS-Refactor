package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.service.impl.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class TestController {

    @Resource(name = "testService")
    private TestService testService;

    @PatchMapping("/test")
    public String test(@RequestBody Map<String, String> params){
        // return testService.test();
        System.out.println(params.get("password"));
        return "success";
    }

}
