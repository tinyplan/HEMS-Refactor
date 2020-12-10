package com.tinyplan.exam.entity.vo;

import java.util.List;

public class Pagination<T> {
    private Integer total;
    private List<List<T>> tableData;

    public Pagination() {}

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<List<T>> getTableData() {
        return tableData;
    }

    public void setTableData(List<List<T>> tableData) {
        this.tableData = tableData;
    }
}
