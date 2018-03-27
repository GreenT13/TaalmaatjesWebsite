package com.apon.service;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.*;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringValueObject;
import com.apon.service.valueobject.VolunteerMatchValueObject;
import com.apon.service.valueobject.mapper.VolunteerMatchMapper;
import com.apon.util.DateTimeUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;

@SuppressWarnings("unchecked")
@Path("volunteer/{volunteerExtId}/match")
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerMatchService implements IService {
    private DbContext context;

    /**
     * Retrieves a volunteerMatch.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerMatchExtId The extId from the match.
     * @return VolunteerMatchValueObject
     */
    @GET
    @Path("{volunteerMatchExtId}")
    @InjectContext
    public VolunteerMatchValueObject get(@PathParam("volunteerExtId") String volunteerExtId,
                                         @PathParam("volunteerMatchExtId") String volunteerMatchExtId) throws Exception {
        // Mapper and Dao variables.
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        VolunteerMatchMapper volunteerMatchMapper = new VolunteerMatchMapper();

        // Retrieve volunteerMatch.
        QueryResult<VolunteermatchPojo, StudentPojo> result = volunteerMatchMyDao.retrieveMatchWithStudent(volunteerExtId, volunteerMatchExtId);
        if (result == null) {
            throw new FunctionalException("VolunteerMatchService.notFound.match");
        }
        volunteerMatchMapper.setVolunteermatchPojo(result.getS());
        volunteerMatchMapper.setStudentPojo(result.getT());

        // volunteerExtId is not retrieved, but it is a path param so we just set it manually.
        volunteerMatchMapper.getVolunteerMatchValueObject().setVolunteerExtId(volunteerExtId);

        return volunteerMatchMapper.getVolunteerMatchValueObject();
    }

    /**
     * Add a VolunteerMatch in the database.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerMatchValueObject The match object.
     * @return The extId from the instance that is inserted.
     */
    @PUT
    @InjectContext
    public StringValueObject insert(@PathParam("volunteerExtId") String volunteerExtId,
                                    VolunteerMatchValueObject volunteerMatchValueObject) throws Exception {
        // Mapper and Dao variables.
        VolunteerMatchMapper volunteerMatchMapper = new VolunteerMatchMapper();
        volunteerMatchMapper.setVolunteerMatchValueObject(volunteerMatchValueObject);

        // Get volunteerId.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerMatchService.notFound.volunteer");
        }

        // Get studentId
        StudentMyDao studentMyDao = new StudentMyDao(context);
        StudentPojo studentPojo = studentMyDao.fetchOneByExternalidentifier(volunteerMatchValueObject.getStudentValueObject().getExternalIdentifier());
        if (studentPojo == null) {
            throw new FunctionalException("VolunteerMatchService.notFound.student");
        }

        VolunteermatchPojo volunteermatchPojo = volunteerMatchMapper.getVolunteermatchPojo();
        volunteermatchPojo.setVolunteerid(volunteerPojo.getVolunteerid());
        volunteermatchPojo.setStudentid(studentPojo.getStudentid());

        // Handle the complete adding / merging in another function. This function will throw its exceptions.
        isVolunteerMatchValidAndAdd(volunteerExtId, studentPojo.getExternalidentifier(), volunteermatchPojo, false);

        // Commit the changes.
        context.commit();

        // Return the extId.
        return new StringValueObject(volunteermatchPojo.getExternalidentifier());
    }

    /**
     * Update a VolunteerMatch in the database with a certain extId.
     * Note that you overwrite ALL fields on the instance. You have to fill every parameter.
     * @param volunteerExtId The extId to identify the volunteer.
     * @param volunteerMatchExtId The extId to identify the match.
     * @param volunteerMatchValueObject The instance object.
     * @throws Exception FunctionalException
     */
    @POST
    @Path("{volunteerMatchExtId}")
    @InjectContext
    public void update(@PathParam("volunteerExtId") String volunteerExtId,
                       @PathParam("volunteerMatchExtId") String volunteerMatchExtId,
                       VolunteerMatchValueObject volunteerMatchValueObject) throws Exception {
        // Retrieve the match.
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        VolunteermatchPojo volunteermatchPojo = volunteerMatchMyDao.fetchByExtIds(volunteerExtId, volunteerMatchExtId);
        if (volunteermatchPojo == null) {
            throw new FunctionalException("VolunteerMatchService.notFound.match");
        }

        VolunteerMatchMapper volunteerMatchMapper = new VolunteerMatchMapper();
        // Make sure the id's are set.
        volunteerMatchMapper.setVolunteermatchPojo(volunteermatchPojo);
        // Overwrite the rest.
        volunteerMatchMapper.setVolunteerMatchValueObject(volunteerMatchValueObject);

        // Handle the complete adding / merging in another function.
        isVolunteerMatchValidAndAdd(volunteerExtId, volunteerMatchValueObject.getStudentValueObject().getExternalIdentifier(),
                volunteermatchPojo, true);

        // Commit the changes.
        context.commit();
    }


    /**
     * Check if the line can be added to the database. Merge the line if needed. Returns true if added (possibly merged)
     * and return false if some verification failed.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteermatchPojoNew The match to add.
     */
    private void isVolunteerMatchValidAndAdd(String volunteerExtId, String studentExtId,
                                                VolunteermatchPojo volunteermatchPojoNew, boolean isUpdate)
    throws Exception {
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        // if [A,B] can be merged into [dateStart,dateEnd] with B=dateStart then mergeAfterVolunteerMatchExtId will be B.
        String mergeAfterVolunteerMatchExtId = null;

        // if [dateStart,dateEnd] can be merged into [A,B] with A=dateEnd then mergeAfterVolunteerMatchExtId will be A.
        String mergeBeforeVolunteerMatchExtId = null;

        // Since getting fields from the pojo is annoying, we make it easier.
        Date dateStart = volunteermatchPojoNew.getDatestart();
        Date dateEnd = volunteermatchPojoNew.getDateend();

        for (VolunteermatchPojo volunteermatchPojo : volunteerMatchMyDao.getMatchForVolunteerAndStudent(volunteerExtId, studentExtId)) {
            // If we updateStudent instead of insert, we want to 'exclude' the to-be-updated volunteer match from the search.
            if (isUpdate && volunteermatchPojo.getVolunteermatchid().equals(volunteermatchPojoNew.getVolunteermatchid())) {
                continue;
            }
            // If one of the following hold, return false:
            // 1. dateStart is contained in (pojo.dateStart, pojo.dateEnd)
            // 2. dateEnd is contained in (pojo.dateStart, pojo.dateEnd)
            // 3. Range [pojo.dateStart, pojo.dateEnd] is contained within (dateStart, dateEnd).

            // However, the above does not hold at all whenever pojo.dateStart = pojo.dateEnd (one day instance).
            // So we only threat this case differently.
            if (volunteermatchPojo.getDateend() != null &&
                    volunteermatchPojo.getDatestart().compareTo(volunteermatchPojo.getDateend()) == 0) {
                // We have 4 possibilities:
                // 1. pojo.date \in (dateStart, dateEnd) => overlap so false.
                // 2. pojo.date == dateStart || pojo.date + 1 DAY = dateStart => merge
                // 3. pojo.date == dateEnd || pojo.date + 1 DAY = dateEnd => merge
                // 4. none of the above => we do nothing, we can ignore this line.

                if (DateTimeUtil.isBetweenWithoutEndpoints(volunteermatchPojo.getDatestart(), dateStart, dateEnd)) {
                    throw new FunctionalException("VolunteerMatchService.error.overlap");
                }

                // Merge before
                if (DateTimeUtil.nrOfDaysInBetween(volunteermatchPojo.getDateend(), dateStart) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(volunteermatchPojo.getDateend(), dateStart) == 1) {
                    mergeBeforeVolunteerMatchExtId = volunteermatchPojo.getExternalidentifier();
                }

                // Merge after
                if (dateEnd != null && (DateTimeUtil.nrOfDaysInBetween(volunteermatchPojo.getDatestart(), dateEnd) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteermatchPojo.getDatestart()) == 1)) {
                    mergeAfterVolunteerMatchExtId = volunteermatchPojo.getExternalidentifier();
                }

                // In any case we can just go on searching.
                continue;
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateStart,
                    volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend())) {
                throw new FunctionalException("VolunteerMatchService.error.overlap");
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateEnd,
                    volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend())) {
                throw new FunctionalException("VolunteerMatchService.error.overlap");
            }

            if (DateTimeUtil.isContained(volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend(),
                    dateStart, dateEnd)) {
                throw new FunctionalException("VolunteerMatchService.error.completeOverlap");
            }

            // Determine whether we can actually merge with this line.
            // Merge after: (note that if both dates are null, we can never merge.
            if (dateEnd != null && volunteermatchPojo.getDatestart() != null &&
                    (volunteermatchPojo.getDatestart().compareTo(dateEnd) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteermatchPojo.getDatestart()) == 1)) {
                mergeAfterVolunteerMatchExtId = volunteermatchPojo.getExternalidentifier();
            }

            // Merge before: (note that dateEnd must be non-null to merge. Also dateStart is never null).
            if (volunteermatchPojo.getDateend() != null &&
                    (DateTimeUtil.nrOfDaysInBetween(volunteermatchPojo.getDateend(), dateStart) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(volunteermatchPojo.getDateend(), dateStart) == 1)) {
                mergeBeforeVolunteerMatchExtId = volunteermatchPojo.getExternalidentifier();
            }
        }

        // If we actually reach this point, we know the line will be added to the database (merged or not).
        VolunteermatchPojo volunteermatchPojo = new VolunteermatchPojo();

        // Merge after if possible.
        if (mergeAfterVolunteerMatchExtId != null) {
            VolunteermatchPojo mergedVolunteermatchPojo = volunteerMatchMyDao.fetchByExtIds(volunteerExtId, mergeAfterVolunteerMatchExtId);
            volunteermatchPojo.setDateend(mergedVolunteermatchPojo.getDateend());
            volunteerMatchMyDao.delete(mergedVolunteermatchPojo);
        } else {
            // Date start must still be set.
            volunteermatchPojo.setDateend(dateEnd);
        }

        // Merge before if possible.
        if (mergeBeforeVolunteerMatchExtId != null) {
            VolunteermatchPojo mergedVolunteermatchPojo = volunteerMatchMyDao.fetchByExtIds(volunteerExtId, mergeBeforeVolunteerMatchExtId);
            volunteermatchPojo.setDatestart(mergedVolunteermatchPojo.getDatestart());
            volunteerMatchMyDao.delete(mergedVolunteermatchPojo);
        } else {
            // Date start must still be set.
            volunteermatchPojo.setDatestart(dateStart);
        }

        // Check that the volunteer is active during this period.
        if (!isVolunteerActiveDuringMatch(volunteerExtId, volunteermatchPojo)) {
            throw new FunctionalException("VolunteerMatchService.error.matchWithoutInstance");
        }

        if (!isUpdate) {
            volunteerMatchMyDao.insertPojo(volunteermatchPojo);
        } else {
            volunteerMatchMyDao.update(volunteermatchPojo);
        }
    }

    /**
     * Returns whether the volunteer is active during the [match.dateStart, match.dateEnd].
     * @param volunteermatchPojo .
     * @return boolean
     */
    private boolean isVolunteerActiveDuringMatch(String volunteerExtId, VolunteermatchPojo volunteermatchPojo) {
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        for (VolunteerinstancePojo volunteerinstancePojo : volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId)) {
            if (DateTimeUtil.isContained(volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend(),
                    volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Delete match from the database.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerMatchExtId The extId from the match.
     * @throws Exception FunctionalException
     */
    @DELETE
    @Path("{volunteerMatchExtId}")
    @InjectContext
    public void delete(@PathParam("volunteerExtId") String volunteerExtId,
                       @PathParam("volunteerMatchExtId") String volunteerMatchExtId) throws Exception {
        // Retrieve the match.
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        VolunteermatchPojo volunteermatchPojo = volunteerMatchMyDao.fetchByExtIds(volunteerExtId, volunteerMatchExtId);
        if (volunteermatchPojo == null) {
            throw new FunctionalException("VolunteerMatchService.notFound.match");
        }

        // Delete the instance.
        volunteerMatchMyDao.delete(volunteermatchPojo);

        // Commit the changes.
        context.commit();
    }


    @Override
    public DbContext getContext() {
        return context;
    }

    @Override
    public void setContext(DbContext dbContext) {
        context = dbContext;
    }
}
