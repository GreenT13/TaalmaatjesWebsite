package com.apon.service;

import com.apon.database.generated.tables.pojos.*;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.*;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringVO;
import com.apon.service.valueobject.VolunteerVOGet;
import com.apon.service.valueobject.VolunteerVOSearch;
import com.apon.service.valueobject.database.TaskDVO;
import com.apon.service.valueobject.database.VolunteerDVO;
import com.apon.service.valueobject.database.VolunteerInstanceDVO;
import com.apon.service.valueobject.database.VolunteerMatchDVO;
import com.apon.service.valueobject.database.mapper.TaskDVOMapper;
import com.apon.service.valueobject.database.mapper.VolunteerDVOMapper;
import com.apon.service.valueobject.database.mapper.VolunteerInstanceDVOMapper;
import com.apon.service.valueobject.database.mapper.VolunteerMatchDVOMapper;
import com.apon.util.DateTimeUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @return List&lt;VolunteerDVO&gt;
     */
    @GET
    @Path("{volunteerExtId}")
    @InjectContext
    public VolunteerVOGet get(@PathParam("volunteerExtId") String volunteerExtId) throws Exception {
        // Variables
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerInstanceMyDao volunteerInstanceMyDao = new VolunteerInstanceMyDao(context);
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        TaskMyDao taskMyDao = new TaskMyDao(context);
        VolunteerVOGet volunteerVOGet = new VolunteerVOGet();

        // Retrieve the volunteer.
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteer");
        }
        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerPojo(volunteerPojo);
        volunteerVOGet.setVolunteerDVO(volunteerDVOMapper.getVolunteerDVO());

        // Retrieve the instances.
        List<VolunteerinstancePojo> volunteerinstancePojos = volunteerInstanceMyDao.getInstanceForVolunteer(volunteerExtId, false);
        if (volunteerinstancePojos == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteerInstance");
        }
        List<VolunteerInstanceDVO> volunteerInstanceDVOS = new ArrayList();
        for (VolunteerinstancePojo volunteerinstancePojo : volunteerinstancePojos) {
            VolunteerInstanceDVOMapper volunteerInstanceDVOMapper = new VolunteerInstanceDVOMapper();
            volunteerInstanceDVOMapper.setVolunteerinstancePojo(volunteerinstancePojo);
            volunteerInstanceDVOS.add(volunteerInstanceDVOMapper.getVolunteerInstanceDVO());
        }
        volunteerVOGet.setVolunteerInstanceDVOS(volunteerInstanceDVOS);

        // Retrieve the matches.
        List<QueryResult<VolunteermatchPojo, StudentPojo>> result = volunteerMatchMyDao.getMatchForVolunteerWithStudent(volunteerExtId, false);
        if (result == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteerMatch");
        }
        List<VolunteerMatchDVO> volunteerMatchDVOS = new ArrayList();
        for (QueryResult<VolunteermatchPojo, StudentPojo> queryResult : result) {
            VolunteerMatchDVOMapper volunteerMatchDVOMapper = new VolunteerMatchDVOMapper();
            volunteerMatchDVOMapper.setVolunteermatchPojo(queryResult.getS());
            volunteerMatchDVOMapper.setStudentPojo(queryResult.getT());
            volunteerMatchDVOS.add(volunteerMatchDVOMapper.getVolunteerMatchDVO());
        }
        volunteerVOGet.setVolunteerMatchDVOS(volunteerMatchDVOS);

        // Retrieve the tasks.
        List<TaskPojo> taskPojos = taskMyDao.getTasksForVolunteer(volunteerExtId);
        if (taskPojos == null) {
            throw new FunctionalException("VolunteerService.notFound.task");
        }
        List<TaskDVO> taskDVOS = new ArrayList();
        for (TaskPojo taskPojo : taskPojos) {
            TaskDVOMapper taskDVOMapper = new TaskDVOMapper();
            taskDVOMapper.setTaskPojo(taskPojo);
            taskDVOS.add(taskDVOMapper.getTaskDVO());
        }
        volunteerVOGet.setTaskDVOS(taskDVOS);

        // Return the volunteer.
        return volunteerVOGet;
    }

    /**
     * Add a Volunteer in the database. If dateStartActive is filled, we also add a VolunteerInstance to the database.
     * @param stringStartActive (optional) Date as a string in "dd-MM-yyyy" format.
     * @param volunteerDVO The volunteer object.
     * @return The extId from the volunteer that is inserted.
     */
    @PUT
    @InjectContext
    public StringVO insert(@QueryParam("dateStartActive") String stringStartActive,
                           VolunteerDVO volunteerDVO) throws Exception {
        Date dateStartActive = stringStartActive != null ? DateTimeUtil.convertStringToDate(stringStartActive) : null;

        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerDVO(volunteerDVO);

        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerDVOMapper.getVolunteerPojo();
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
        return new StringVO(volunteerPojo.getExternalidentifier());
    }

    /**
     * Update a Volunteer in the database with a certain extId.
     * Note that you overwrite ALL fields on the volunteer. You have to fill every parameter.
     * Also note that you can only change details from Volunteer, not instances, matches or tasks.
     * @param volunteerExtId The extId to identify the volunteer.
     * @param volunteerDVO The volunteer object.
     */
    @POST
    @Path("{volunteerExtId}")
    @InjectContext
    public void update(@PathParam("volunteerExtId") String volunteerExtId,
                       VolunteerDVO volunteerDVO) throws Exception {
        // Retrieve the volunteer.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        VolunteerPojo volunteerPojo = volunteerMyDao.fetchOneByExternalidentifier(volunteerExtId);
        if (volunteerPojo == null) {
            throw new FunctionalException("VolunteerService.notFound.volunteer");
        }

        // There is no way to indicate whether a field was not available. So just copy everything from the value object
        // to the mapper. Only set the volunteerId from the pojo.
        VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
        volunteerDVOMapper.setVolunteerDVO(volunteerDVO);
        volunteerDVOMapper.getVolunteerPojo().setVolunteerid(volunteerPojo.getVolunteerid());

        if (!volunteerMyDao.updatePojo(volunteerDVOMapper.getVolunteerPojo())) {
            throw new FunctionalException(volunteerMyDao.getResultObject());
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
     * @return List&lt;VolunteerVOSearch&gt;
     */
    @GET
    @InjectContext
    public List<VolunteerVOSearch> searchVolunteers(@QueryParam("search") String input,
                                                    @QueryParam("isActive") Boolean isActive,
                                                    @QueryParam("hasTraining") Boolean hasTraining,
                                                    @QueryParam("hasMatch") Boolean hasMatch,
                                                    @QueryParam("city") String city) throws Exception {
        // Retrieve the list from the database.
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        Map<VolunteerPojo, Integer> volunteerPojoIntegerMap = volunteerMyDao.advancedSearch(input, isActive, hasTraining, hasMatch, city);
        if (volunteerPojoIntegerMap == null) {
            throw new FunctionalException("VolunteerService.search.error");
        }

        // Convert the list of pojo's to value objects.
        List<VolunteerVOSearch> volunteerVOSearches = new ArrayList();
        for (Map.Entry<VolunteerPojo, Integer> entry : volunteerPojoIntegerMap.entrySet()) {
            VolunteerDVOMapper volunteerDVOMapper = new VolunteerDVOMapper();
            volunteerDVOMapper.setVolunteerPojo(entry.getKey());
            VolunteerVOSearch volunteerVOSearch = new VolunteerVOSearch();
            volunteerVOSearch.setVolunteerDVO(volunteerDVOMapper.getVolunteerDVO());
            volunteerVOSearch.setNumberOfMatches(entry.getValue());
            volunteerVOSearches.add(volunteerVOSearch);
        }

        return volunteerVOSearches;
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
