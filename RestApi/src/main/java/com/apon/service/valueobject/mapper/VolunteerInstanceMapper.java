package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.service.valueobject.VolunteerInstanceValueObject;

public class VolunteerInstanceMapper {
    private VolunteerInstanceValueObject volunteerInstanceValueObject;
    private VolunteerinstancePojo volunteerinstancePojo;

    public VolunteerInstanceMapper() {
        volunteerInstanceValueObject = new VolunteerInstanceValueObject();
        volunteerinstancePojo = new VolunteerinstancePojo();
    }

    public VolunteerInstanceValueObject getVolunteerInstanceValueObject() {
        return volunteerInstanceValueObject;
    }

    public VolunteerinstancePojo getVolunteerinstancePojo() {
        return volunteerinstancePojo;
    }

    public void setVolunteerInstanceValueObject(VolunteerInstanceValueObject volunteerInstanceValueObject) {
        this.volunteerInstanceValueObject = volunteerInstanceValueObject;
        fillPojoWithValueObject(volunteerInstanceValueObject);
    }

    public void setVolunteerinstancePojo(VolunteerinstancePojo volunteerinstancePojo) {
        this.volunteerinstancePojo = volunteerinstancePojo;
        fillValueObjectWithPojo(volunteerinstancePojo);
    }

    public void fillValueObjectWithPojo(VolunteerinstancePojo volunteerinstancePojo) {
        volunteerInstanceValueObject.setExternalIdentifier(volunteerinstancePojo.getExternalidentifier());
        volunteerInstanceValueObject.setDateStart(volunteerinstancePojo.getDatestart());
        volunteerInstanceValueObject.setDateEnd(volunteerinstancePojo.getDateend());
    }

    public void fillPojoWithValueObject(VolunteerInstanceValueObject volunteerInstanceValueObject) {
        volunteerinstancePojo.setExternalidentifier(volunteerInstanceValueObject.getExternalIdentifier());
        volunteerinstancePojo.setDatestart(volunteerInstanceValueObject.getDateStart());
        volunteerinstancePojo.setDateend(volunteerInstanceValueObject.getDateEnd());
    }

}
