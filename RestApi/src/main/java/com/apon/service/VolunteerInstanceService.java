package com.apon.service;

import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.VolunteerInstanceMyDao;
import com.apon.database.mydao.VolunteerMatchMyDao;
import com.apon.database.mydao.VolunteerMyDao;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.VolunteerInstanceValueObject;
import com.apon.service.valueobject.mapper.VolunteerInstanceMapper;
import com.apon.util.DateTimeUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Path("volunteer/{volunteerExtId}/instance")
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerInstanceService implements IService {
    private DbContext context;

    @GET
    @Path("{volunteerInstanceExtId}")
    @InjectContext
    public VolunteerInstanceValueObject getVolunteerInstance(@PathParam("volunteerExtId") String volunteerExtId,
                                                             @PathParam("volunteerInstanceExtId") String volunteerInstanceExtId)
            throws com.apon.exceptionhandler.NotFoundException {
        // Mapper and Dao variables.
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerInstanceMapper volunteerInstanceMapper = new VolunteerInstanceMapper();

        // Retrieve volunteerInstanceId.
        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, volunteerInstanceExtId);
        if (volunteerinstancePojo == null) {
            throw new com.apon.exceptionhandler.NotFoundException("Could not find volunteer instance.");
        }

        // Retrieve the instances and put them on the volunteer.
        volunteerInstanceMapper.setVolunteerinstancePojo(volunteerinstancePojo);

        // volunteerExtId is not retrieved, but it is a path param so we just set it manually.
        volunteerInstanceMapper.getVolunteerInstanceValueObject().setVolunteerExtId(volunteerExtId);

        return volunteerInstanceMapper.getVolunteerInstanceValueObject();
    }

    @PUT
    @InjectContext
    public void insertVolunteerInstance(@PathParam("volunteerExtId") String volunteerExtId,
                                                        VolunteerInstanceValueObject volunteerInstanceValueObject) throws Exception {
        // Mapper and Dao variables.
        VolunteerInstanceMapper volunteerInstanceMapper = new VolunteerInstanceMapper();
        volunteerInstanceMapper.setVolunteerInstanceValueObject(volunteerInstanceValueObject);

        // Get volunteerId.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        Integer volunteerId = volunteerMyDao.getIdFromExtId(volunteerExtId);
        if (volunteerId == null) {
            throw new Exception("VolunteerInstanceAPI.error.noVolunteerExtIdFound");
        }

        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMapper.getVolunteerinstancePojo();
        volunteerinstancePojo.setVolunteerid(volunteerId);

        // Handle the complete adding / merging in another function.
        isVolunteerInstanceValidAndAdd(volunteerExtId, volunteerinstancePojo, false);

        context.commit();
    }

    @POST
    @Path("{volunteerInstanceExtId}")
    @InjectContext
    public void updateVolunteerInstance(@PathParam("volunteerExtId") String volunteerExtId,
                                @PathParam("volunteerInstanceExtId") String volunteerInstanceExtId,
                                VolunteerInstanceValueObject volunteerInstanceValueObject) throws Exception {
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, volunteerInstanceExtId);
        if (volunteerinstancePojo == null) {
            throw new com.apon.exceptionhandler.NotFoundException("Could not find volunteer instance.");
        }

        VolunteerInstanceMapper volunteerInstanceMapper = new VolunteerInstanceMapper();

        // Handle the complete adding / merging in another function.
        isVolunteerInstanceValidAndAdd(volunteerExtId, volunteerinstancePojo, true);

        context.commit();
    }

    /**
     * Check if the line can be added to the database. Merge the line if needed. Returns true if added (possibly merged)
     * and return false if some verification failed.
     * @param volunteerExtId
     * @param volunteerinstancePojoNew
     * @param isUpdate
     * @return boolean
     * @throws Exception
     */
    private boolean isVolunteerInstanceValidAndAdd(String volunteerExtId, VolunteerinstancePojo volunteerinstancePojoNew, boolean isUpdate)
        throws Exception {
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        // if [A,B] can be merged into [dateStart,dateEnd] with B=dateStart then mergeAfterVolunteerInstanceId will be B.
        String mergeAfterVolunteerInstanceExtId = null;

        // if [dateStart,dateEnd] can be merged into [A,B] with A=dateEnd then mergeAfterVolunteerInstanceId will be A.
        String mergeBeforeVolunteerInstanceExtId = null;

        // Since getting fields from the pojo is annoying, we make it easier.
        Date dateStart = volunteerinstancePojoNew.getDatestart();
        Date dateEnd = volunteerinstancePojoNew.getDateend();

        for (VolunteerinstancePojo volunteerinstancePojo : volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId)) {
            // If we updateVolunteer instead of insert, we want to 'exclude' the to-be-updated volunteerinstance from the search.
            if (isUpdate && volunteerinstancePojo.getVolunteerinstanceid().equals(volunteerinstancePojoNew.getVolunteerinstanceid())) {
                continue;
            }
            // If one of the following hold, return false:
            // 1. dateStart is contained in (pojo.dateStart, pojo.dateEnd)
            // 2. dateEnd is contained in (pojo.dateStart, pojo.dateEnd)
            // 3. Range [pojo.dateStart, pojo.dateEnd] is contained within (dateStart, dateEnd).

            // However, the above does not hold at all whenever pojo.dateStart = pojo.dateEnd (one day instance).
            // So we only threat this case differently.
            if (volunteerinstancePojo.getDateend() != null &&
                    volunteerinstancePojo.getDatestart().compareTo(volunteerinstancePojo.getDateend()) == 0) {
                // We have 4 possibilities:
                // 1. pojo.date \in (dateStart, dateEnd) => overlap so false.
                // 2. pojo.date == dateStart || pojo.date + 1 DAY = dateStart => merge
                // 3. pojo.date == dateEnd || pojo.date + 1 DAY = dateEnd => merge
                // 4. none of the above => we do nothing, we can ignore this line.

                if (DateTimeUtil.isBetweenWithoutEndpoints(volunteerinstancePojo.getDatestart(), dateStart, dateEnd)) {
                    throw new Exception("VolunteerInstanceAPI.error.overlap");
                }

                // Merge before
                if (volunteerinstancePojo.getDateend().compareTo(dateStart) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 1) {
                    mergeBeforeVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
                }

                // Merge after
                if (dateEnd != null && (volunteerinstancePojo.getDatestart().compareTo(dateEnd) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteerinstancePojo.getDatestart()) == 1)) {
                    mergeAfterVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
                }

                // In any case we can just go on searching.
                continue;
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateStart,
                    volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend())) {
                throw new Exception("VolunteerInstanceAPI.error.overlap");
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateEnd,
                    volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend())) {
                throw new Exception("VolunteerInstanceAPI.error.overlap");
            }

            if (DateTimeUtil.isContained(volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend(),
                    dateStart, dateEnd)) {
                throw new Exception("VolunteerInstanceAPI.error.completeOverlap");
            }

            // Determine whether we can actually merge with this line.
            // Merge after: (note that if both dates are null, we can never merge.
            if (dateEnd != null && volunteerinstancePojo.getDatestart() != null &&
                    (volunteerinstancePojo.getDatestart().compareTo(dateEnd) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteerinstancePojo.getDatestart()) == 1)) {
                mergeAfterVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
            }

            // Merge before: (note that dateEnd must be non-null to merge. Also dateStart is never null).
            if (volunteerinstancePojo.getDateend() != null &&
                    (volunteerinstancePojo.getDateend().compareTo(dateStart) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 1)) {
                mergeBeforeVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
            }
        }

        // If we actually reach this point, we know the line will be added to the database (merged or not).
        VolunteerinstancePojo volunteerinstancePojo = new VolunteerinstancePojo();
        volunteerinstancePojo.setVolunteerid(volunteerinstancePojoNew.getVolunteerid());

        // Merge after if possible.
        if (mergeAfterVolunteerInstanceExtId != null) {
            VolunteerinstancePojo mergedVolunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, mergeAfterVolunteerInstanceExtId);
            volunteerinstancePojo.setDateend(mergedVolunteerinstancePojo.getDateend());
            volunteerInstanceMyDao.delete(mergedVolunteerinstancePojo);
        } else {
            // Date start must still be set.
            volunteerinstancePojo.setDateend(dateEnd);
        }

        // Merge before if possible.
        if (mergeBeforeVolunteerInstanceExtId != null) {
            VolunteerinstancePojo mergedVolunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, mergeAfterVolunteerInstanceExtId);
            volunteerinstancePojo.setDatestart(mergedVolunteerinstancePojo.getDatestart());
            volunteerInstanceMyDao.delete(mergedVolunteerinstancePojo);
        } else {
            // Date start must still be set.
            volunteerinstancePojo.setDatestart(dateStart);
        }

        if (!isUpdate) {
            volunteerInstanceMyDao.insertPojo(volunteerinstancePojo);
        } else {
            // Check that we can actually update without affecting matches.
            List<String> merged = new ArrayList();
            if (mergeAfterVolunteerInstanceExtId != null) {
                merged.add(mergeAfterVolunteerInstanceExtId);
            }
            if (mergeBeforeVolunteerInstanceExtId != null) {
                merged.add(mergeBeforeVolunteerInstanceExtId);
            }
            if (!allMatchesAreInsideInstance(volunteerExtId, volunteerinstancePojo, merged)) {
                throw new Exception("VolunteerInstanceAPI.error.matchWithoutInstance");
            }

            volunteerInstanceMyDao.update(volunteerinstancePojo);
        }

        return true;
    }

    /**
     * Check if all the matches are still inside instances when we edit this instance.
     * @param volunteerinstancePojo The new pojo.
     * @return boolean
     */
    private boolean allMatchesAreInsideInstance(String volunteerExtId, VolunteerinstancePojo volunteerinstancePojo, List<String> removeInstanceExtIds) {
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        List<VolunteerinstancePojo> volunteerinstancePojos = volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId);

        // Remove the merged from the list.
        List<VolunteerinstancePojo> removePojos = new ArrayList();
        // Also remove the current pojo we are going to updateStudent, since we are going to "overwrite" this one.
        removeInstanceExtIds.add(volunteerinstancePojo.getExternalidentifier());
        for (VolunteerinstancePojo v : volunteerinstancePojos) {
            for (String s : removeInstanceExtIds) {
                if (v.getExternalidentifier().equals(s)) {
                    removePojos.add(v);
                }
            }
        }

        for (VolunteerinstancePojo v : removePojos) {
            volunteerinstancePojos.remove(v);
        }
        volunteerinstancePojos.add(volunteerinstancePojo);

        // Just check that all the matches are completely inside any pojo.
        for (VolunteermatchPojo volunteermatchPojo : volunteerMatchMyDao.getMatchForVolunteer(volunteerExtId)) {
            if (!isMatchCompletelyInsideInstance(volunteermatchPojo, volunteerinstancePojos)) {
                // There is a match that does not fit completely inside an instance. Therefore we cannot edit.
                return false;
            }
        }

        return true;
    }

    private boolean isMatchCompletelyInsideInstance(VolunteermatchPojo volunteermatchPojo, List<VolunteerinstancePojo> list) {
        for (VolunteerinstancePojo v : list) {
            if (DateTimeUtil.isContained(volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend(),
                    v.getDatestart(), v.getDateend())) {
                return true;
            }
        }

        return false;
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
