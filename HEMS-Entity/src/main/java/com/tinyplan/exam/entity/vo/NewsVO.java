package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.News;

import java.util.ArrayList;
import java.util.List;

public class NewsVO {
    private String newsId;
    private String title;
    private String publishDate;
    private String publisher;
    // 发布者姓名(需要额外设置)
    private String publisherName;
    private String coverImg;
    private String content;
    // 内容图片数组
    private List<String> contentImgList;
    private String type;

    public NewsVO(News news) {
        this.newsId = news.getNewsId();
        this.title = news.getTitle();
        this.publishDate = news.getPublishDate();
        this.publisher = news.getPublisher();
        // 先赋值为原始路径
        this.coverImg = news.getCoverImg();
        this.content = news.getContent();
        this.contentImgList = new ArrayList<>();
        this.type = news.getType();
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getContentImgList() {
        return contentImgList;
    }

    public void setContentImgList(List<String> contentImgList) {
        this.contentImgList = contentImgList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
