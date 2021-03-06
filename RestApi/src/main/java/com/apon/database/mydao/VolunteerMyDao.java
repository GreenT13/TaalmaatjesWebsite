package com.apon.database.mydao;

import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteerinstance;
import com.apon.database.generated.tables.Volunteermatch;
import com.apon.database.generated.tables.daos.VolunteerDao;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.records.VolunteerRecord;
import com.apon.database.generated.tables.records.VolunteerinstanceRecord;
import com.apon.database.generated.tables.records.VolunteermatchRecord;
import com.apon.database.jooq.DbContext;
import com.apon.exceptionhandler.ResultObject;
import com.apon.util.ResultUtil;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import javax.annotation.Nonnull;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.using;

@SuppressWarnings("unused")
public class VolunteerMyDao extends VolunteerDao {
    private final static Integer STARTING_EXT_ID = 1001;
    private ResultObject resultObject;

    public VolunteerMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    public ResultObject getResultObject() {
        return resultObject;
    }

    @SuppressWarnings("Duplicates")
    private boolean generateIds(VolunteerPojo volunteerPojo) {
        if (volunteerPojo.getVolunteerid() == null) {
            Integer maxId = getMaxId();
            volunteerPojo.setVolunteerid(maxId != null ? maxId + 1 : 0);
        }

        if (volunteerPojo.getExternalidentifier() == null) {
            String maxExtId = getMaxExtId();
            if (maxExtId == null) {
                maxExtId = STARTING_EXT_ID.toString();
            } else {
                maxExtId = String.valueOf(Integer.valueOf(maxExtId) + 1);
            }
            volunteerPojo.setExternalidentifier(maxExtId);
        }

        return true;
    }

    private Integer getMaxId() {
        return using(configuration())
                .select(Volunteer.VOLUNTEER.VOLUNTEERID.max())
                .from(Volunteer.VOLUNTEER)
                .fetchOne(0, Integer.class);
    }

    private String getMaxExtId() {
        return using(configuration())
                .select(Volunteer.VOLUNTEER.EXTERNALIDENTIFIER.cast(MySQLDataType.INT).max())
                .from(Volunteer.VOLUNTEER)
                .fetchOne(0, String.class);
    }

    public VolunteerMyDao() {
        super();
    }

    private boolean validatePojo(VolunteerPojo volunteerPojo) {
        if (volunteerPojo.getLastname() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.validate.lastName");
            return false;
        }

        if (volunteerPojo.getDateofbirth() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.validate.dateOfBirth");
            return false;
        }

        if (volunteerPojo.getGender() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.validate.gender");
            return false;
        }

        if (volunteerPojo.getIsclassassistant() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.validate.isClassAssistant");
            return false;
        }

        if (volunteerPojo.getIstaalmaatje() == null) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.validate.isTaalmaatje");
            return false;
        }

        return true;
    }

    public boolean insertPojo(VolunteerPojo volunteerPojo) {
        if (!validatePojo(volunteerPojo)) {
            // Result object is already set.
            return false;
        }

        if (!generateIds(volunteerPojo)) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.generateId.error");
            return false;
        }

        try {
            super.insert(volunteerPojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.insert.error", e);
            return false;
        }

        return true;
    }

    public boolean updatePojo(VolunteerPojo volunteerPojo) {
        if (!validatePojo(volunteerPojo)) {
            // Result object is already set.
            return false;
        }

        try {
            super.update(volunteerPojo);
        } catch (Exception e) {
            resultObject = ResultUtil.createErrorResult("VolunteerMyDao.update.error", e);
        }

        return true;
    }

    /**
     * Count how many volunteers there are in the database with a VolunteerInstance.dateStart
     * between minimumDate and maximumDate, with age between certain range. Needs to follow training within date range.
     * @param minimumDate Minimum start date.
     * @param maximumDate Maximum start date.
     * @param minAge Minimum age.
     * @param maxAge Maximum age.
     * @param gender Gender.
     * @return Integer value of volunteers that satisfy the criteria
     */
    public int countNew(@Nonnull Date minimumDate, @Nonnull Date maximumDate,
                        int minAge, int maxAge, @Nonnull String gender) {
        SelectConditionStep<Record1<Integer>> query = using(configuration())
                .selectCount()
                .from(Volunteer.VOLUNTEER)
                // Select the miminum dateStart from the VolunteerInstances that connect to this Volunteer.
                .where(using(configuration()).select(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.min())
                        .from(Volunteerinstance.VOLUNTEERINSTANCE)
                        .where(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID))
                        // Check whether this date is between the minimumDate and maximumDate
                        .asField().between(minimumDate, maximumDate))
                // Age is in certain category.
                .and(DSL.floor(DSL.dateDiff(DSL.currentDate(), Volunteer.VOLUNTEER.DATEOFBIRTH).div(365.25)).between(minAge, maxAge));

        // Volunteer needs to be trained in this range (new = trained in this range).
        query.and(Volunteer.VOLUNTEER.DATETRAINING.between(minimumDate, maximumDate));

        // Need to equal gender
        query.and(Volunteer.VOLUNTEER.GENDER.eq(gender));

        // Return the single row integer.
        return query.fetchOne(0, int.class);
    }

    /**
     * Count how many volunteers are active for at least one day in the period minimumDate-maximumDate.
     * If hasTraining is non-null, addVolunteer criteria that it must match.
     * A volunteer is considered active on date X if there is some volunteerInstance for which holds:
     * 1. dateStart <= x
     * 2. dateEnd is null or x <= dateEnd
     * @param minimumDate Minimum start date.
     * @param maximumDate Maximum start date.
     * @param minAge Minimum age.
     * @param maxAge Maximum age.
     * @param gender Gender.
     * @return Integer value of volunteers that satisfy the criteria
     */
    public int countActive(@Nonnull Date minimumDate, @Nonnull Date maximumDate,
                           int minAge, int maxAge, @Nonnull String gender) {
        SelectConditionStep<Record1<Integer>> query = using(configuration())
                // Since we use a join on instance, we must count distinct number of ID's.
                .select(Volunteer.VOLUNTEER.VOLUNTEERID.countDistinct())
                .from(Volunteer.VOLUNTEER)
                .join(Volunteerinstance.VOLUNTEERINSTANCE)
                    .on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID))
                .where(
                    // dateStart between minimumDate and maximumDate
                    Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.between(minimumDate, maximumDate)

                    // dateEnd between minimumDate and maximumDate
                    // Will give unknown if dateEnd is null, which is fine since it is part of an or-cause.
                    .or(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.between(minimumDate, maximumDate))

                    // dateStart <= min and (dateEnd is null || min <= dateEnd)
                    .or(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.le(minimumDate)
                                .and(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.isNull()
                                    .or(DSL.val(minimumDate).le(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND))))

                    // dateStart <= max and (dateEnd is null || max <= dateEnd)
                    .or(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.le(maximumDate)
                            .and(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.isNull()
                                    .or(DSL.val(maximumDate).le(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND)))))

                // Age is in certain category.
                .and(DSL.floor(DSL.dateDiff(DSL.currentDate(), Volunteer.VOLUNTEER.DATEOFBIRTH).div(365.25)).between(minAge, maxAge));

        // Volunteer needs to be trained before end date of range.
        query.and(Volunteer.VOLUNTEER.DATETRAINING.le(maximumDate));

        // Need to equal gender
        query.and(Volunteer.VOLUNTEER.GENDER.eq(gender));

        // Return the single row integer.
        return query.fetchOne(0, int.class);
    }

    /**
     * Search for volunteers based on non-null inputs.
     * @param input Search for this text in Volunteer.firstName, Volunteer.insertion and Volunteer.lastName.
     * @param isActive Whether there is a VolunteerInstance today.
     * @param hasTraining Whether Volunteer.dateTraining is filled.
     * @param hasMatch Whether there is a VolunteerMatch today.
     * @param city Search for this text in Volunteer.city.
     * @return Map&lt;VolunteerPojo, Integer&gt;
     */
    public Map<VolunteerPojo, Integer> advancedSearch(String input, Boolean isActive, Boolean hasTraining, Boolean hasMatch, String city) {
        SelectWhereStep<Record> query = using(configuration())
                .select(Volunteer.VOLUNTEER.fields())
                .select(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.count())
                .from(Volunteer.VOLUNTEER)
                .leftJoin(Volunteermatch.VOLUNTEERMATCH).on(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.eq(Volunteer.VOLUNTEER.VOLUNTEERID));

        // Add the input to search criteria.
        if (input != null && input.trim().length() > 0) {
            String[] searchStrings = input.toLowerCase().split(" ");
            for (String s : searchStrings) {
                query.where(
                        Volunteer.VOLUNTEER.FIRSTNAME.lower().like(s + "%")
                                .or(Volunteer.VOLUNTEER.INSERTION.lower().like(s + "%"))
                                .or(Volunteer.VOLUNTEER.LASTNAME.lower().like(s + "%"))
                );
            }
        }

        if (isActive != null) {
            // Query to find out whether the volunteer is active.
            Select<VolunteerinstanceRecord> subQuery = using(configuration())
                    .selectFrom(Volunteerinstance.VOLUNTEERINSTANCE)
                    .where(Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID.eq(Volunteer.VOLUNTEER.VOLUNTEERID))
                        // dateStart <= current_date and (dateEnd is null || current_date <= dateEnd)
                        .and(Volunteerinstance.VOLUNTEERINSTANCE.DATESTART.le(DSL.currentDate()))
                        .and(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.isNull()
                                .or(Volunteerinstance.VOLUNTEERINSTANCE.DATEEND.ge(DSL.currentDate())));

            // Depending on the value of isActive, use exists or not exists.
            if (isActive) {
                query.whereExists(subQuery);
            } else {
                query.whereNotExists(subQuery);
            }
        }

        if (hasTraining != null && hasTraining) {
            query.where(Volunteer.VOLUNTEER.DATETRAINING.isNotNull());
        } else if (hasTraining != null) {
            query.where(Volunteer.VOLUNTEER.DATETRAINING.isNull());
        }

        if (hasMatch != null) {
            Select<VolunteermatchRecord> subQuery =using(configuration()).selectFrom(Volunteermatch.VOLUNTEERMATCH)
                    .where(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.eq(Volunteer.VOLUNTEER.VOLUNTEERID)
                                    // dateStart <= current_date and (dateEnd is null || current_date <= dateEnd)
                                    .and(Volunteermatch.VOLUNTEERMATCH.DATESTART.le(DSL.currentDate()))
                                    .and(Volunteermatch.VOLUNTEERMATCH.DATEEND.isNull()
                                            .or(Volunteermatch.VOLUNTEERMATCH.DATEEND.ge(DSL.currentDate()))));

            if (hasMatch) {
                query.whereExists(subQuery);
            } else {
                query.whereNotExists(subQuery);
            }
        }

        if (city != null && city.trim().length() > 0) {
            query.where(Volunteer.VOLUNTEER.CITY.lower().like("%" + city.toLowerCase() + "%"));
        }
        return query.groupBy(Volunteer.VOLUNTEER.fields()) .fetchMap(
                r -> r.into(Volunteer.VOLUNTEER).into(VolunteerPojo.class),
                r -> r.get(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID.count()));
    }

    public List<VolunteerPojo> getCurrentVolunteer(int studentId) {
        return using(configuration())
                .selectFrom(Volunteer.VOLUNTEER)
                .where(Volunteer.VOLUNTEER.VOLUNTEERID.eq(
                        using(configuration())
                                .select(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID)
                                .from(Volunteermatch.VOLUNTEERMATCH)
                                .where(Volunteermatch.VOLUNTEERMATCH.STUDENTID.eq(studentId))
                                // Instance is active today: dateStart <= current_date and (dateEnd is null || current_date <= dateEnd)
                                .and(Volunteermatch.VOLUNTEERMATCH.DATESTART.le(DSL.currentDate()))
                                .and(Volunteermatch.VOLUNTEERMATCH.DATEEND.isNull()
                                        .or(Volunteermatch.VOLUNTEERMATCH.DATEEND.ge(DSL.currentDate())))
                ))
                .fetch()
                .map(mapper());
    }

}
