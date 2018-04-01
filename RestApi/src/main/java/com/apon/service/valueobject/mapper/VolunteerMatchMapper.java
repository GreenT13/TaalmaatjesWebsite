package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
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
        fillPojoWithValueObject(volunteerMatchValueObject);
    }

    public void setVolunteermatchPojo(VolunteermatchPojo volunteermatchPojo) {
        this.volunteermatchPojo = volunteermatchPojo;
        fillValueObjectWithPojo(volunteermatchPojo);
    }

    private void fillValueObjectWithPojo(VolunteermatchPojo volunteermatchPojo) {
        volunteerMatchValueObject.setExternalIdentifier(volunteermatchPojo.getExternalidentifier());
        volunteerMatchValueObject.setDateStart(volunteermatchPojo.getDatestart());
        volunteerMatchValueObject.setDateEnd(volunteermatchPojo.getDateend());
    }

    private void fillPojoWithValueObject(VolunteerMatchValueObject volunteerMatchValueObject) {
        volunteermatchPojo.setDatestart(volunteerMatchValueObject.getDateStart());
        volunteermatchPojo.setDateend(volunteerMatchValueObject.getDateEnd());
        volunteermatchPojo.setExternalidentifier(volunteerMatchValueObject.getExternalIdentifier());
    }

    public void setStudentPojo(StudentPojo studentPojo) {
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.setStudentPojo(studentPojo);
        volunteerMatchValueObject.setStudentValueObject(studentMapper.getStudentValueObject());
    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerPojo(volunteerPojo);
        volunteerMatchValueObject.setVolunteerValueObject(volunteerMapper.getVolunteerValueObject());
    }

}
