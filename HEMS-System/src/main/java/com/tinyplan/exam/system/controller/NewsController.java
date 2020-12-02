package com.tinyplan.exam.system.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.entity.form.DeleteImagesForm;
import com.tinyplan.exam.entity.form.PublishNewsForm;
import com.tinyplan.exam.entity.form.UpdateNewsForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.NewsVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.NewsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NewsController {

    @Resource(name = "newsServiceImpl")
    private NewsService newsService;

    /**
     * 上传新闻图片(只上传一张图片)
     *
     * @param file 图片文件
     * @param filename 文件名
     * @param request 请求体
     */
    @PostMapping(value = "/news/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Authorization
    public ApiResult<Map<String, String>> uploadNewsImage(@RequestParam("filename") String filename,
                                                          @RequestParam("file") MultipartFile file,
                                                          HttpServletRequest request){
        newsService.saveOneNewsImage(request, filename, file);
        // 有异常的话已经在业务逻辑中抛出，这里不用判断，执行正常的逻辑即可
        Map<String, String> result = new HashMap<>();
        result.put("filename", filename);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, result);
    }

    /**
     * 删除新闻图片(只删除一张图片)
     *
     * @param filename 删除的文件名
     * @param request 请求体
     */
    @DeleteMapping("/news/image")
    @Authorization
    public ApiResult<Map<String, String>> deleteNewsImage(@RequestParam("filename") String filename,
                                                          HttpServletRequest request){
        newsService.deleteOneNewsImage(request, filename);
        // 有异常的话已经在调用中抛出，这里不用判断，执行正常的逻辑即可
        Map<String, String> result = new HashMap<>();
        result.put("filename", filename);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, result);
    }

    /**
     * 取消表单提交时, 批量删除本地缓存的图片
     *
     * @return
     */
    @PostMapping("/news/images")
    @Authorization
    public ApiResult<Object> deleteNewsImages(HttpServletRequest request, @RequestBody DeleteImagesForm form){
        newsService.deleteNewsImages(request, form.getFilenameList());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 发布新闻
     *
     * @param request 请求体
     * @param publishNewsForm 发布新闻所提交的表单
     */
    @PostMapping("/news")
    @Authorization
    public ApiResult<Object> publishNews(HttpServletRequest request, @RequestBody PublishNewsForm publishNewsForm){
        newsService.publishNews(request, publishNewsForm.getTitle(), publishNewsForm.getContent(), publishNewsForm.getFilenameList());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 获取全部新闻
     *
     * @param pageSize 每页数据容量
     */
    @GetMapping("/news")
    @Authorization
    public ApiResult<Pagination<NewsVO>> getAllNews(@RequestParam("pageSize") Integer pageSize) {
        return new ApiResult<>(ResultStatus.RES_SUCCESS, newsService.getAllNews(pageSize));
    }

    /**
     * 修改新闻信息
     *
     * @param form 更新信息提交的表单
     */
    @PatchMapping("/news")
    public ApiResult<Object> updateNews(HttpServletRequest request, @RequestBody UpdateNewsForm form){
        newsService.updateNews(request, form.getNewsId(), form.getTitle(), form.getContent(), form.getFilenameList());
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 删除新闻
     *
     * @param newsId 新闻ID
     */
    @DeleteMapping("/news")
    @Authorization
    public ApiResult<Object> deleteNews(@RequestParam("newsId") String newsId){
        newsService.deleteNews(newsId);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

}
