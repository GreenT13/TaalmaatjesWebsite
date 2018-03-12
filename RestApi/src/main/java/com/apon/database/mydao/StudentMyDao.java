package com.apon.database.mydao;

import com.apon.database.generated.tables.Student;
import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteermatch;
import com.apon.database.generated.tables.daos.StudentDao;
import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.records.StudentRecord;
import com.apon.database.generated.tables.records.VolunteermatchRecord;
import com.apon.database.jooq.DbContext;
import com.apon.log.MyLogger;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDataType;

import javax.annotation.Nonnull;
import java.sql.Date;
import java.util.List;

import static org.jooq.impl.DSL.using;

public class StudentMyDao extends StudentDao {
    private final static Integer STARTING_EXT_ID = 5001;

    public StudentMyDao(DbContext context) {
        super(context.getConfiguration());
    }

    @Deprecated
    public StudentMyDao(Configuration configuration) {
        super(configuration);
    }

    @SuppressWarnings("Duplicates")
    private boolean generateIds(StudentPojo studentPojo) {
        if (studentPojo.getStudentid() == null) {
            Integer maxId = getMaxId();
            studentPojo.setStudentid(maxId != null ? maxId + 1 : 0);
        }

        if (studentPojo.getExternalidentifier() == null) {
            String maxExtId = getMaxExtId();
            if (maxExtId == null) {
                maxExtId = STARTING_EXT_ID.toString();
            } else {
                maxExtId = String.valueOf(Integer.valueOf(maxExtId) + 1);
            }
            studentPojo.setExternalidentifier(maxExtId);
        }

        return true;
    }

    private Integer getMaxId() {
        return using(configuration())
                .select(Student.STUDENT.STUDENTID.max())
                .from(Student.STUDENT)
                .fetchOne(0, Integer.class);
    }

    private String getMaxExtId() {
        return using(configuration())
                .select(Student.STUDENT.EXTERNALIDENTIFIER.cast(MySQLDataType.INT).max())
                .from(Student.STUDENT)
                .fetchOne(0, String.class);
    }

    public Integer getIdFromExtId(String externalIdentifier) {
        return using(configuration())
                .select(Student.STUDENT.STUDENTID)
                .from(Student.STUDENT)
                .where(Student.STUDENT.EXTERNALIDENTIFIER.eq(externalIdentifier))
                .fetchOne(0, Integer.class);
    }

    public String getExtIdFromid(int studentId) {
        return using(configuration())
                .select(Student.STUDENT.EXTERNALIDENTIFIER)
                .from(Student.STUDENT)
                .where(Student.STUDENT.STUDENTID.eq(studentId))
                .fetchOne(0, String.class);
    }

    public boolean insertPojo(StudentPojo studentPojo) {
        if (!generateIds(studentPojo)) {
            // Some kind of logError message?
            return false;
        }

        try {
            super.insert(studentPojo);
        } catch (Exception e) {
            MyLogger.logError("Could not insert student", e);
            return false;
        }

        return true;
    }

    /**
     * Count how many students have their first dateStart from a match between minimumDate and maximumDate.
     * @param minimumDate Minimum start date.
     * @param maximumDate Maximum start date.
     * @return Integer value of students that satisfy the criteria.
     */
    public int countNew(@Nonnull Date minimumDate, @Nonnull Date maximumDate,
                        @Nonnull int minAge, @Nonnull int maxAge, @Nonnull String gender) {
        SelectConditionStep<Record1<Integer>> query =  using(configuration())
                .selectCount()
                .from(Student.STUDENT)
                .where(using(configuration()).select(Volunteermatch.VOLUNTEERMATCH.DATESTART.min())
                                .from(Volunteermatch.VOLUNTEERMATCH)
                                .innerJoin(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                                .where(Volunteermatch.VOLUNTEERMATCH.STUDENTID.eq(Student.STUDENT.STUDENTID))
                                .and(Volunteer.VOLUNTEER.DATETRAINING.le(maximumDate))
                                // Check whether this date is between the minimumDate and maximumDate
                                .asField().between(minimumDate, maximumDate))
                // Need to equal gender
                .and(Student.STUDENT.GENDER.eq(gender))
                // Age is in certain category.
                .and(DSL.floor(DSL.dateDiff(DSL.currentDate(), Student.STUDENT.DATEOFBIRTH).div(365.25)).between(minAge, maxAge));

        return query.fetchOne(0, int.class);
    }

    /**
     * Count how many students are active for at least one day in the period minimumDate-maximumDate.
     * If isGroup is non-null, addVolunteer criteria that it must match.
     * A student is considered active on date X if there is some volunteerMatch for which holds:
     * 1. dateStart <= x
     * 2. dateEnd is null or x <= dateEnd
     * @param minimumDate
     * @param maximumDate
     * @return
     */
    public int countActive(@Nonnull Date minimumDate, @Nonnull Date maximumDate,
                           @Nonnull int minAge, @Nonnull int maxAge, @Nonnull String gender) {
        SelectConditionStep<Record1<Integer>> query = using(configuration())
                // Since we use a join on instance, we must count distinct number of ID's.
                .select(Student.STUDENT.STUDENTID.countDistinct())
                .from(Student.STUDENT)
                .join(Volunteermatch.VOLUNTEERMATCH).on(Student.STUDENT.STUDENTID.eq(Volunteermatch.VOLUNTEERMATCH.STUDENTID))
                .join(Volunteer.VOLUNTEER).on(Volunteer.VOLUNTEER.VOLUNTEERID.eq(Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID))
                .where(
                        // dateStart between minimumDate and maximumDate
                        Volunteermatch.VOLUNTEERMATCH.DATESTART.between(minimumDate, maximumDate)

                        // dateEnd between minimumDate and maximumDate
                        // Will give unknown if dateEnd is null, which is fine since it is part of an or-cause.
                        .or(Volunteermatch.VOLUNTEERMATCH.DATEEND.between(minimumDate, maximumDate))

                        // dateStart <= min and (dateEnd is null || min <= dateEnd)
                        .or(Volunteermatch.VOLUNTEERMATCH.DATESTART.le(minimumDate)
                                .and(Volunteermatch.VOLUNTEERMATCH.DATEEND.isNull()
                                        .or(DSL.val(minimumDate).le(Volunteermatch.VOLUNTEERMATCH.DATEEND))))

                        // dateStart <= max and (dateEnd is null || max <= dateEnd)
                        .or(Volunteermatch.VOLUNTEERMATCH.DATESTART.le(maximumDate)
                                .and(Volunteermatch.VOLUNTEERMATCH.DATEEND.isNull()
                                        .or(DSL.val(maximumDate).le(Volunteermatch.VOLUNTEERMATCH.DATEEND))))
                )
                // Volunteer must be trained.
                .and(Volunteer.VOLUNTEER.DATETRAINING.le(maximumDate))
                // Need to equal gender
                .and(Student.STUDENT.GENDER.eq(gender))
                // Age is in certain category.
                .and(DSL.floor(DSL.dateDiff(DSL.currentDate(), Student.STUDENT.DATEOFBIRTH).div(365.25)).between(minAge, maxAge));

        // Return the single row integer.
        return query.fetchOne(0, int.class);
    }

    /**
     * Search for students based on non-null inputs.
     * @param input
     * @param hasMatch
     * @return
     */
    public List<StudentPojo> advancedSearch(String input, Boolean hasMatch) {
        SelectWhereStep<StudentRecord> query = using(configuration()).selectFrom(Student.STUDENT);

        // Add the input to search criteria.
        if (input != null && input.trim().length() > 0) {
            String[] searchStrings = input.toLowerCase().split(" ");
            for (String s : searchStrings) {
                query.where(
                        Student.STUDENT.FIRSTNAME.lower().like("%" + s + "%")
                                .or(Student.STUDENT.INSERTION.lower().like("%" + s + "%"))
                                .or(Student.STUDENT.LASTNAME.lower().like("%" + s + "%"))
                );
            }
        }

        if (hasMatch != null) {
            Select<VolunteermatchRecord> subQuery =using(configuration()).selectFrom(Volunteermatch.VOLUNTEERMATCH)
                    .where(Volunteermatch.VOLUNTEERMATCH.STUDENTID.eq(Student.STUDENT.STUDENTID)
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

        return query.orderBy(Student.STUDENT.FIRSTNAME.asc()).limit(50).fetch().map(mapper());
    }
}
