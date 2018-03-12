package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.service.valueobject.TaskValueObject;

public class TaskMapper {
    private TaskValueObject taskValueObject;
    private TaskPojo taskPojo;

    public TaskMapper() {
        taskValueObject = new TaskValueObject();
        taskPojo = new TaskPojo();
    }

    public TaskValueObject getTaskValueObject() {
        return taskValueObject;
    }

    public TaskPojo getTaskPojo() {
        return taskPojo;
    }

    public void setTaskValueObject(TaskValueObject taskValueObject) {
        this.taskValueObject = taskValueObject;
        fillPojoWithValueObject(taskValueObject);
    }

    public void setTaskPojo(TaskPojo taskPojo) {
        this.taskPojo = taskPojo;
        fillValueObjectWithPojo(taskPojo);
    }

    public void fillValueObjectWithPojo(TaskPojo taskPojo) {
        taskValueObject.setTaskExtId(taskPojo.getExternalidentifier());
        taskValueObject.setTitle(taskPojo.getTitle());
        taskValueObject.setDescription(taskPojo.getDescription());
        taskValueObject.setFinished(taskPojo.getIsfinished());
        taskValueObject.setDateToBeFinished(taskPojo.getDatetobefinished());
    }

    public void fillPojoWithValueObject(TaskValueObject taskValueObject) {
        taskPojo.setExternalidentifier(taskValueObject.getTaskExtId());
        taskPojo.setTitle(taskValueObject.getTitle());
        taskPojo.setDescription(taskValueObject.getDescription());
        taskPojo.setIsfinished(taskValueObject.getFinished());
        taskPojo.setDatetobefinished(taskValueObject.getDateToBeFinished());
    }

}
