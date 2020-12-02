package com.tinyplan.exam.entity.form;

import java.util.List;

/**
 * 批量删除本地图片 表单
 */
public class DeleteImagesForm {
    private List<String> filenameList;

    public DeleteImagesForm() {}

    public List<String> getFilenameList() {
        return filenameList;
    }

    public void setFilenameList(List<String> filenameList) {
        this.filenameList = filenameList;
    }
}
