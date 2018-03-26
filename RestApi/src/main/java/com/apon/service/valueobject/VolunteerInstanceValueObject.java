package com.apon.service.valueobject;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class VolunteerInstanceValueObject {
    private String volunteerExtId;
    private String externalIdentifier;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateStart;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEnd;

    public String getVolunteerExtId() {
        return volunteerExtId;
    }

    public void setVolunteerExtId(String volunteerExtId) {
        this.volunteerExtId = volunteerExtId;
    }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
