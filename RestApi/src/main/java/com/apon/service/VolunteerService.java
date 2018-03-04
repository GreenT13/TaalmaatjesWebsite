package com.apon.service;

import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.VolunteerMyDao;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.VolunteerValueObject;
import com.apon.service.valueobject.mapper.VolunteerMapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Path("volunteer")
@Produces(MediaType.APPLICATION_JSON)
@RequiresAuthentication
@RequiresUser
public class VolunteerService implements IService {
    private DbContext context;

    @GET
    @Path("{volunteerExtId}")
    @InjectContext
    public VolunteerValueObject getVolunteer(@PathParam("volunteerExtId") String volunteerExtId) throws com.apon.exceptionhandler.NotFoundException {
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new com.apon.exceptionhandler.NotFoundException("Could not find volunteer");
        }
        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerPojo(volunteerPojo);

        return volunteerMapper.getVolunteerValueObject();
    }

    @PUT
    @InjectContext
    public void insertVolunteer(VolunteerValueObject volunteerValueObject) {
        VolunteerMapper volunteerMapper = new VolunteerMapper();
        volunteerMapper.setVolunteerValueObject(volunteerValueObject);

        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        volunteerMyDao.insertPojo(volunteerMapper.getVolunteerPojo());
        context.commit();
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
        volunteerMapper.setVolunteerValueObject(volunteerValueObject, volunteerPojo.getVolunteerid());
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
