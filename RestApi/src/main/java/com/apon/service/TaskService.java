package com.apon.service;

import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.QueryResult;
import com.apon.database.mydao.TaskMyDao;
import com.apon.database.mydao.VolunteerMyDao;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringVO;
import com.apon.service.valueobject.database.TaskDVO;
import com.apon.service.valueobject.database.mapper.TaskDVOMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "RedundantThrows"})
@Path("task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskService implements IService {
    private DbContext context;

    /**
     * Retrieve a task.
     * @param taskExtId The external identifier.
     * @return TaskDVO.
     */
    @GET
    @Path("{taskExtId}")
    @InjectContext
    public TaskDVO get(@PathParam("taskExtId") String taskExtId) throws Exception {
        // Get taskId
        TaskMyDao taskMyDao = new TaskMyDao(context);
        QueryResult<TaskPojo, VolunteerPojo> result = taskMyDao.retrieveTaskWithVolunteer(taskExtId);
        if (result == null) {
            throw new FunctionalException("TaskService.notFound.task");
        }

        TaskDVOMapper taskDVOMapper = new TaskDVOMapper();
        taskDVOMapper.setTaskPojo(result.getS());
        taskDVOMapper.setVolunteerPojo(result.getT());

        return taskDVOMapper.getTaskDVO();
    }

    /**
     * Add a Task.
     * @param taskDVO The task object.
     * @return The extId from the task that is inserted.
     */
    @PUT
    @InjectContext
    public StringVO addTask(TaskDVO taskDVO) throws Exception {
        TaskMyDao taskMyDao = new TaskMyDao(context);
        TaskDVOMapper taskDVOMapper = new TaskDVOMapper();

        // Retrieve the volunteerId
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(
                taskDVO.getVolunteerDVO().getExternalIdentifier());
        if (volunteerPojo == null) {
            throw new FunctionalException("TaskService.notFound.volunteer");
        }

        taskDVOMapper.setTaskDVO(taskDVO);
        taskDVOMapper.getTaskPojo().setVolunteerid(volunteerPojo.getVolunteerid());

        // Insert also fills the external identifier we return later.
        if (!taskMyDao.insertPojo(taskDVOMapper.getTaskPojo())) {
            throw new FunctionalException(taskMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();

        return new StringVO(taskDVOMapper.getTaskPojo().getExternalidentifier());
    }

    /**
     * Update a Task with a certain extId. Note that you can overwrite ALL fields on the task.
     * @param taskExtId The task.
     */
    @POST
    @Path("{taskExtId}")
    @InjectContext
    public void updateTask(@PathParam("taskExtId") String taskExtId,
                           TaskDVO taskDVO) throws Exception {
        // Retrieve the original task.
        TaskMyDao taskMyDao = new TaskMyDao(context);
        TaskPojo taskPojo = taskMyDao.fetchOneByExternalidentifier(taskExtId);
        if (taskPojo == null) {
            throw new FunctionalException("TaskService.notFound.task");
        }

        // Retrieve the volunteerId
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(
                taskDVO.getVolunteerDVO().getExternalIdentifier());
        if (volunteerPojo == null) {
            throw new FunctionalException("TaskService.notFound.volunteer");
        }

        // Task is valid, so we map return to pojo.
        TaskDVOMapper taskDVOMapper = new TaskDVOMapper();
        taskDVOMapper.setTaskPojo(taskPojo);
        taskDVOMapper.setTaskDVO(taskDVO);
        // Overwrite the volunteerId so you can set one task to another volunteer.
        taskDVOMapper.getTaskPojo().setVolunteerid(volunteerPojo.getVolunteerid());

        if (!taskMyDao.updatePojo(taskPojo)) {
            throw new FunctionalException(taskMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();
    }

    /**
     * Retrieve a list of tasks based on search parameters.
     * @param title Searched for in Task.title.
     * @param description Searched for in Task.description.
     * @param isFinished Whether the value of Task.isFinished is true or false.
     * @param volunteerExtId The volunteer it is linked to.
     * @return List&lt;TaskDVO&gt;
     */
    @GET
    @InjectContext
    public List<TaskDVO> advancedSearch(@QueryParam("title") String title,
                                        @QueryParam("description") String description,
                                        @QueryParam("isFinished") Boolean isFinished,
                                        @QueryParam("volunteerExtId") String volunteerExtId) {
        // Retrieve the list from the database.
        TaskMyDao taskMyDao = new TaskMyDao(context);
        Map<TaskPojo, List<VolunteerPojo>> taskPojos = taskMyDao.advancedSearch(title, description, isFinished, volunteerExtId);

        // Convert the list of pojo's to returns.
        List<TaskDVO> taskDVOS = new ArrayList();
        for (Map.Entry<TaskPojo, List<VolunteerPojo>> entry : taskPojos.entrySet()) {
            TaskDVOMapper taskDVOMapper = new TaskDVOMapper();
            taskDVOMapper.setTaskPojo(entry.getKey());
            taskDVOMapper.setVolunteerPojo(entry.getValue().get(0));
            taskDVOS.add(taskDVOMapper.getTaskDVO());
        }

        return taskDVOS;
    }

    /**
     * Delete a Task.
     * @param taskExtId The extId from the task.
     * @throws Exception FunctionException
     */
    @DELETE
    @Path("{taskExtId}")
    @InjectContext
    public void deleteTask(@PathParam("{taskExtId}") String taskExtId) throws Exception {
        // Retrieve the original task.
        TaskMyDao taskMyDao = new TaskMyDao(context);
        TaskPojo taskPojo = taskMyDao.fetchOneByExternalidentifier(taskExtId);
        if (taskPojo == null) {
            throw new FunctionalException("TaskService.notFound.task");
        }

        // Delete task
        if (!taskMyDao.deletePojo(taskPojo)) {
            throw new FunctionalException(taskMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();
    }

    @Override
    public DbContext getContext() {
        return context;
    }

    @Override
    public void setContext(DbContext context) {
        this.context = context;
    }
}
