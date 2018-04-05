package com.apon.service;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.VolunteerInstanceMyDao;
import com.apon.database.mydao.VolunteerMatchMyDao;
import com.apon.database.mydao.VolunteerMyDao;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringVO;
import com.apon.service.valueobject.database.VolunteerInstanceDVO;
import com.apon.service.valueobject.database.mapper.VolunteerInstanceDVOMapper;
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

    /**
     * Retrieves a volunteerInstance.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerInstanceExtId The extId from the instance.
     * @return VolunteerInstanceDVO
     */
    @GET
    @Path("{volunteerInstanceExtId}")
    @InjectContext
    public VolunteerInstanceDVO get(@PathParam("volunteerExtId") String volunteerExtId,
                                    @PathParam("volunteerInstanceExtId") String volunteerInstanceExtId) throws Exception {
        // Variables
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerInstanceDVOMapper volunteerInstanceDVOMapper = new VolunteerInstanceDVOMapper();

        // Retrieve volunteerInstance.
        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, volunteerInstanceExtId);
        if (volunteerinstancePojo == null) {
            throw new FunctionalException("VolunteerInstanceService.notFound.instance");
        }
        volunteerInstanceDVOMapper.setVolunteerinstancePojo(volunteerinstancePojo);

        // Retrieve the volunteer.
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerInstanceService.notFound.volunteer");
        }
        volunteerInstanceDVOMapper.setVolunteerPojo(volunteerPojo);

        return volunteerInstanceDVOMapper.getVolunteerInstanceDVO();
    }

    /**
     * Add a VolunteerInstance in the database.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerInstanceDVO The instance object.
     * @return The extId from the instance that is inserted.
     */
    @PUT
    @InjectContext
    public StringVO insert(@PathParam("volunteerExtId") String volunteerExtId,
                           VolunteerInstanceDVO volunteerInstanceDVO) throws Exception {
        VolunteerInstanceDVOMapper volunteerInstanceDVOMapper = new VolunteerInstanceDVOMapper();
        volunteerInstanceDVOMapper.setVolunteerInstanceDVO(volunteerInstanceDVO);

        // Get volunteerId.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerInstanceService.notFound.volunteer");
        }

        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceDVOMapper.getVolunteerinstancePojo();
        volunteerinstancePojo.setVolunteerid(volunteerPojo.getVolunteerid());

        // Handle the complete adding / merging in another function. This function will throw its exceptions.
        isVolunteerInstanceValidAndAdd(volunteerExtId, volunteerinstancePojo, false);

        // Commit the changes.
        context.commit();

        // Return the extId.
        return new StringVO(volunteerinstancePojo.getExternalidentifier());
    }

    /**
     * Update a VolunteerInstance in the database with a certain extId.
     * Note that you overwrite ALL fields on the instance. You have to fill every parameter.
     * @param volunteerExtId The extId to identify the volunteer.
     * @param volunteerInstanceExtId The extId to identify the instance.
     * @param volunteerInstanceDVO The instance object.
     */
    @POST
    @Path("{volunteerInstanceExtId}")
    @InjectContext
    public void update(@PathParam("volunteerExtId") String volunteerExtId,
                       @PathParam("volunteerInstanceExtId") String volunteerInstanceExtId,
                       VolunteerInstanceDVO volunteerInstanceDVO) throws Exception {
        // Retrieve the instance.
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, volunteerInstanceExtId);
        if (volunteerinstancePojo == null) {
            throw new FunctionalException("VolunteerInstanceService.notFound.instance");
        }

        VolunteerInstanceDVOMapper volunteerInstanceDVOMapper = new VolunteerInstanceDVOMapper();
        // Make sure the id's are set.
        volunteerInstanceDVOMapper.setVolunteerinstancePojo(volunteerinstancePojo);
        // Overwrite the rest.
        volunteerInstanceDVOMapper.setVolunteerInstanceDVO(volunteerInstanceDVO);

        // Handle the complete adding / merging in another function.
        isVolunteerInstanceValidAndAdd(volunteerExtId, volunteerInstanceDVOMapper.getVolunteerinstancePojo(), true);

        // Commit the changes.
        context.commit();
    }

    /**
     * Delete instance from the database. Checks if there are no matches inside the instance.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerInstanceExtId The extId from the volunteer instance.
     */
    @DELETE
    @Path("{volunteerInstanceExtId}")
    @InjectContext
    public void delete(@PathParam("volunteerExtId") String volunteerExtId,
                       @PathParam("volunteerInstanceExtId") String volunteerInstanceExtId) throws Exception {
        // Retrieve the instance.
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerinstancePojo volunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, volunteerInstanceExtId);
        if (volunteerinstancePojo == null) {
            throw new FunctionalException("VolunteerInstanceService.notFound.instance");
        }

        // Check that there are no matches contained inside the instance.
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        for (VolunteermatchPojo volunteermatchPojo : volunteerMatchMyDao.getMatchForVolunteer(volunteerExtId)) {
            if (isMatchCompletelyInsideInstance(volunteermatchPojo, volunteerinstancePojo)) {
                // This match would fall outside of activity range, hence we cannot delete the instance.
                throw new FunctionalException("VolunteerInstanceService.error.matchWithoutInstance");
            }
        }

        // Delete the instance.
        volunteerInstanceMyDao.delete(volunteerinstancePojo);

        // Commit the changes.
        context.commit();
    }

    /**
     * Check if the line can be added to the database. Merge the line if needed. Returns true if added (possibly merged)
     * and return false if some verification failed.
     * @param volunteerExtId The extId from the volunteer.
     * @param volunteerinstancePojoNew The pojo we are going to insert into the database.
     * @param isUpdate Whether we call this function to update the pojo or insert the pojo.
     */
    private void isVolunteerInstanceValidAndAdd(String volunteerExtId, VolunteerinstancePojo volunteerinstancePojoNew, boolean isUpdate)
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
                    DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend()) == 0) {
                // We have 4 possibilities:
                // 1. pojo.date \in (dateStart, dateEnd) => overlap so false.
                // 2. pojo.date == dateStart || pojo.date + 1 DAY = dateStart => merge
                // 3. pojo.date == dateEnd || pojo.date + 1 DAY = dateEnd => merge
                // 4. none of the above => we do nothing, we can ignore this line.

                if (DateTimeUtil.isBetweenWithoutEndpoints(volunteerinstancePojo.getDatestart(), dateStart, dateEnd)) {
                    throw new FunctionalException("VolunteerInstanceService.error.overlap");
                }

                // Merge before
                if (DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 1) {
                    mergeBeforeVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
                }

                // Merge after
                if (dateEnd != null && (DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDatestart(), dateEnd) == 0 ||
                        DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteerinstancePojo.getDatestart()) == 1)) {
                    mergeAfterVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
                }

                // In any case we can just go on searching.
                continue;
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateStart,
                    volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend())) {
                throw new FunctionalException("VolunteerInstanceService.error.overlap");
            }

            if (DateTimeUtil.isBetweenWithoutEndpoints(dateEnd,
                    volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend())) {
                throw new FunctionalException("VolunteerInstanceService.error.overlap");
            }

            if (DateTimeUtil.isContained(volunteerinstancePojo.getDatestart(), volunteerinstancePojo.getDateend(),
                    dateStart, dateEnd)) {
                throw new FunctionalException("VolunteerInstanceAPI.error.completeOverlap");
            }

            // Determine whether we can actually merge with this line.
            // Merge after: (note that if both dates are null, we can never merge.
            if (dateEnd != null && volunteerinstancePojo.getDatestart() != null &&
                    (DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDatestart(), dateEnd) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(dateEnd, volunteerinstancePojo.getDatestart()) == 1)) {
                mergeAfterVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
            }

            // Merge before: (note that dateEnd must be non-null to merge. Also dateStart is never null).
            if (volunteerinstancePojo.getDateend() != null &&
                    (DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 0 ||
                            DateTimeUtil.nrOfDaysInBetween(volunteerinstancePojo.getDateend(), dateStart) == 1)) {
                mergeBeforeVolunteerInstanceExtId = volunteerinstancePojo.getExternalidentifier();
            }
        }

        // If we actually reach this point, we know the line will be added to the database (merged or not).

        // Merge after if possible.
        if (mergeAfterVolunteerInstanceExtId != null) {
            VolunteerinstancePojo mergedVolunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, mergeAfterVolunteerInstanceExtId);
            volunteerinstancePojoNew.setDateend(mergedVolunteerinstancePojo.getDateend());
            volunteerInstanceMyDao.delete(mergedVolunteerinstancePojo);
        }

        // Merge before if possible.
        if (mergeBeforeVolunteerInstanceExtId != null) {
            VolunteerinstancePojo mergedVolunteerinstancePojo = volunteerInstanceMyDao.fetchByExtIds(volunteerExtId, mergeBeforeVolunteerInstanceExtId);
            volunteerinstancePojoNew.setDatestart(mergedVolunteerinstancePojo.getDatestart());
            volunteerInstanceMyDao.delete(mergedVolunteerinstancePojo);
        }

        if (!isUpdate) {
            volunteerInstanceMyDao.insertPojo(volunteerinstancePojoNew);
        } else {
            // Check that we can actually update without affecting matches.
            List<String> merged = new ArrayList();
            if (mergeAfterVolunteerInstanceExtId != null) {
                merged.add(mergeAfterVolunteerInstanceExtId);
            }
            if (mergeBeforeVolunteerInstanceExtId != null) {
                merged.add(mergeBeforeVolunteerInstanceExtId);
            }
            if (!allMatchesAreInsideInstance(volunteerExtId, volunteerinstancePojoNew, merged)) {
                throw new FunctionalException("VolunteerInstanceAPI.error.matchWithoutInstance");
            }

            // We can just update the instance we got from the function.
            volunteerInstanceMyDao.update(volunteerinstancePojoNew);
        }
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

    private boolean isMatchCompletelyInsideInstance(VolunteermatchPojo volunteermatchPojo, VolunteerinstancePojo volunteerinstancePojo) {
        List<VolunteerinstancePojo> volunteerInstancePojos = new ArrayList();
        volunteerInstancePojos.add(volunteerinstancePojo);
        return isMatchCompletelyInsideInstance(volunteermatchPojo, volunteerInstancePojos);
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
