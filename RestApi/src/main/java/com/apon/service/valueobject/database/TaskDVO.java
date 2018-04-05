package com.apon.service.valueobject.database;

import com.apon.service.config.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;

public class TaskDVO {
    private String externalIdentifier;
    private String title;
    private String description;
    private VolunteerDVO volunteerDVO;
    private Boolean isFinished;
    @JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateToBeFinished;

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VolunteerDVO getVolunteerDVO() {
        return volunteerDVO;
    }

    public void setVolunteerDVO(VolunteerDVO volunteerDVO) {
        this.volunteerDVO = volunteerDVO;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getDateToBeFinished() {
        return dateToBeFinished;
    }

    public void setDateToBeFinished(Date dateToBeFinished) {
        this.dateToBeFinished = dateToBeFinished;
    }
}
