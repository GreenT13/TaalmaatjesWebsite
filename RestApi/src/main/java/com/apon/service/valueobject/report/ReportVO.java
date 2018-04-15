package com.apon.service.valueobject.report;

import com.apon.service.config.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;
import java.util.List;

public class ReportVO {
    @JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateStart;
    @JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateEnd;
    private List<ReportPersonVO> volunteers;
    private List<ReportPersonVO> students;

    public ReportVO(Date dateStart, Date dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<ReportPersonVO> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<ReportPersonVO> volunteers) {
        this.volunteers = volunteers;
    }

    public List<ReportPersonVO> getStudents() {
        return students;
    }

    public void setStudents(List<ReportPersonVO> students) {
        this.students = students;
    }
}
