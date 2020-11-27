package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class NewsController {

    @PostMapping("/news")
    @Authorization
    public ApiResult<String> publishNews(MultipartHttpServletRequest request) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        MultipartFile fileList = request.getFile("fileList");
        System.out.println(title);
        System.out.println(content);
        System.out.println(fileList.getName());
        System.out.println(fileList.getOriginalFilename());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, "123");
    }

    @GetMapping("/news")
    @Authorization
    public ApiResult<String> getAllNews() {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, "123");
    }

}
