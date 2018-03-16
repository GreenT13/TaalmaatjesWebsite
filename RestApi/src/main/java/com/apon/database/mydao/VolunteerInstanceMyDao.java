package com.apon.database.mydao;

import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteerinstance;
import com.apon.database.generated.tables.daos.VolunteerinstanceDao;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.DateTimeUtil;
import com.apon.util.ResultUtil;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;

import static org.jooq.impl.DSL.using;

@SuppressWarnings("unused")
public class VolunteerInstanceMyDao extends VolunteerinstanceDao {
    private ResultObject resultObject;

    public VolunteerInstanceMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    public ResultObject getResultObject() {
        return resultObject;
    }

    @SuppressWarnings("Duplicates")
    private boolean generateIds(VolunteerinstancePojo volunteerinstancePojo) {
        // We cannot generate an id if the volunteerId is unknown.
        if (volunteerinstancePojo.getVolunteerid() == null) {
            return false;
        }

        if (volunteerinstancePojo.getVolunteerinstanceid() == null) {
            Integer maxId = getMaxId(volunteerinstancePojo.getVolunteerid());
            volunteerinstancePojo.setVolunteerinstanceid(maxId != null ? maxId + 1 : 0);
        }

        if (volunteerinstancePojo.getExternalidentifier() == null) {
            String maxExtId = getMaxExtId(volunteerinstancePojo.getVolunteerid());
            if (maxExtId == null) {
                maxExtId = "1";
            } else {
                maxExtId = String.valueOf(Integer.valueOf(maxExtId) + 1);
            }
            volunteerinstancePojo.setExternalidentifier(maxExtId);
        }

        return true;
    }

    private Integer getMaxId(int volunteerId) {
        return using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID.max())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(volunteerId))
                .fetchOne(0, Integer.class);
    }

    private String getMaxExtId(int volunteerId) {
        return using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER.cast(MySQLDataType.INT).max())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(volunteerId))
                .fetchOne(0, String.class);
    }

    private boolean validatePojo(VolunteerinstancePojo volunteerinstancePojo) {
        if (volunteerinstancePojo.getVolunteerid() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerInstanceMyDao.validate.volunteer");
            return false;
        }

        if (volunteerinstancePojo.getDatestart() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerInstanceMyDao.validate.dateStart");
            return false;
        }

        if (DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend()) < 0) {
            resultObject = ResultUtil.createErrorResult("VolunteerInstanceMyDao.validate.dateEndBeforeDateStart");
            return false;
        }

        return true;
    }

    public boolean insertPojo(VolunteerinstancePojo volunteerinstancePojo) {
        if (!validatePojo(volunteerinstancePojo)) {
            // Result object is already set.
            return false;
        }

        if (!generateIds(volunteerinstancePojo)) {
            resultObject = ResultUtil.createErrorResult("VolunteerInstanceMyDao.generateId.error");
            return false;
        }

        try {
            super.insert(volunteerinstancePojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("VolunteerInstanceMyDao.insert.error", e);
            return false;
        }

        return true;
    }

    /**
     * Calls {@link #getInstanceForVolunteer(String, Boolean) getInstanceForVolunteer} with sortAscending=null.
     * @param volunteerExtId The extId from the volunteer.
     * @return List&lt;VolunteerinstancePojo&gt;
     */
    public List<VolunteerinstancePojo> getInstanceForVolunteer(String volunteerExtId) {
        return getInstanceForVolunteer(volunteerExtId, null);
    }

    /**
     * Get all instances for a volunteer.
     * @param volunteerExtId The extId from the volunteer.
     * @param sortAscending Sort by dateStart. If null, it will not sort.
     * @return List&lt;VolunteerinstancePojo&gt;
     */
    public List<VolunteerinstancePojo> getInstanceForVolunteer(String volunteerExtId, Boolean sortAscending) {
        SelectConditionStep<Record> query = using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.fields())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .innerJoin(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId));

        if (sortAscending != null && sortAscending) {
            query.orderBy(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.asc());
        } else if (sortAscending != null) {
            query.orderBy(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.desc());
        }

        return query.fetchInto(Volunteerinstance.VOLUNTEERINSTANCE).map(mapper());
    }

    /**
     * Retrieve a single VolunteerInstance based on extId's.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerInstanceExtId The extId from the volunteerInstance.
     * @return VolunteerinstancePojo
     */
    public VolunteerinstancePojo fetchByExtIds(String volunteerExtId, String volunteerInstanceExtId) {
        List<VolunteerinstancePojo> volunteerinstancePojos = using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.fields())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .and(Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER.eq(volunteerInstanceExtId))
                .fetchInto(Volunteerinstance.VOLUNTEERINSTANCE).map(mapper());

        // We know there must be one. We do this to prevent IndexOutOfBoundsExceptions.
        if (volunteerinstancePojos.size() != 1) {
            return null;
        }

        return volunteerinstancePojos.get(0);
    }

    /**
     * Return the number of volunteers that are active today.
     * @return Integer.
     */
    public Integer getActiveToday() {
        return using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID.count())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                // Active today: dateStart <= current_date and (dateEnd is null || current_date <= dateEnd)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.le(DSL.currentDate()))
                .and(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.isNull()
                        .or(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.ge(DSL.currentDate())))
                .fetchOne(0, Integer.class);
    }
}
