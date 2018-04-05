package com.apon.service.valueobject.database.mapper;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.service.valueobject.database.VolunteerMatchDVO;

public class VolunteerMatchDVOMapper {
    private VolunteerMatchDVO volunteerMatchDVO;
    private VolunteermatchPojo volunteermatchPojo;

    public VolunteerMatchDVOMapper() {
        volunteerMatchDVO = new VolunteerMatchDVO();
        volunteermatchPojo = new VolunteermatchPojo();
    }

    public VolunteerMatchDVO getVolunteerMatchDVO() {
        return volunteerMatchDVO;
    }

    public VolunteermatchPojo getVolunteermatchPojo() {
        return volunteermatchPojo;
    }

    public void setVolunteerMatchDVO(VolunteerMatchDVO volunteerMatchDVO) {
        this.volunteerMatchDVO = volunteerMatchDVO;
        fillPojoWithDVO(volunteerMatchDVO);
    }

    public void setVolunteermatchPojo(VolunteermatchPojo volunteermatchPojo) {
        this.volunteermatchPojo = volunteermatchPojo;
        fillDVOWithPojo(volunteermatchPojo);
    }

    private void fillDVOWithPojo(VolunteermatchPojo volunteermatchPojo) {
        volunteerMatchDVO.setExternalIdentifier(volunteermatchPojo.getExternalidentifier());
        volunteerMatchDVO.setDateStart(volunteermatchPojo.getDatestart());
        volunteerMatchDVO.setDateEnd(volunteermatchPojo.getDateend());
    }

    private void fillPojoWithDVO(VolunteerMatchDVO volunteerMatchDVO) {
        volunteermatchPojo.setDatestart(volunteerMatchDVO.getDateStart());
        volunteermatchPojo.setDateend(volunteerMatchDVO.getDateEnd());
        volunteermatchPojo.setExternalidentifier(volunteerMatchDVO.getExternalIdentifier());
    }

    public void setStudentPojo(StudentPojo studentPojo) {
        StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
        studentDVOMapper.setStudentPojo(studentPojo);
        volunteerMatchDVO.setStudentDVO(studentDVOMapper.getStudentDVO());
    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerPojo(volunteerPojo);
        volunteerMatchDVO.setVolunteerDVO(volunteerDVOMapper.getVolunteerDVO());
    }

}
