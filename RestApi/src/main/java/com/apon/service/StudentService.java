package com.apon.service;

import com.apon.database.generated.tables.pojos.StudentPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.QueryResult;
import com.apon.database.mydao.StudentMyDao;
import com.apon.database.mydao.VolunteerMatchMyDao;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.StringVO;
import com.apon.service.valueobject.StudentVOGet;
import com.apon.service.valueobject.database.StudentDVO;
import com.apon.service.valueobject.database.VolunteerMatchDVO;
import com.apon.service.valueobject.database.mapper.StudentDVOMapper;
import com.apon.service.valueobject.database.mapper.VolunteerMatchDVOMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "RedundantThrows"})
@Path("student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentService implements IService {
    private DbContext context;

    /**
     * Retrieves all information connected to a student from the database.
     * 1. Student
     * 3. StudentMatch
     * @param studentExtId The extId from the volunteer.
     * @return StudentVOGet
     */
    @GET
    @Path("{studentExtId}")
    @InjectContext
    public StudentVOGet get(@PathParam("studentExtId") String studentExtId) throws Exception {
        // Variables
        StudentMyDao studentMyDao = new StudentMyDao(context);
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        StudentVOGet studentVOGet = new StudentVOGet();

        // Retrieve the student.
        StudentPojo studentPojo = studentMyDao.fetchOneByExternalidentifier(studentExtId);
        if (studentPojo == null) {
            throw new FunctionalException("StudentService.notFound.student");
        }
        StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
        studentDVOMapper.setStudentPojo(studentPojo);
        studentVOGet.setStudentDVO(studentDVOMapper.getStudentDVO());

        // Retrieve the matches.
        List<QueryResult<VolunteermatchPojo, VolunteerPojo>> result = volunteerMatchMyDao.getMatchForStudent(studentExtId, false);
        if (result == null) {
            throw new FunctionalException("StudentService.notFound.volunteerMatch");
        }

        // Convert list of pojo's to list of DVO's for the studentVOGet.
        List<VolunteerMatchDVO> volunteerMatchDVOs = new ArrayList();
        for (QueryResult<VolunteermatchPojo, VolunteerPojo> queryResult : result) {
            VolunteerMatchDVOMapper volunteerMatchDVOMapper = new VolunteerMatchDVOMapper();
            volunteerMatchDVOMapper.setVolunteermatchPojo(queryResult.getS());
            volunteerMatchDVOMapper.setVolunteerPojo(queryResult.getT());

            volunteerMatchDVOs.add(volunteerMatchDVOMapper.getVolunteerMatchDVO());
        }
        studentVOGet.setVolunteerMatchDVOS(volunteerMatchDVOs);

        // Return the student.
        return studentVOGet;
    }

    /**
     * Add a Student in the database.
     * @param studentDVO The student object.
     * @return The extId from the student that is inserted.
     */
    @PUT
    @InjectContext
    public StringVO insert(StudentDVO studentDVO) throws Exception {
        StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
        studentDVOMapper.setStudentDVO(studentDVO);

        StudentMyDao studentMyDao = new StudentMyDao(context);
        StudentPojo studentPojo = studentDVOMapper.getStudentPojo();

        if (!studentMyDao.insertPojo(studentPojo)) {
            throw new FunctionalException(studentMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();

        // Return the extId.
        return new StringVO(studentPojo.getExternalidentifier());
    }

    /**
     * Update a Student in the database with a certain extId.
     * Note that you overwrite ALL fields on the student. You have to fill every parameter.
     * Also note that you can only change details from Student, not matches.
     * @param studentExtId The extId to identify the student.
     * @param studentDVO The student object.
     */
    @POST
    @Path("{studentExtId}")
    @InjectContext
    public void update(@PathParam("studentExtId") String studentExtId,
                       StudentDVO studentDVO) throws Exception {
        // Retrieve the student.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        StudentPojo studentPojo = studentMyDao.fetchOneByExternalidentifier(studentExtId);
        if (studentPojo == null) {
            throw new FunctionalException("StudentService.notFound.student");
        }

        // There is no way to indicate whether a field was not available. So just copy everything from the value object
        // to the mapper.
        StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
        studentDVOMapper.setStudentPojo(studentPojo);
        studentDVOMapper.setStudentDVO(studentDVO);

        if (!studentMyDao.updatePojo(studentDVOMapper.getStudentPojo())) {
            throw new FunctionalException(studentMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();
    }


    /**
     * Retrieve list of students based on search parameters.
     * @param search Search for this text in Student.firstName, Student.insertion and Student.lastName.
     * @param hasMatch Whether there is a VolunteerMatch today.
     * @return List&lt;StudentDVO&gt;
     */
    @GET
    @InjectContext
    public List<StudentDVO> searchStudents(@QueryParam("search") String search,
                                           @QueryParam("hasMatch") Boolean hasMatch) throws Exception {
        // Retrieve the list from the database.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        List<StudentPojo> studentPojos = studentMyDao.advancedSearch(search, hasMatch);
        if (studentPojos == null) {
            throw new FunctionalException("StudentService.search.error");
        }

        // Convert the list of pojo's to value objects.
        List<StudentDVO> studentDVOS = new ArrayList();
        for (StudentPojo studentPojo : studentPojos) {
            StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
            studentDVOMapper.setStudentPojo(studentPojo);

            studentDVOS.add(studentDVOMapper.getStudentDVO());
        }

        return studentDVOS;
    }

    /**
     * Retrieve students that are available for matching.
     * @return List&lt;StudentDVO&gt;
     */
    @GET
    @Path("match")
    @InjectContext
    public List<StudentDVO> getStudentsForMatch() throws Exception {
        // At this moment it is only hasMatch == false, but later it might be more.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        List<StudentPojo> studentPojos = studentMyDao.advancedSearch(null, false);
        if (studentPojos == null) {
            throw new FunctionalException("StudentService.search.error");
        }

        // Convert the list of pojo's to value objects.
        List<StudentDVO> studentDVOS = new ArrayList();
        for (StudentPojo studentPojo : studentPojos) {
            StudentDVOMapper studentDVOMapper = new StudentDVOMapper();
            studentDVOMapper.setStudentPojo(studentPojo);

            studentDVOS.add(studentDVOMapper.getStudentDVO());
        }

        return studentDVOS;
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
