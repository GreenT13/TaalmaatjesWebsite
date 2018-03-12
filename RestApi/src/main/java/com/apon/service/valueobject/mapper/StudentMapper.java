package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.service.valueobject.StudentValueObject;

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

    public void fillValueObjectWithPojo(StudentPojo studentPojo) {
        studentValueObject.setExternalIdentifier(studentPojo.getExternalidentifier());
        studentValueObject.setFirstName(studentPojo.getFirstname());
        studentValueObject.setInsertion(studentPojo.getInsertion());
        studentValueObject.setLastName(studentPojo.getLastname());
        studentValueObject.setGender(studentPojo.getGender());
        studentValueObject.setDateOfBirth(studentPojo.getDateofbirth());
        studentValueObject.setGroupIdentification(studentPojo.getGroupidentification());
        studentValueObject.setHasQuit(studentPojo.getHasquit());
    }

    public void fillPojoWithValueObject(StudentValueObject studentValueObject) {
        studentPojo.setExternalidentifier(studentValueObject.getExternalIdentifier());
        studentPojo.setFirstname(studentValueObject.getFirstName());
        studentPojo.setInsertion(studentValueObject.getInsertion());
        studentPojo.setLastname(studentValueObject.getLastName());
        studentPojo.setGender(studentValueObject.getGender());
        studentPojo.setDateofbirth(studentValueObject.getDateOfBirth());
        studentPojo.setGroupidentification(studentValueObject.getGroupIdentification());
        studentPojo.setHasquit(studentValueObject.getHasQuit());
    }
}
