package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.vo.NewsVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public interface ImageService {

    String saveToLocal(String localPath, String filename, MultipartFile file);

    void deleteLocal(String localPath, String filename);

    void deleteLocal(File file);

    void uploadQiniuYun(String localPath, List<String> filenameList);

    void deleteQiniuYun(List<String> uploadList);

    List<NewsVO> handleNewsImage(List<News> newsList);

    NewsVO handleNewsImage(News news);

    String getFileTmpPath(HttpServletRequest request, ObjectType type);

}
