package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.vo.NewsVO;
import com.tinyplan.exam.entity.vo.Pagination;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NewsService {

    void saveOneNewsImage(HttpServletRequest request, String filename, MultipartFile file);

    void deleteOneNewsImage(HttpServletRequest request, String filename);

    void deleteNewsImages(HttpServletRequest request, List<String> filenameList);

    void publishNews(HttpServletRequest request, String title, String content, List<String> filenameList);

    Pagination<NewsVO> getAllNews(Integer pageSize);

    NewsVO getOneNews(String newsId);

    void deleteNews(String newsId);

    void updateNews(HttpServletRequest request, String newsId, String title, String content, List<String> filenameList);

}
