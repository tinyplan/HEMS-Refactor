package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.vo.NewsVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImageService {

    void saveToLocal(String localPath, String filename, MultipartFile file);

    void deleteLocal(String localPath, String filename);

    void deleteLocal(File file);

    void uploadQiniuYun(String localPath, List<String> filenameList);

    void deleteQiniuYun(List<String> uploadList);

    List<NewsVO> handleNewsImage(List<News> newsList);

    NewsVO handleNewsImage(News news);

}
