package com.tinyplan.exam.entity.dto;

public class SiteInfoDTO {
    private String building;
    private Integer floor;
    private String room;
    private Integer capacity;

    public SiteInfoDTO() {}

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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "SiteInfoDTO{" +
                "building='" + building + '\'' +
                ", floor=" + floor +
                ", room='" + room + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
