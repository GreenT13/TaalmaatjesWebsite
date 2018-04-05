package com.apon.service.valueobject.database.mapper;

import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.service.valueobject.database.TaskDVO;

public class TaskDVOMapper {
    private TaskDVO taskDVO;
    private TaskPojo taskPojo;

    public TaskDVOMapper() {
        taskDVO = new TaskDVO();
        taskPojo = new TaskPojo();
    }

    public TaskDVO getTaskDVO() {
        return taskDVO;
    }

    public TaskPojo getTaskPojo() {
        return taskPojo;
    }

    public void setTaskDVO(TaskDVO taskDVO) {
        this.taskDVO = taskDVO;
        fillPojoWithDVO(taskDVO);
    }

    public void setTaskPojo(TaskPojo taskPojo) {
        this.taskPojo = taskPojo;
        fillDVOWithPojo(taskPojo);
    }

    private void fillDVOWithPojo(TaskPojo taskPojo) {
        taskDVO.setExternalIdentifier(taskPojo.getExternalidentifier());
        taskDVO.setTitle(taskPojo.getTitle());
        taskDVO.setDescription(taskPojo.getDescription());
        taskDVO.setIsFinished(taskPojo.getIsfinished());
        taskDVO.setDateToBeFinished(taskPojo.getDatetobefinished());
    }

    private void fillPojoWithDVO(TaskDVO taskDVO) {
        taskPojo.setExternalidentifier(taskDVO.getExternalIdentifier());
        taskPojo.setTitle(taskDVO.getTitle());
        taskPojo.setDescription(taskDVO.getDescription());
        taskPojo.setIsfinished(taskDVO.getIsFinished());
        taskPojo.setDatetobefinished(taskDVO.getDateToBeFinished());
    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerPojo(volunteerPojo);
        this.taskDVO.setVolunteerDVO(volunteerDVOMapper.getVolunteerDVO());
    }

}
