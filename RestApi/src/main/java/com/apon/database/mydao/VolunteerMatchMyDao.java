package com.apon.database.mydao;

import com.apon.database.generated.tables.Student;
import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteermatch;
import com.apon.database.generated.tables.daos.VolunteermatchDao;
import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.ResultUtil;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<VolunteermatchPojo> getMatchForVolunteer(String volunteerExtId, Boolean sortAscending) {
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
     * Get all matches for a volunteer.
     * @param volunteerExtId The extId from the volunteer.
     * @param sortAscending Sort by dateStart. If null, it will not sort.
     * @return QueryResult&lt;VolunteermatchPojo, StudentPojo&gt;
     */
    public List<QueryResult<VolunteermatchPojo, StudentPojo>> getMatchForVolunteerWithStudent(String volunteerExtId, Boolean sortAscending) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .select(Student.STUDENT.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .join(Student.STUDENT).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId));

        if (sortAscending != null && sortAscending) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.asc());
        } else if (sortAscending != null) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.desc());
        }

        Map<VolunteermatchPojo, List<StudentPojo>> map = query.fetchGroups(
                        r -> r.into(Volunteermatch.VOLUNTEERMATCH).into(VolunteermatchPojo.class),
                        r -> r.into(Student.STUDENT).into(StudentPojo.class));

        // Create a list of query results.
        List<QueryResult<VolunteermatchPojo, StudentPojo>> list = new ArrayList<>();
        for (Map.Entry<VolunteermatchPojo, List<StudentPojo>> entry : map.entrySet()) {
            list.add(new QueryResult<>(entry.getKey(), entry.getValue().get(0)));
        }

        return list;
    }

    /**
     * Get al matches for a student, together with the volunteer.
     * @param studentExtId The extId from the student.
     * @param sortAscending Sort by dateStart. If null, it will not sort.
     * @return List&lt;QueryResult&lt;VolunteermatchPojo, VolunteerPojo&gt;&gt;
     */
    public List<QueryResult<VolunteermatchPojo, VolunteerPojo>> getMatchForStudent(String studentExtId, Boolean sortAscending) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .select(Volunteer.VOLUNTEER.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Student.STUDENT).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .where(Student.STUDENT.EXTERNALIDENTIFIER.eq(studentExtId));

        if (sortAscending != null && sortAscending) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.asc());
        } else if (sortAscending != null) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.desc());
        }

        Map<VolunteermatchPojo, List<VolunteerPojo>> map = query.fetchGroups(
                r -> r.into(Volunteermatch.VOLUNTEERMATCH).into(VolunteermatchPojo.class),
                r -> r.into(Volunteer.VOLUNTEER).into(VolunteerPojo.class)
        );

        // Create a list of query results.
        List<QueryResult<VolunteermatchPojo, VolunteerPojo>> list = new ArrayList<>();
        for (Map.Entry<VolunteermatchPojo, List<VolunteerPojo>> entry : map.entrySet()) {
            list.add(new QueryResult<>(entry.getKey(), entry.getValue().get(0)));
        }

        return list;
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

    /**
     * Retrieve a single VolunteerMatch based on extId's.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerMatchExtId The extId from the volunteerMatch.
     * @return VolunteerinstancePojo
     */
    public VolunteermatchPojo fetchByExtIds(String volunteerExtId, String volunteerMatchExtId) {
        List<VolunteermatchPojo> volunteermatchPojos = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .and(Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER.eq(volunteerMatchExtId))
                .fetchInto(Volunteermatch.VOLUNTEERMATCH).map(mapper());

        // We know there must be one. We do this to prevent IndexOutOfBoundsExceptions.
        if (volunteermatchPojos.size() != 1) {
            return null;
        }

        return volunteermatchPojos.get(0);
    }

    /**
     * Retrieve a VolunteerMatch with a corresponding Student.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerMatchExtId The extId from the match.
     * @return QueryResult&lt;VolunteermatchPojo, StudentPojo&gt;
     */
    public QueryResult<VolunteermatchPojo, StudentPojo> retrieveMatchWithStudent(String volunteerExtId, String volunteerMatchExtId) {
        Map<VolunteermatchPojo, List<StudentPojo>> map = using(configuration())
                .select(Volunteermatch.VOLUNTEERMATCH.fields())
                .select(Student.STUDENT.fields())
                .from(Volunteermatch.VOLUNTEERMATCH)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .join(Student.STUDENT).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .and(Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER.eq(volunteerMatchExtId))
                .fetchGroups(
                        r -> r.into(Volunteermatch.VOLUNTEERMATCH).into(VolunteermatchPojo.class),
                        r -> r.into(Student.STUDENT).into(StudentPojo.class)
                );
        // If the map has no elements.
        if (map.size() == 0) {
            return null;
        }

        Map.Entry<VolunteermatchPojo, List<StudentPojo>> entry = map.entrySet().iterator().next();
        return new QueryResult<>(entry.getKey(), entry.getValue().get(0));
    }
}
