package com.apon.service.valueobject.database.mapper;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.service.valueobject.database.StudentDVO;

public class StudentDVOMapper {
    private StudentDVO studentDVO;
    private StudentPojo studentPojo;

    public StudentDVOMapper() {
        studentDVO = new StudentDVO();
        studentPojo = new StudentPojo();
    }

    public StudentDVO getStudentDVO() {
        return studentDVO;
    }

    public StudentPojo getStudentPojo() {
        return studentPojo;
    }

    public void setStudentDVO(StudentDVO studentDVO) {
        this.studentDVO = studentDVO;
        fillPojoWithDVO(studentDVO);
    }

    public void setStudentPojo(StudentPojo studentPojo) {
        this.studentPojo = studentPojo;
        fillDVOWithPojo(studentPojo);
    }

    private void fillDVOWithPojo(StudentPojo studentPojo) {
        studentDVO.setExternalIdentifier(studentPojo.getExternalidentifier());
        studentDVO.setFirstName(studentPojo.getFirstname());
        studentDVO.setInsertion(studentPojo.getInsertion());
        studentDVO.setLastName(studentPojo.getLastname());
        studentDVO.setGender(studentPojo.getGender());
        studentDVO.setDateOfBirth(studentPojo.getDateofbirth());
        studentDVO.setGroupIdentification(studentPojo.getGroupidentification());
        studentDVO.setHasQuit(studentPojo.getHasquit());
    }

    private void fillPojoWithDVO(StudentDVO studentDVO) {
        studentPojo.setExternalidentifier(studentDVO.getExternalIdentifier());
        studentPojo.setFirstname(studentDVO.getFirstName());
        studentPojo.setInsertion(studentDVO.getInsertion());
        studentPojo.setLastname(studentDVO.getLastName());
        studentPojo.setGender(studentDVO.getGender());
        studentPojo.setDateofbirth(studentDVO.getDateOfBirth());
        studentPojo.setGroupidentification(studentDVO.getGroupIdentification());
        studentPojo.setHasquit(studentDVO.getHasQuit());
    }

}
