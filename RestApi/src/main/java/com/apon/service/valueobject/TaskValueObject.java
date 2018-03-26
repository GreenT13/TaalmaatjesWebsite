package com.apon.service.valueobject;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class TaskValueObject {
    private String taskExtId;
    private String title;
    private String description;
    private Boolean isFinished;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateToBeFinished;

    private VolunteerValueObject volunteerValueObject;

    public String getTaskExtId() {
        return taskExtId;
    }

    public void setTaskExtId(String taskExtId) {
        this.taskExtId = taskExtId;
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

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean finished) {
        isFinished = finished;
    }

    public Date getDateToBeFinished() {
        return dateToBeFinished;
    }

    public void setDateToBeFinished(Date dateToBeFinished) {
        this.dateToBeFinished = dateToBeFinished;
    }

    public VolunteerValueObject getVolunteerValueObject() {
        return volunteerValueObject;
    }

    public void setVolunteerValueObject(VolunteerValueObject volunteerValueObject) {
        this.volunteerValueObject = volunteerValueObject;
    }
}
