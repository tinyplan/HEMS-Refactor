package com.tinyplan.exam.service;

import com.tinyplan.exam.dao.TestMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class TestService {

    @Resource
    private TestMapper testMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String test(){
        return testMapper.getMaxId() + "";
    }

    public String testRedis(){
        stringRedisTemplate.opsForValue().set("name", "tom");
        return "123";
    }


}
