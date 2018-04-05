package com.apon.service.valueobject;

import com.apon.service.valueobject.database.StudentDVO;
import com.apon.service.valueobject.database.VolunteerMatchDVO;

import java.util.List;

public class StudentVOGet {
    private StudentDVO studentDVO;
    private List<VolunteerMatchDVO> volunteerMatchDVOS;

    public StudentDVO getStudentDVO() {
        return studentDVO;
    }

    public void setStudentDVO(StudentDVO studentDVO) {
        this.studentDVO = studentDVO;
    }

    public List<VolunteerMatchDVO> getVolunteerMatchDVOS() {
        return volunteerMatchDVOS;
    }

    public void setVolunteerMatchDVOS(List<VolunteerMatchDVO> volunteerMatchDVOS) {
        this.volunteerMatchDVOS = volunteerMatchDVOS;
    }
}