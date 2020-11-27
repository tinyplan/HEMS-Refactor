package com.tinyplan.exam.entity.form;

import org.springframework.web.multipart.MultipartFile;

public class PublishNewsForm {
    private String title;
    private String content;
    private MultipartFile[] multipartFile;

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

    public MultipartFile[] getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile[] multipartFile) {
        this.multipartFile = multipartFile;
    }
}
