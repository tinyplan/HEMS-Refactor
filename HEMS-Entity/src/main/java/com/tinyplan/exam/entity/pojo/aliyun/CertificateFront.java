package com.tinyplan.exam.entity.pojo.aliyun;

public class CertificateFront {
    private String name;
    private String nationality;
    private String num;
    private String sex;
    private String birth;
    private String address;
    private boolean success;

    public CertificateFront() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CertificateFront{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", num='" + num + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", success=" + success +
                '}';
    }
}
