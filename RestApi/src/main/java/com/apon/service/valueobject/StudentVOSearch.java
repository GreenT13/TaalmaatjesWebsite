package com.apon.service.valueobject;

import com.apon.service.valueobject.database.StudentDVO;
import com.apon.service.valueobject.database.VolunteerMatchDVO;

public class StudentVOSearch {
    private StudentDVO studentDVO;
    private VolunteerMatchDVO currentVolunteerMatchDVO;

    public StudentDVO getStudentDVO() {
        return studentDVO;
    }

    public void setStudentDVO(StudentDVO studentDVO) {
        this.studentDVO = studentDVO;
    }

    public VolunteerMatchDVO getCurrentVolunteerMatchDVO() {
        return currentVolunteerMatchDVO;
    }

    public void setCurrentVolunteerMatchDVO(VolunteerMatchDVO currentVolunteerMatchDVO) {
        this.currentVolunteerMatchDVO = currentVolunteerMatchDVO;
    }
}
