package com.apon.database.mydao;

import com.apon.database.generated.tables.Task;
import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.daos.TaskDao;
import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.database.generated.tables.records.TaskRecord;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.ResultUtil;
import org.jooq.SelectWhereStep;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;

import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.using;

@SuppressWarnings("unused")
public class TaskMyDao extends TaskDao {
    private final static Integer STARTING_EXT_ID = 8001;
    private ResultObject resultObject;

    public TaskMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    public ResultObject getResultObject() {
        return resultObject;
    }

    @SuppressWarnings("Duplicates")
    private boolean generateIds(TaskPojo taskPojo) {
        if (taskPojo.getTaskid() == null) {
            Integer maxId = getMaxId();
            taskPojo.setTaskid(maxId != null ? maxId + 1 : 0);
        }

        if (taskPojo.getExternalidentifier() == null) {
            String maxExtId = getMaxExtId();
            if (maxExtId == null) {
                maxExtId = STARTING_EXT_ID.toString();
            } else {
                maxExtId = String.valueOf(Integer.valueOf(maxExtId) + 1);
            }
            taskPojo.setExternalidentifier(maxExtId);
        }

        return true;
    }

    private Integer getMaxId() {
        return using(configuration())
                .select(Task.TASK.TASKID.max())
                .from(Task.TASK)
                .fetchOne(0, Integer.class);
    }

    private String getMaxExtId() {
        return using(configuration())
                .select(Task.TASK.EXTERNALIDENTIFIER.cast(MySQLDataType.INT).max())
                .from(Task.TASK)
                .fetchOne(0, String.class);
    }

    private boolean validatePojo(TaskPojo taskPojo) {
        if (taskPojo.getIsfinished() == null) {
            taskPojo.setIsfinished(false);
        }

        if (taskPojo.getTitle() == null) {
            resultObject = ResultUtil.createErrorResult("TaskMyDao.validate.title");
            return false;
        }

        if (taskPojo.getVolunteerid() == null) {
            resultObject = ResultUtil.createErrorResult("TaskMyDao.validate.volunteer");
            return false;
        }

        return true;
    }

    public boolean insertPojo(TaskPojo taskPojo) {
        if (!validatePojo(taskPojo)) {
            // Result object is already set.
            return false;
        }

        if (!generateIds(taskPojo)) {
            resultObject = ResultUtil.createErrorResult("TaskMyDao.generateId.error");
            return false;
        }

        try {
            super.insert(taskPojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("TaskMyDao.insert.error", e);
            return false;
        }

        return true;
    }

    public boolean updatePojo(TaskPojo taskPojo) {
        if (!validatePojo(taskPojo)) {
            // Result object is already set.
            return false;
        }

        try {
            super.update(taskPojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("TaskMyDao.update.error", e);
        }

        return true;
    }

    /**
     * Get all tasks for a volunteer, ordered by dateToBeFinished (asc).
     * @param volunteerExtId The extId from the volunteer.
     * @return List&lt;TaskPojo&gt;
     */
    public List<TaskPojo> getTasksForVolunteer(String volunteerExtId) {
        return  using(configuration())
                .select(Task.TASK.fields())
                .from(Volunteer.VOLUNTEER)
                .join(Task.TASK).on(Task.TASK.VOLUNTEERID.eq(Volunteer.VOLUNTEER.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .orderBy(Task.TASK.DATETOBEFINISHED.asc())
                .fetchInto(Task.TASK).map(mapper());
    }

    /**
     * Search tasks based on given input.
     * @param title Searched for in Task.title. Will lower performance a lot if filled.
     * @param description Searched for in Task.description. Will lower performance a lot if filled.
     * @param isFinished Must equal Task.isFinished (if not null).
     * @param volunteerExtId Task must belong to this volunteer.
     * @return List&lt;TaskPojo&gt;
     */
    public List<TaskPojo> advancedSearch(String title, String description, Boolean isFinished, String volunteerExtId) {
        SelectWhereStep<TaskRecord> query = using(configuration()).selectFrom(Task.TASK);

        // Search for title. Since this is hard to search for, we use %description%.
        if (title != null && title.trim().length() > 0) {
            query.where(Task.TASK.TITLE.lower().like("%" + title.toLowerCase() + "%"));
        }

        // Search for description. Since this is hard to search for, we use %description%.
        if(description != null && description.trim().length() > 0) {
            query.where(Task.TASK.DESCRIPTION.lower().like("%" + description.toLowerCase() + "%"));
        }

        if (isFinished != null) {
            query.where(Task.TASK.ISFINISHED.eq(isFinished));
        }

        if(volunteerExtId != null) {
            query.where(Task.TASK.VOLUNTEERID.eq(
                    select(Volunteer.VOLUNTEER.VOLUNTEERID).from(Volunteer.VOLUNTEER)
                            .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))));
        }

        return query.orderBy(Task.TASK.DATETOBEFINISHED.asc()).limit(50).fetch().map(mapper());
    }

//    If I ever want to use the functions below, I should return how many rows it deleted / updated. This is really useful for finding bugs.
//    public void deleteTask(int taskId) {
//        using(configuration())
//                .deleteFrom(Task.TASK)
//                .where(Task.TASK.TASKID.eq(taskId))
//                .returning()
//                .fetch();
//    }
//
//    public void finishTask(int taskId, boolean isFinished) {
//        using(configuration())
//                .update(Task.TASK)
//                .set(Task.TASK.ISFINISHED, isFinished)
//                .where(Task.TASK.TASKID.eq(taskId))
//                .returning()
//                .fetch();
//    }
}
