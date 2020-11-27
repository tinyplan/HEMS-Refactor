package com.tinyplan.exam.common.service.impl;

import com.tinyplan.exam.common.service.LogService;
import com.tinyplan.exam.entity.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("logServiceImpl")
public class LogServiceImpl implements LogService {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    public void save(Log log) {
        LOGGER.info(log.toString());
    }

}
