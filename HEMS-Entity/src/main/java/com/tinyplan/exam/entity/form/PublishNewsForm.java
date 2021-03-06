package com.tinyplan.exam.entity.form;

import java.util.List;

/**
 * 新闻提交 表单
 */
public class PublishNewsForm {
    private String title;
    private String content;
    private List<String> filenameList;

    public PublishNewsForm() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFilenameList() {
        return filenameList;
    }

    public void setFilenameList(List<String> filenameList) {
        this.filenameList = filenameList;
    }
}
