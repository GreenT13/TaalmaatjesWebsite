package com.apon.database.mydao;

import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteerinstance;
import com.apon.database.generated.tables.daos.VolunteerinstanceDao;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.ResultUtil;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.using;

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

    public boolean insertPojo(VolunteerinstancePojo volunteerinstancePojo) {
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

    public List<VolunteerinstancePojo> getInstanceForVolunteer(String volunteerExtId) {
        return getInstanceForVolunteer(volunteerExtId, null);
    }

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

    public VolunteerinstancePojo fetchByExtIds(String volunteerExtId, String volunteerInstanceExtId) {
        Map<VolunteerinstancePojo, List<VolunteerPojo>> result =  using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.fields())
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID))
                .where(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.eq(volunteerExtId))
                .and(Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER.eq(volunteerInstanceExtId))
                .fetchGroups(
                        r -> r.into(Volunteerinstance.VOLUNTEERINSTANCE).into(VolunteerinstancePojo.class),
                        r -> r.into(Volunteer.VOLUNTEER).into(VolunteerPojo.class)
                );

        // We know there can only be one result.
        return result.entrySet().iterator().next().getKey();
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
