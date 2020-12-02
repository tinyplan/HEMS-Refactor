package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper {

    Integer getMaxId();

    Integer insertNews(News news);

    List<News> getAllNews();

    News getNews(@Param("newsId") String newsId);

    Integer deleteNews(@Param("newsId") String newsId);

    Integer updateNews(News news);

}
