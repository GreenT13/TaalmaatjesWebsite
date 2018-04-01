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
import com.apon.service.valueobject.StringValueObject;
import com.apon.service.valueobject.StudentValueObject;
import com.apon.service.valueobject.mapper.StudentMapper;

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
     * @return List&lt;StudentValueObject&gt;
     */
    @GET
    @Path("{studentExtId}")
    @InjectContext
    public StudentValueObject get(@PathParam("studentExtId") String studentExtId) throws Exception {
        // Mapper and Dao variables.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        VolunteerMatchMyDao volunteerMatchMyDao = new VolunteerMatchMyDao(context);
        StudentMapper studentMapper = new StudentMapper();

        // Retrieve the student.
        StudentPojo studentPojo = studentMyDao.fetchOneByExternalidentifier(studentExtId);
        if (studentPojo == null) {
            throw new FunctionalException("StudentService.notFound.student");
        }
        studentMapper.setStudentPojo(studentPojo);

        // Retrieve the matches.
        List<QueryResult<VolunteermatchPojo, VolunteerPojo>> result = volunteerMatchMyDao.getMatchForStudent(studentExtId, false);
        if (result == null) {
            throw new FunctionalException("StudentService.notFound.volunteerMatch");
        }
        studentMapper.setMatchList(result);

        // Return the student.
        return studentMapper.getStudentValueObject();
    }



    /**
     * Add a Student in the database.
     * @param studentValueObject The student object.
     * @return The extId from the student that is inserted.
     */
    @PUT
    @InjectContext
    public StringValueObject insert(StudentValueObject studentValueObject) throws Exception {
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.setStudentValueObject(studentValueObject);

        StudentMyDao studentMyDao = new StudentMyDao(context);
        StudentPojo studentPojo = studentMapper.getStudentPojo();

        if (!studentMyDao.insertPojo(studentPojo)) {
            throw new FunctionalException(studentMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();

        // Return the extId.
        return new StringValueObject(studentPojo.getExternalidentifier());
    }

    /**
     * Update a Student in the database with a certain extId.
     * Note that you overwrite ALL fields on the student. You have to fill every parameter.
     * Also note that you can only change details from Student, not matches.
     * @param studentExtId The extId to identify the student.
     * @param studentValueObject The student object.
     */
    @POST
    @Path("{studentExtId}")
    @InjectContext
    public void update(@PathParam("studentExtId") String studentExtId,
                       StudentValueObject studentValueObject) throws Exception {
        // Retrieve the student.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        StudentPojo studentPojo = studentMyDao.fetchOneByExternalidentifier(studentExtId);
        if (studentPojo == null) {
            throw new FunctionalException("StudentService.notFound.student");
        }

        // There is no way to indicate whether a field was not available. So just copy everything from the value object
        // to the mapper.
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.setStudentPojo(studentPojo);
        studentMapper.setStudentValueObject(studentValueObject);

        if (!studentMyDao.updatePojo(studentMapper.getStudentPojo())) {
            throw new FunctionalException(studentMyDao.getResultObject());
        }

        // Commit the changes.
        context.commit();
    }


    /**
     * Retrieve list of volunteers based on search parameters.
     * @param search Search for this text in Student.firstName, Student.insertion and Student.lastName.
     * @param hasMatch Whether there is a VolunteerMatch today.
     * @return List&lt;StudentValueObject&gt;
     */
    @GET
    @InjectContext
    public List<StudentValueObject> searchStudents(@QueryParam("search") String search,
                                                   @QueryParam("hasMatch") Boolean hasMatch) throws Exception {
        // Retrieve the list from the database.
        StudentMyDao studentMyDao = new StudentMyDao(context);
        List<StudentPojo> studentPojos = studentMyDao.advancedSearch(search, hasMatch);
        if (studentPojos == null) {
            throw new FunctionalException("StudentService.search.error");
        }

        // Convert the list of pojo's to value objects.
        List<StudentValueObject> studentValueObjects = new ArrayList();
        for (StudentPojo studentPojo : studentPojos) {
            StudentMapper studentMapper = new StudentMapper();
            studentMapper.setStudentPojo(studentPojo);

            studentValueObjects.add(studentMapper.getStudentValueObject());
        }

        return studentValueObjects;
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
