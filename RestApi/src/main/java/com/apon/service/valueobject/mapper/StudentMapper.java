package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.mydao.QueryResult;
import com.apon.service.valueobject.StudentValueObject;
import com.apon.service.valueobject.VolunteerMatchValueObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class StudentMapper {
    private StudentValueObject studentValueObject;
    private StudentPojo studentPojo;

    public StudentMapper() {
        studentValueObject = new StudentValueObject();
        studentPojo = new StudentPojo();
    }

    public StudentValueObject getStudentValueObject() {
        return studentValueObject;
    }

    public StudentPojo getStudentPojo() {
        return studentPojo;
    }

    public void setStudentValueObject(StudentValueObject studentValueObject) {
        this.studentValueObject = studentValueObject;
        fillPojoWithValueObject(studentValueObject);
    }

    public void setStudentPojo(StudentPojo studentPojo) {
        this.studentPojo = studentPojo;
        fillValueObjectWithPojo(studentPojo);
    }

    private void fillValueObjectWithPojo(StudentPojo studentPojo) {
        studentValueObject.setExternalIdentifier(studentPojo.getExternalidentifier());
        studentValueObject.setFirstName(studentPojo.getFirstname());
        studentValueObject.setInsertion(studentPojo.getInsertion());
        studentValueObject.setLastName(studentPojo.getLastname());
        studentValueObject.setGender(studentPojo.getGender());
        studentValueObject.setDateOfBirth(studentPojo.getDateofbirth());
        studentValueObject.setGroupIdentification(studentPojo.getGroupidentification());
        studentValueObject.setHasQuit(studentPojo.getHasquit());
    }

    private void fillPojoWithValueObject(StudentValueObject studentValueObject) {
        studentPojo.setExternalidentifier(studentValueObject.getExternalIdentifier());
        studentPojo.setFirstname(studentValueObject.getFirstName());
        studentPojo.setInsertion(studentValueObject.getInsertion());
        studentPojo.setLastname(studentValueObject.getLastName());
        studentPojo.setGender(studentValueObject.getGender());
        studentPojo.setDateofbirth(studentValueObject.getDateOfBirth());
        studentPojo.setGroupidentification(studentValueObject.getGroupIdentification());
        studentPojo.setHasquit(studentValueObject.getHasQuit());
    }

    public void setMatchList(List<QueryResult<VolunteermatchPojo, VolunteerPojo>> listVolunteerMatchPojo) {
        List<VolunteerMatchValueObject> listVolunteerMatchValueObject = new ArrayList();

        for (QueryResult<VolunteermatchPojo, VolunteerPojo> queryResult : listVolunteerMatchPojo) {
            VolunteerMatchMapper volunteerMatchMapper = new VolunteerMatchMapper();
            volunteerMatchMapper.setVolunteermatchPojo(queryResult.getS());
            volunteerMatchMapper.setVolunteerPojo(queryResult.getT());

            listVolunteerMatchValueObject.add(volunteerMatchMapper.getVolunteerMatchValueObject());
        }

        studentValueObject.setVolunteerMatchValueObjects(listVolunteerMatchValueObject);
    }
}
