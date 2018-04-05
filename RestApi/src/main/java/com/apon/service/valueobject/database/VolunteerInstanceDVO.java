package com.apon.service.valueobject.database;

import com.apon.service.config.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;

public class VolunteerInstanceDVO {
    private VolunteerDVO volunteerDVO;
    private String externalIdentifier;
    @JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateStart;
    @JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateEnd;

    public VolunteerDVO getVolunteerDVO() {
        return volunteerDVO;
    }

    public void setVolunteerDVO(VolunteerDVO volunteerDVO) {
        this.volunteerDVO = volunteerDVO;
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
