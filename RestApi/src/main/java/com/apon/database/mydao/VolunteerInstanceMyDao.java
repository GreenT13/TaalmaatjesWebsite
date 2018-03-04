package com.apon.database.mydao;

import com.apon.database.generated.tables.Volunteerinstance;
import com.apon.database.generated.tables.daos.VolunteerinstanceDao;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.records.VolunteerinstanceRecord;
import com.apon.database.jooq.DbContext;
import com.apon.log.MyLogger;
import org.jooq.Configuration;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;

import static org.jooq.impl.DSL.using;

public class VolunteerInstanceMyDao extends VolunteerinstanceDao {

    public VolunteerInstanceMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    @Deprecated
    public VolunteerInstanceMyDao(Configuration configuration) {
        super(configuration);
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

    public Integer getIdFromExtId(int volunteerId, String volunteerInstanceExtId) {
        return using(configuration())
                .select(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID)
                .from(Volunteerinstance.VOLUNTEERINSTANCE)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(volunteerId))
                .and(Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER.eq(volunteerInstanceExtId))
                .fetchOne(0, Integer.class);
    }


    public boolean insertPojo(VolunteerinstancePojo volunteerinstancePojo) {
        if (!generateIds(volunteerinstancePojo)) {
            // Some kind of logError message?
            return false;
        }

        try {
            super.insert(volunteerinstancePojo);
        } catch (Exception e) {
            MyLogger.logError("Could not insert new volunteerinstance.", e);
            return false;
        }

        return true;
    }

    public List<VolunteerinstancePojo> getInstanceForVolunteer(int volunteerId) {
        return getInstanceForVolunteer(volunteerId, true);
    }

    public List<VolunteerinstancePojo> getInstanceForVolunteer(int volunteerId, boolean sortAscending) {
        SelectConditionStep<VolunteerinstanceRecord> query = using(configuration())
                .selectFrom(Volunteerinstance.VOLUNTEERINSTANCE)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(volunteerId));

        if (sortAscending) {
            query.orderBy(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.asc());
        } else {
            query.orderBy(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.desc());
        }

        return query.fetch().map(mapper());
    }

    @Override
    public List<VolunteerinstancePojo> fetchByVolunteerid(Integer... values) {
        return super.fetchByVolunteerid(values);
    }

    public VolunteerinstancePojo fetchByIds(int volunteerId, int volunteerInstanceId) {
        VolunteerinstanceRecord record =  using(configuration())
                .selectFrom(Volunteerinstance.VOLUNTEERINSTANCE)
                .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(volunteerId))
                .and(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID.eq(volunteerInstanceId))
                .fetchOne();

        return mapper().map(record);
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
