package com.apon.database.mydao;

import com.apon.database.generated.tables.Student;
import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteermatch;
import com.apon.database.generated.tables.daos.VolunteermatchDao;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.ResultUtil;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;

import static org.jooq.impl.DSL.using;

@SuppressWarnings("unused")
public class VolunteerMatchMyDao extends VolunteermatchDao {
    private ResultObject resultObject;

    public VolunteerMatchMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    public ResultObject getResultObject() {
        return resultObject;
    }

    @SuppressWarnings("Duplicates")
    private boolean generateIds(VolunteermatchPojo volunteermatchPojo) {
        // We cannot generate an id if the volunteerId is unknown.
        if (volunteermatchPojo.getVolunteerid() == null) {
            return false;
        }

        if (volunteermatchPojo.getVolunteermatchid() == null) {
            Integer maxId = getMaxId(volunteermatchPojo.getVolunteerid());
            volunteermatchPojo.setVolunteermatchid(maxId != null ? maxId + 1 : 0);
        }

        if (volunteermatchPojo.getExternalidentifier() == null) {
            String maxExtId = getMaxExtId(volunteermatchPojo.getVolunteerid());
            if (maxExtId == null) {
                maxExtId = "1";
            } else {
                maxExtId = String.valueOf(Integer.valueOf(maxExtId) + 1);
            }
            volunteermatchPojo.setExternalidentifier(maxExtId);
        }

        return true;
    }

    private Integer getMaxId(Integer volunteerId) {
        return using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID.max())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .where(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.eq(volunteerId))
                .fetchOne(0, Integer.class);
    }

    private String getMaxExtId(Integer volunteerId) {
        return using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER.cast(MySQLDataType.INT).max())
                .select(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID.max())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .where(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.eq(volunteerId))
                .fetchOne(0, String.class);
    }

    private boolean validatePojo(VolunteermatchPojo volunteermatchPojo) {
        if (volunteermatchPojo.getVolunteerid() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMatchMyDao.validate.volunteer");
            return false;
        }

        if (volunteermatchPojo.getStudentid() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMatchMyDao.validate.student");
            return false;
        }

        if (volunteermatchPojo.getDatestart() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMatchMyDao.validate.dateStart");
            return false;
        }

        return true;
    }

    public boolean insertPojo(VolunteermatchPojo volunteermatchPojo) {
        if (!validatePojo(volunteermatchPojo)) {
            // Result object is already set.
            return false;
        }

        if (!generateIds(volunteermatchPojo)) {
            resultObject = ResultUtil.createErrorResult("VolunteerMatchMyDao.generateId.error");
            return false;
        }

        try {
            super.insert(volunteermatchPojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("VolunteerMatchMyDao.insert.error", e);
            return false;
        }

        return true;
    }

    /**
     * Calls {@link #getMatchForVolunteer(String, Boolean) getMatchForVolunteer} with sortAscending=null.
     * @param volunteerExtId The extId from the volunteer.
     * @return List&lt;VolunteermatchPojo&gt;
     */
    public List<VolunteermatchPojo> getMatchForVolunteer(String volunteerExtId) {
        return getMatchForVolunteer(volunteerExtId, null);
    }

    /**
     * Get all matches for a volunteer.
     * @param volunteerExtId The extId from the volunteer.
     * @param sortAscending Sort by dateStart. If null, it will not sort.
     * @return List&lt;VolunteermatchPojo&gt;
     */
    public List<VolunteermatchPojo> getMatchForVolunteer(String volunteerExtId, Boolean sortAscending) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .innerJoin(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId));

        if (sortAscending != null && sortAscending) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.asc());
        } else if (sortAscending != null) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.desc());
        }

        return query.fetchInto(Volunteermatch.VOLUNTEERMATCH).map(mapper());
    }

    /**
     * Get al matches for a student.
     * @param studentExtId The extid from the student.
     * @param sortAscending Sort by dateStart. If null, it will not sort.
     * @return List&lt;VolunteermatchPojo&gt;
     */
    public List<VolunteermatchPojo> getMatchForStudent(String studentExtId, Boolean sortAscending) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Student.STUDENT).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .where(Student.STUDENT.EXTERNALIDENTIFIER.eq(studentExtId));

        if (sortAscending != null && sortAscending) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.asc());
        } else if (sortAscending != null) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.desc());
        }

        return query.fetchInto(Volunteermatch.VOLUNTEERMATCH).map(mapper());
    }

    /**
     * Retrieve all matches between a certain volunteer and student.
     * @param volunteerExtId The extId from the volunteer.
     * @param studentExtId The extId from the student.
     * @return List&lt;VolunteermatchPojo&gt;
     */
    public List<VolunteermatchPojo> getMatchForVolunteerAndStudent(String volunteerExtId, String studentExtId) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .join(Student.STUDENT).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .and(Student.STUDENT.EXTERNALIDENTIFIER.eq(studentExtId));

        return query.fetchInto(Volunteermatch.VOLUNTEERMATCH).map(mapper());
    }

    /**
     * Get the number of matches that are active today.
     * @return Integer
     */
    public Integer getMatchToday() {
        return using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID.count())
                .from(Volunteermatch.VOLUNTEERMATCH)
                // Active today: dateStart <= current_date and (dateEnd is null || current_date <= dateEnd)
                .where(Volunteermatch.VOLUNTEERMATCH.DATESTART.le(DSL.currentDate()))
                .and(Volunteermatch.VOLUNTEERMATCH.DATEEND.isNull()
                        .or(Volunteermatch.VOLUNTEERMATCH.DATEEND.ge(DSL.currentDate())))
                .fetchOne(0, Integer.class);
    }
}
