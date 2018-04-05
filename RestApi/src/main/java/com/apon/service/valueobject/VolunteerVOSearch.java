package com.apon.service.valueobject;

import com.apon.service.valueobject.database.VolunteerDVO;

public class VolunteerVOSearch {
    private VolunteerDVO volunteerDVO;
    private Integer numberOfMatches;

    public VolunteerDVO getVolunteerDVO() {
        return volunteerDVO;
    }

    public void setVolunteerDVO(VolunteerDVO volunteerDVO) {
        this.volunteerDVO = volunteerDVO;
    }

    public Integer getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(Integer numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }
}
