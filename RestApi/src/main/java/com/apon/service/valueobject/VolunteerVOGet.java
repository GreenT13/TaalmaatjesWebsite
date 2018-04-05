package com.apon.service.valueobject;

import com.apon.service.valueobject.database.TaskDVO;
import com.apon.service.valueobject.database.VolunteerDVO;
import com.apon.service.valueobject.database.VolunteerInstanceDVO;
import com.apon.service.valueobject.database.VolunteerMatchDVO;

import java.util.List;

public class VolunteerVOGet {
    private VolunteerDVO volunteerDVO;

    // Lists of instances, matches and tasks.
    private List<VolunteerInstanceDVO> volunteerInstanceDVOS;
    private List<VolunteerMatchDVO> volunteerMatchDVOS;
    private List<TaskDVO> taskDVOS;

    public VolunteerDVO getVolunteerDVO() {
        return volunteerDVO;
    }

    public void setVolunteerDVO(VolunteerDVO volunteerDVO) {
        this.volunteerDVO = volunteerDVO;
    }

    public List<VolunteerInstanceDVO> getVolunteerInstanceDVOS() {
        return volunteerInstanceDVOS;
    }

    public void setVolunteerInstanceDVOS(List<VolunteerInstanceDVO> volunteerInstanceDVOS) {
        this.volunteerInstanceDVOS = volunteerInstanceDVOS;
    }

    public List<VolunteerMatchDVO> getVolunteerMatchDVOS() {
        return volunteerMatchDVOS;
    }

    public void setVolunteerMatchDVOS(List<VolunteerMatchDVO> volunteerMatchDVOS) {
        this.volunteerMatchDVOS = volunteerMatchDVOS;
    }

    public List<TaskDVO> getTaskDVOS() {
        return taskDVOS;
    }

    public void setTaskDVOS(List<TaskDVO> taskDVOS) {
        this.taskDVOS = taskDVOS;
    }
}
