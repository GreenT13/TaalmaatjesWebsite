package com.apon.service;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.*;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringValueObject;
import com.apon.service.valueobject.VolunteerValueObject;
import com.apon.service.valueobject.mapper.VolunteerMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Path("volunteer")
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerService implements IService {
    private DbContext context;

    @GET
    @Path("{volunteerExtId}")
    @InjectContext
    public VolunteerValueObject getVolunteer(@PathParam("volunteerExtId") String volunteerExtId) throws com.apon.exceptionhandler.NotFoundException {
        // Mapper and Dao variables.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        TaskMyDao taskMyDao = new TaskMyDao(context);
        VolunteerMapper volunteerMapper = new VolunteerMapper();

        // Retrieve the volunteer.
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new com.apon.exceptionhandler.NotFoundException("Could not find volunteer");
        }
        volunteerMapper.setVolunteerPojo(volunteerPojo);

        // Retrieve the instances and put them on the volunteer.
        volunteerMapper.setInstanceList(volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId, false));

        // Retrieve the matches and put them on the volunteer.
        volunteerMapper.setMatchList(volunteerMatchMyDao.getMatchForVolunteer(volunteerExtId, false), new StudentMyDao(context));

        // Retrieve all tasks
        volunteerMapper.setTaskList(taskMyDao.getTasksForVolunteer(volunteerPojo.getVolunteerid()));

        return volunteerMapper.getVolunteerValueObject();
    }

    @PUT
    @InjectContext
    public StringValueObject insertVolunteer(VolunteerValueObject volunteerValueObject) throws Exception {
        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerValueObject(volunteerValueObject);

        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMapper.getVolunteerPojo();
        volunteerMyDao.insertPojo(volunteerPojo);

        if (volunteerValueObject.getDateStartActive() != null) {
            VolunteerinstancePojo volunteerinstancePojo = new VolunteerinstancePojo();
            volunteerinstancePojo.setVolunteerid(volunteerPojo.getVolunteerid());
            volunteerinstancePojo.setDatestart(volunteerValueObject.getDateStartActive());
            VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
            volunteerInstanceMyDao.insertPojo(volunteerinstancePojo);
        }

        context.commit();

        return new StringValueObject(volunteerPojo.getExternalidentifier());
    }

    @POST
    @Path("{volunteerExtId}")
    @InjectContext
    public void updateVolunteer(@PathParam("volunteerExtId") String volunteerExtId,
                                VolunteerValueObject volunteerValueObject) throws com.apon.exceptionhandler.NotFoundException {
        // Check that the volunteer exists.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new com.apon.exceptionhandler.NotFoundException("Could not find volunteer");
        }

        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerValueObject(volunteerValueObject);
        volunteerMapper.getVolunteerPojo().setVolunteerid(volunteerPojo.getVolunteerid());
        volunteerMyDao.update(volunteerMapper.getVolunteerPojo());
        context.commit();
    }

    @GET
    @InjectContext
    public List<VolunteerValueObject> searchVolunteers(@QueryParam("search") String input,
                                                       @QueryParam("isActive") Boolean isActive,
                                                       @QueryParam("hasTraining") Boolean hasTraining,
                                                       @QueryParam("hasMatch") Boolean hasMatch,
                                                       @QueryParam("city") String city) {
        // Retrieve the list from the database.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        List<VolunteerPojo> volunteerPojos = volunteerMyDao.advancedSearch(input, isActive, hasTraining, hasMatch, city);

        // Convert the list of pojos to returns.
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
