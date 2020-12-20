package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.Site;

public class SiteVO {
    private String siteId;
    private String building;
    private Integer floor;
    private String classroom;
    private Integer capacity;

    public SiteVO() {}

    public SiteVO(Site site) {
        this.siteId = site.getSiteId();
        this.building = site.getBuilding();
        this.floor = site.getFloor();
        this.classroom = site.getRoom();
        this.capacity = site.getCapacity();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
