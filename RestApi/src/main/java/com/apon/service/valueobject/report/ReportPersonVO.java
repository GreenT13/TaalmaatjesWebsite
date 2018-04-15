package com.apon.service.valueobject.report;

public class ReportPersonVO {
    private Integer minAge;
    private Integer maxAge;
    private String sex;
    private Integer countNew;
    private Integer countActive;

    public ReportPersonVO(Integer minAge, Integer maxAge, String sex, Integer countNew, Integer countActive) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sex = sex;
        this.countNew = countNew;
        this.countActive = countActive;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCountNew() {
        return countNew;
    }

    public void setCountNew(Integer countNew) {
        this.countNew = countNew;
    }

    public Integer getCountActive() {
        return countActive;
    }

    public void setCountActive(Integer countActive) {
        this.countActive = countActive;
    }
}

