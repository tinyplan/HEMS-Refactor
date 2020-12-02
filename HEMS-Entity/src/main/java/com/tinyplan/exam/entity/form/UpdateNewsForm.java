package com.tinyplan.exam.entity.form;

import java.util.List;

public class UpdateNewsForm {
    private String newsId;
    private String title;
    private String content;
    private List<String> filenameList;

    public UpdateNewsForm() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<String> getFilenameList() {
        return filenameList;
    }

    public void setFilenameList(List<String> filenameList) {
        this.filenameList = filenameList;
    }
}
