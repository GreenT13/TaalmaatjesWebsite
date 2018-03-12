package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.service.valueobject.VolunteerMatchValueObject;

public class VolunteerMatchMapper {
    private VolunteerMatchValueObject volunteerMatchValueObject;
    private VolunteermatchPojo volunteermatchPojo;

    public VolunteerMatchMapper() {
        volunteerMatchValueObject = new VolunteerMatchValueObject();
        volunteermatchPojo = new VolunteermatchPojo();
    }

    public VolunteerMatchValueObject getVolunteerMatchValueObject() {
        return volunteerMatchValueObject;
    }

    public VolunteermatchPojo getVolunteermatchPojo() {
        return volunteermatchPojo;
    }

    public void setVolunteerMatchValueObject(VolunteerMatchValueObject volunteerMatchValueObject) {
        this.volunteerMatchValueObject = volunteerMatchValueObject;
    }

    public void setVolunteermatchPojo(VolunteermatchPojo volunteermatchPojo) {
        this.volunteermatchPojo = volunteermatchPojo;
    }


    public void fillValueObjectWithPojo(VolunteermatchPojo volunteermatchPojo) {
        volunteerMatchValueObject.setExternalIdentifier(volunteermatchPojo.getExternalidentifier());
        volunteerMatchValueObject.setDateStart(volunteermatchPojo.getDatestart());
        volunteerMatchValueObject.setDateEnd(volunteermatchPojo.getDateend());
    }

    public void fillPojoWithValueObject(VolunteerMatchValueObject volunteerMatchValueObject) {
        volunteermatchPojo.setDatestart(volunteerMatchValueObject.getDateStart());
        volunteermatchPojo.setDateend(volunteerMatchValueObject.getDateEnd());
        volunteermatchPojo.setExternalidentifier(volunteerMatchValueObject.getExternalIdentifier());
    }

    public void setStudent(String studentExtId) {
        volunteerMatchValueObject.setStudentExtId(studentExtId);
    }
}
