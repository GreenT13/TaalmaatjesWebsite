package com.apon.service.valueobject.database.mapper;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.service.valueobject.database.VolunteerDVO;

public class VolunteerDVOMapper {
    private VolunteerDVO volunteerDVO;
    private VolunteerPojo volunteerPojo;

    public VolunteerDVOMapper() {
        this.volunteerDVO = new VolunteerDVO();
        this.volunteerPojo = new VolunteerPojo();
    }

    public VolunteerDVO getVolunteerDVO() {
        return volunteerDVO;
    }

    public VolunteerPojo getVolunteerPojo() {
        return volunteerPojo;
    }

    public void setVolunteerDVO(VolunteerDVO volunteerDVO) {
        this.volunteerDVO = volunteerDVO;
        fillPojoWithDVO(volunteerDVO);
    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        this.volunteerPojo = volunteerPojo;
        fillDVOWithPojo(volunteerPojo);
    }

    private void fillDVOWithPojo(VolunteerPojo volunteerPojo) {
        volunteerDVO.setExternalIdentifier(volunteerPojo.getExternalidentifier());
        volunteerDVO.setFirstName(volunteerPojo.getFirstname());
        volunteerDVO.setInsertion(volunteerPojo.getInsertion());
        volunteerDVO.setLastName(volunteerPojo.getLastname());
        volunteerDVO.setDateOfBirth(volunteerPojo.getDateofbirth());
        volunteerDVO.setGender(volunteerPojo.getGender());
        volunteerDVO.setPhoneNumber(volunteerPojo.getPhonenumber());
        volunteerDVO.setMobilePhoneNumber(volunteerPojo.getMobilephonenumber());
        volunteerDVO.setEmail(volunteerPojo.getEmail());
        volunteerDVO.setDateTraining(volunteerPojo.getDatetraining());
        volunteerDVO.setCity(volunteerPojo.getCity());
        volunteerDVO.setPostalCode(volunteerPojo.getPostalcode());
        volunteerDVO.setStreetName(volunteerPojo.getStreetname());
        volunteerDVO.setHouseNr(volunteerPojo.getHousenr());
        volunteerDVO.setLog(volunteerPojo.getLog());
        volunteerDVO.setJob(volunteerPojo.getJob());
        volunteerDVO.setIsClassAssistant(volunteerPojo.getIsclassassistant());
        volunteerDVO.setIsTaalmaatje(volunteerPojo.getIstaalmaatje());
    }

    private void fillPojoWithDVO(VolunteerDVO volunteerDVO) {
        volunteerPojo.setExternalidentifier(volunteerDVO.getExternalIdentifier());
        volunteerPojo.setFirstname(volunteerDVO.getFirstName());
        volunteerPojo.setInsertion(volunteerDVO.getInsertion());
        volunteerPojo.setLastname(volunteerDVO.getLastName());
        volunteerPojo.setDateofbirth(volunteerDVO.getDateOfBirth());
        volunteerPojo.setGender(volunteerDVO.getGender());
        volunteerPojo.setPhonenumber(volunteerDVO.getPhoneNumber());
        volunteerPojo.setMobilephonenumber(volunteerDVO.getMobilePhoneNumber());
        volunteerPojo.setEmail(volunteerDVO.getEmail());
        volunteerPojo.setDatetraining(volunteerDVO.getDateTraining());
        volunteerPojo.setPostalcode(volunteerDVO.getPostalCode());
        volunteerPojo.setCity(volunteerDVO.getCity());
        volunteerPojo.setStreetname(volunteerDVO.getStreetName());
        volunteerPojo.setHousenr(volunteerDVO.getHouseNr());
        volunteerPojo.setLog(volunteerDVO.getLog());
        volunteerPojo.setJob(volunteerDVO.getJob());
        volunteerPojo.setIsclassassistant(volunteerDVO.getIsClassAssistant());
        volunteerPojo.setIstaalmaatje(volunteerDVO.getIsTaalmaatje());
    }

}
