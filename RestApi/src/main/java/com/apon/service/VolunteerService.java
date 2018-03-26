package com.apon.service;

import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.*;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringValueObject;
import com.apon.service.valueobject.VolunteerValueObject;
import com.apon.service.valueobject.mapper.VolunteerMapper;
import com.apon.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "RedundantThrows"})
@Path("volunteer")
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerService implements IService {
    private DbContext context;

    /**
     * Retrieves all information connected to a volunteer from the database.
     * 1. Volunteer
     * 2. VolunteerInstance
     * 3. VolunteerMatch
     * 4. Task
     * @param volunteerExtId The extId from the volunteer.
     * @return List&lt;VolunteerValueObject&gt;
     */
    @GET
    @Path("{volunteerExtId}")
    @InjectContext
    public VolunteerValueObject get(@PathParam("volunteerExtId") String volunteerExtId) throws Exception {
        // Mapper and Dao variables.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        TaskMyDao taskMyDao = new TaskMyDao(context);
        VolunteerMapper volunteerMapper = new VolunteerMapper();

        // Retrieve the volunteer.
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteer");
        }
        volunteerMapper.setVolunteerPojo(volunteerPojo);

        // Retrieve the instances.
        List<VolunteerinstancePojo> volunteerinstancePojos = volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId, false);
        if (volunteerinstancePojos == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteerInstance");
        }
        volunteerMapper.setInstanceList(volunteerinstancePojos);

        // Retrieve the matches.
        List<VolunteermatchPojo> volunteermatchPojos = volunteerMatchMyDao.getMatchForVolunteer(volunteerExtId, false);
        if (volunteermatchPojos == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteerMatch");
        }
        // TODO: remove this StudentMyDao.
        volunteerMapper.setMatchList(volunteermatchPojos, new StudentMyDao(context));

        // Retrieve the tasks.
        List<TaskPojo> taskPojos = taskMyDao.getTasksForVolunteer(volunteerExtId);
        if (taskPojos == null) {
            throw new FunctionalException("VolunteerService.notFound.task");
        }
        volunteerMapper.setTaskList(taskPojos);

        // Return the volunteer.
        return volunteerMapper.getVolunteerValueObject();
    }

    /**
     * Add a Volunteer in the database. If dateStartActive is filled, we also add a VolunteerInstance to the database.
     * @param stringStartActive (optional) Date as a string in "dd-MM-yyyy" format.
     * @param volunteerValueObject The volunteer object.
     * @return The extId from the volunteer that is inserted.
     */
    @PUT
    @InjectContext
    public StringValueObject insert(@QueryParam("dateStartActive") String stringStartActive,
                                    VolunteerValueObject volunteerValueObject) throws Exception {
        Date dateStartActive = stringStartActive != null ? DateTimeUtil.convertStringToDate(stringStartActive) : null;

        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerValueObject(volunteerValueObject);

        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMapper.getVolunteerPojo();
        if (!volunteerMyDao.insertPojo(volunteerPojo)) {
            throw new FunctionalException(volunteerMyDao.getResultObject());
        }

        // Whenever the dateStartActive is filled, we create a VolunteerInstance row starting on this date.
        if (dateStartActive != null) {
            VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
            VolunteerinstancePojo volunteerinstancePojo = new VolunteerinstancePojo();

            volunteerinstancePojo.setVolunteerid(volunteerPojo.getVolunteerid());
            volunteerinstancePojo.setDatestart(dateStartActive);

            if (!volunteerInstanceMyDao.insertPojo(volunteerinstancePojo)) {
                throw new FunctionalException(volunteerInstanceMyDao.getResultObject());
            }
        }

        // Commit the changes.
        context.commit();

        // Return the extId.
        return new StringValueObject(volunteerPojo.getExternalidentifier());
    }

    /**
     * Update a Volunteer in the database with a certain extId.
     * Note that you overwrite ALL fields on the volunteer. You have to fill every parameter.
     * Also note that you can only change details from Volunteer, not instances, matches or tasks.
     * @param volunteerExtId The extId to identify the volunteer.
     * @param volunteerValueObject The volunteer object.
     */
    @POST
    @Path("{volunteerExtId}")
    @InjectContext
    public void update(@PathParam("volunteerExtId") String volunteerExtId,
                                VolunteerValueObject volunteerValueObject) throws Exception {
        // Retrieve the volunteer.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteer");
        }

        // There is no way to indicate whether a field was not available. So just copy everything from the value object
        // to the mapper. Only set the volunteerId from the pojo.
        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerValueObject(volunteerValueObject);
        volunteerMapper.getVolunteerPojo().setVolunteerid(volunteerPojo.getVolunteerid());

        if (!volunteerMyDao.updatePojo(volunteerMapper.getVolunteerPojo())) {
            throw new FunctionalException("VolunteerService.update.error");
        }

        // Commit the changes.
        context.commit();
    }

    /**
     * Retrieve list of volunteers based on search parameters.
     * @param input Search for this text in Volunteer.firstName, Volunteer.insertion and Volunteer.lastName.
     * @param isActive Whether there is a VolunteerInstance today.
     * @param hasTraining Whether Volunteer.dateTraining is filled.
     * @param hasMatch Whether there is a VolunteerMatch today.
     * @param city Search for this text in Volunteer.city.
     * @return List&lt;VolunteerValueObject&gt;
     */
    @GET
    @InjectContext
    public List<VolunteerValueObject> searchVolunteers(@QueryParam("search") String input,
                                                       @QueryParam("isActive") Boolean isActive,
                                                       @QueryParam("hasTraining") Boolean hasTraining,
                                                       @QueryParam("hasMatch") Boolean hasMatch,
                                                       @QueryParam("city") String city) throws Exception {
        // Retrieve the list from the database.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        List<VolunteerPojo> volunteerPojos = volunteerMyDao.advancedSearch(input, isActive, hasTraining, hasMatch, city);
        if (volunteerPojos == null) {
            throw new FunctionalException("VolunteerService.search.error");
        }

        // Convert the list of pojo's to value objects.
        List<VolunteerValueObject> volunteerValueObjects = new ArrayList();
        for (VolunteerPojo volunteerPojo : volunteerPojos) {
            VolunteerMapper volunteerMapper = new VolunteerMapper();
            volunteerMapper.setVolunteerPojo(volunteerPojo);

            volunteerValueObjects.add(volunteerMapper.getVolunteerValueObject());
        }

        return volunteerValueObjects;
    }

    @Override
    public DbContext getContext() {
        return context;
    }

    @Override
    public void setContext(DbContext context) {
        this.context = context;
    }
}
