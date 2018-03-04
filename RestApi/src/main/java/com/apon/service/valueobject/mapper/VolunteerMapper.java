package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.service.valueobject.VolunteerValueObject;

@SuppressWarnings("unused")
public class VolunteerMapper {
    private VolunteerValueObject volunteerValueObject;
    private VolunteerPojo volunteerPojo;

    public VolunteerMapper() {
        this.volunteerValueObject = new VolunteerValueObject();
        this.volunteerPojo = new VolunteerPojo();

    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        volunteerValueObject.setExternalIdentifier(volunteerPojo.getExternalidentifier());
        volunteerValueObject.setFirstName(volunteerPojo.getFirstname());
        volunteerValueObject.setInsertion(volunteerPojo.getInsertion());
        volunteerValueObject.setLastName(volunteerPojo.getLastname());
        volunteerValueObject.setDateOfBirth(volunteerPojo.getDateofbirth());
        volunteerValueObject.setSex(volunteerPojo.getSex());
        volunteerValueObject.setPhoneNumber(volunteerPojo.getPhonenumber());
        volunteerValueObject.setMobilePhoneNumber(volunteerPojo.getMobilephonenumber());
        volunteerValueObject.setEmail(volunteerPojo.getEmail());
        volunteerValueObject.setDateTraining(volunteerPojo.getDatetraining());
        volunteerValueObject.setPostalCode(volunteerPojo.getPostalcode());
        volunteerValueObject.setStreetName(volunteerPojo.getStreetname());
        volunteerValueObject.setHouseNr(volunteerPojo.getHousenr());
        volunteerValueObject.setLog(volunteerPojo.getLog());
        volunteerValueObject.setJob(volunteerPojo.getJob());
        volunteerValueObject.setClassAssistant(volunteerPojo.getIsclassassistant());
        volunteerValueObject.setTaalmaatje(volunteerPojo.getIstaalmaatje());
    }

    public void setVolunteerValueObject(VolunteerValueObject volunteerValueObject) {
        setVolunteerValueObject(volunteerValueObject, null);
    }

    public void setVolunteerValueObject(VolunteerValueObject volunteerValueObject, Integer volunteerId) {
        volunteerPojo.setVolunteerid(volunteerId);
        volunteerPojo.setExternalidentifier(volunteerValueObject.getExternalIdentifier());
        volunteerPojo.setFirstname(volunteerValueObject.getFirstName());
        volunteerPojo.setInsertion(volunteerValueObject.getInsertion());
        volunteerPojo.setLastname(volunteerValueObject.getLastName());
        volunteerPojo.setDateofbirth(volunteerValueObject.getDateOfBirth());
        volunteerPojo.setSex(volunteerValueObject.getSex());
        volunteerPojo.setPhonenumber(volunteerValueObject.getPhoneNumber());
        volunteerPojo.setMobilephonenumber(volunteerValueObject.getMobilePhoneNumber());
        volunteerPojo.setEmail(volunteerValueObject.getEmail());
        volunteerPojo.setDatetraining(volunteerValueObject.getDateTraining());
        volunteerPojo.setPostalcode(volunteerValueObject.getPostalCode());
        volunteerPojo.setCity(volunteerValueObject.getCity());
        volunteerPojo.setStreetname(volunteerValueObject.getStreetName());
        volunteerPojo.setHousenr(volunteerValueObject.getHouseNr());
        volunteerPojo.setLog(volunteerValueObject.getLog());
        volunteerPojo.setJob(volunteerValueObject.getJob());
        volunteerPojo.setIsclassassistant(volunteerValueObject.getClassAssistant());
        volunteerPojo.setIstaalmaatje(volunteerValueObject.getTaalmaatje());
    }

    public VolunteerValueObject getVolunteerValueObject() {
        return volunteerValueObject;
    }

    public VolunteerPojo getVolunteerPojo() {
        return volunteerPojo;
    }
}
