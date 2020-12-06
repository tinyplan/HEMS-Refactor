package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.NewsVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class NewsController {

    @Resource(name = "newsServiceImpl")
    private NewsService newsService;

    /**
     * 获取全部新闻
     *
     * @param pageSize 每页数据容量
     */
    @GetMapping("/news/pages")
    public ApiResult<Pagination<NewsVO>> getAllNews(@RequestParam("pageSize") Integer pageSize) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, newsService.getAllNews(pageSize));
    }

    @GetMapping("/news")
    public ApiResult<NewsVO> getOneNews(@RequestParam("newsId") String newsId) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, newsService.getOneNews(newsId));
    }

}
