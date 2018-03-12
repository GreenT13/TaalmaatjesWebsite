package com.apon.database.mydao;

import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteermatch;
import com.apon.database.generated.tables.daos.VolunteermatchDao;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.generated.tables.records.VolunteermatchRecord;
import com.apon.database.jooq.DbContext;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import java.util.List;

import static org.jooq.impl.DSL.using;

public class VolunteerMatchMyDao extends VolunteermatchDao {

    public VolunteerMatchMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    @Deprecated
    public VolunteerMatchMyDao(Configuration configuration) {
        super(configuration);
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

    public boolean insertPojo(VolunteermatchPojo volunteermatchPojo) {
        if (!generateIds(volunteermatchPojo)) {
            // Some kind of logError message?
            return false;
        }

        super.insert(volunteermatchPojo);

        return true;
    }

    public List<VolunteermatchPojo> getMatchForVolunteer(String volunteerExtId) {
        return getMatchForVolunteer(volunteerExtId, null);
    }

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

    public List<VolunteermatchPojo> getMatchForStudent(int studentId, boolean sortAscending) {
        SelectConditionStep<VolunteermatchRecord> query = using(configuration())
                .selectFrom(Volunteermatch.VOLUNTEERMATCH)
                .where(Volunteermatch.VOLUNTEERMATCH.STUDENTID.eq(studentId));

        if (sortAscending) {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.asc());
        } else {
            query.orderBy(Volunteermatch.VOLUNTEERMATCH.DATESTART.desc());
        }

        return query.fetch().map(mapper());
    }

    public List<VolunteermatchPojo> getMatchForVolunteerAndStudent(int volunteerId, int studentId) {
        SelectConditionStep<VolunteermatchRecord> query = using(configuration())
                .selectFrom(Volunteermatch.VOLUNTEERMATCH)
                .where(Volunteermatch.VOLUNTEERMATCH.STUDENTID.eq(studentId))
                .and(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.eq(volunteerId));

        return query.fetch().map(mapper());
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
