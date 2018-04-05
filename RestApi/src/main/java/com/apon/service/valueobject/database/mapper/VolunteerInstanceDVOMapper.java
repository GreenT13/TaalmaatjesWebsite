package com.apon.service.valueobject.database.mapper;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.service.valueobject.database.VolunteerInstanceDVO;

public class VolunteerInstanceDVOMapper {
    private VolunteerInstanceDVO volunteerInstanceDVO;
    private VolunteerinstancePojo volunteerinstancePojo;

    public VolunteerInstanceDVOMapper() {
        volunteerInstanceDVO = new VolunteerInstanceDVO();
        volunteerinstancePojo = new VolunteerinstancePojo();
    }

    public VolunteerInstanceDVO getVolunteerInstanceDVO() {
        return volunteerInstanceDVO;
    }

    public VolunteerinstancePojo getVolunteerinstancePojo() {
        return volunteerinstancePojo;
    }

    public void setVolunteerInstanceDVO(VolunteerInstanceDVO volunteerInstanceDVO) {
        this.volunteerInstanceDVO = volunteerInstanceDVO;
        fillPojoWithDVO(volunteerInstanceDVO);
    }

    public void setVolunteerinstancePojo(VolunteerinstancePojo volunteerinstancePojo) {
        this.volunteerinstancePojo = volunteerinstancePojo;
        fillDVOWithPojo(volunteerinstancePojo);
    }

    private void fillDVOWithPojo(VolunteerinstancePojo volunteerinstancePojo) {
        volunteerInstanceDVO.setExternalIdentifier(volunteerinstancePojo.getExternalidentifier());
        volunteerInstanceDVO.setDateStart(volunteerinstancePojo.getDatestart());
        volunteerInstanceDVO.setDateEnd(volunteerinstancePojo.getDateend());
    }

    private void fillPojoWithDVO(VolunteerInstanceDVO volunteerInstanceDVO) {
        volunteerinstancePojo.setExternalidentifier(volunteerInstanceDVO.getExternalIdentifier());
        volunteerinstancePojo.setDatestart(volunteerInstanceDVO.getDateStart());
        volunteerinstancePojo.setDateend(volunteerInstanceDVO.getDateEnd());
    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerPojo(volunteerPojo);
        this.volunteerInstanceDVO.setVolunteerDVO(volunteerDVOMapper.getVolunteerDVO());
    }

}
