package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.common.utils.*;
import com.tinyplan.exam.dao.AdminMapper;
import com.tinyplan.exam.dao.NewsMapper;
import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.NewsVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.ImageService;
import com.tinyplan.exam.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("newsServiceImpl")
public class NewsServiceImpl implements NewsService {

    @Resource(name = "adminMapper")
    private AdminMapper adminMapper;

    @Resource(name = "newsMapper")
    private NewsMapper newsMapper;

    @Resource(name = "imageServiceImpl")
    private ImageService imageService;

    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

    /**
     * 保存一张图片到本地缓存
     *
     * @param request 请求体
     * @param filename 文件名
     * @param file 文件
     */
    @Override
    public void saveOneNewsImage(HttpServletRequest request, String filename, MultipartFile file) {
        String path = this.generateTmpPath(request);
        imageService.saveToLocal(path, filename, file);
    }

    /**
     * 删除一张缓存到本地的新闻图片
     *
     * @param filename 文件名
     */
    @Override
    public void deleteOneNewsImage(HttpServletRequest request, String filename) {
        String path = this.generateTmpPath(request);
        imageService.deleteLocal(path, filename);
    }

    /**
     * 批量删除本地缓存的文件
     *
     * @param request 请求体
     * @param filenameList 删除的文件数组
     */
    @Override
    public void deleteNewsImages(HttpServletRequest request, List<String> filenameList) {
        String path = this.generateTmpPath(request);
        for (String filename : filenameList) {
            imageService.deleteLocal(path, filename);
        }
    }

    /**
     * 发布新闻
     *
     * 业务描述: 1. 根据filenameList找到本地缓存中的图片;
     *          2. 将图片上传到七牛云，成功后，删除本地缓存中的图片
     *          3. 保存新闻信息到数据库
     * @param title 标题
     * @param content 内容
     * @param filenameList 图片文件名
     */
    @Override
    public void publishNews(HttpServletRequest request, String title, String content, List<String> filenameList) {
        String path = this.generateTmpPath(request);
        imageService.uploadQiniuYun(path, filenameList);
        // 准备新闻图片
        News news = new News();
        String prefix = PrefixUtil.getObjectPrefix(ObjectType.NEWS);
        LocalDateTime now = LocalDateTime.now();
        String date = LocalDateTimeUtil.format(now, "yyyyMMdd");
        String id = Integer.toString(CommonUtil.checkMaxId(newsMapper.getMaxId()) + 1);
        news.setNewsId(PrefixUtil.generateId(prefix, date, id));
        news.setTitle(title);
        news.setPublishDate(LocalDateTimeUtil.format(now, "yyyy-MM-dd"));
        JwtDataLoad jwtDataLoad = JwtUtil.getDataLoad(request);
        news.setPublisher(jwtDataLoad.getUserId());
        news.setCoverImg(hemsProperties.getNewsCoverImg());
        news.setContent(content);
        news.setContentImg(String.join("|", filenameList));
        // TODO 新闻类型，可能不做
        news.setType("1");
        Integer result = newsMapper.insertNews(news);
        if (result != 1) {
            throw new BusinessException(ResultStatus.RES_NEWS_PUBLISH_FAILED);
        }
    }

    /**
     * 获取全部新闻
     *
     * @param pageSize 页面容量
     */
    @Override
    public Pagination<NewsVO> getAllNews(Integer pageSize) {
        List<NewsVO> newsVOList = imageService.handleNewsImage(newsMapper.getAllNews());
        Pagination<NewsVO> paginationData = new Pagination<>();
        paginationData.setTotal(newsVOList.size());
        paginationData.setTableData(PaginationUtil.getLogicPagination(newsVOList, pageSize));
        return paginationData;
    }

    /**
     * 获取一条新闻
     *
     * @param newsId 新闻ID
     * @return 新闻信息
     */
    @Override
    public NewsVO getOneNews(String newsId) {
        NewsVO newsVO = imageService.handleNewsImage(newsMapper.getNews(newsId));
        User user = adminMapper.getAdminByUsername(newsVO.getPublisher());
        newsVO.setPublisherName(user.getAccountName());
        return newsVO;
    }

    /**
     * 删除新闻
     *
     * @param newsId 新闻ID
     */
    @Override
    public void deleteNews(String newsId) {
        News news = newsMapper.getNews(newsId);
        Integer result = newsMapper.deleteNews(newsId);
        if (news == null || result != 1) {
            throw new BusinessException(ResultStatus.RES_NEWS_DELETE_FAILED);
        }
        // 删除云端文件
        this.deleteOldImages(news.getContentImg());
    }

    /**
     * 更新新闻信息
     *
     * @param request 请求体
     * @param newsId 新闻ID
     * @param title 标题
     * @param content 新闻内容
     * @param filenameList 覆盖的文件列表
     */
    @Override
    public void updateNews(HttpServletRequest request, String newsId, String title, String content, List<String> filenameList) {
        News old = newsMapper.getNews(newsId);
        News news = new News();
        news.setNewsId(newsId);
        news.setTitle(title);
        news.setContent(content);
        news.setContentImg(String.join("|", filenameList));
        // 上传覆盖的图片
        imageService.uploadQiniuYun(this.generateTmpPath(request), filenameList);
        // 删除旧图片
        this.deleteOldImages(old.getContentImg());
        // 更新数据库信息
        newsMapper.updateNews(news);
    }

    /**
     * 构建文件保存的路径(项目根路径 + 缓存路径 + 用户ID命名的目录)
     *
     * @return 保存路径
     */
    private String generateTmpPath(HttpServletRequest request) {
        JwtDataLoad load = JwtUtil.getDataLoad(request);
        return request.getServletContext().getRealPath(hemsProperties.getNewsTmpDir() + load.getUserId());
    }

    /**
     * 删除旧图片
     *
     * @param image 用“|”分割的图片文件名字符串
     */
    private void deleteOldImages(String image){
        if (image == null || image.length() == 0) {
            return;
        }
        List<String> images = new ArrayList<>(Arrays.asList(image.split("\\|")));
        imageService.deleteQiniuYun(images);
    }
}
