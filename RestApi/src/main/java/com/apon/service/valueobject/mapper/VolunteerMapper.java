package com.apon.service.valueobject.mapper;

import com.apon.database.generated.tables.pojos.TaskPojo;
import com.apon.database.generated.tables.pojos.VolunteerPojo;
import com.apon.database.generated.tables.pojos.VolunteerinstancePojo;
import com.apon.database.generated.tables.pojos.VolunteermatchPojo;
import com.apon.database.mydao.StudentMyDao;
import com.apon.service.valueobject.TaskValueObject;
import com.apon.service.valueobject.VolunteerInstanceValueObject;
import com.apon.service.valueobject.VolunteerMatchValueObject;
import com.apon.service.valueobject.VolunteerValueObject;
import com.apon.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VolunteerMapper {
    private VolunteerValueObject volunteerValueObject;
    private VolunteerPojo volunteerPojo;

    public VolunteerMapper() {
        this.volunteerValueObject = new VolunteerValueObject();
        this.volunteerPojo = new VolunteerPojo();
    }

    public VolunteerValueObject getVolunteerValueObject() {
        return volunteerValueObject;
    }

    public VolunteerPojo getVolunteerPojo() {
        return volunteerPojo;
    }

    public void setVolunteerValueObject(VolunteerValueObject volunteerValueObject) {
        this.volunteerValueObject = volunteerValueObject;

    }

    public void setVolunteerPojo(VolunteerPojo volunteerPojo) {
        this.volunteerPojo = volunteerPojo;
        fillValueObjectWithPojo(volunteerPojo);
    }

    public void fillValueObjectWithPojo(VolunteerPojo volunteerPojo) {
        volunteerValueObject.setExternalIdentifier(volunteerPojo.getExternalidentifier());
        volunteerValueObject.setFirstName(volunteerPojo.getFirstname());
        volunteerValueObject.setInsertion(volunteerPojo.getInsertion());
        volunteerValueObject.setLastName(volunteerPojo.getLastname());
        volunteerValueObject.setDateOfBirth(volunteerPojo.getDateofbirth());
        volunteerValueObject.setGender(volunteerPojo.getGender());
        volunteerValueObject.setPhoneNumber(volunteerPojo.getPhonenumber());
        volunteerValueObject.setMobilePhoneNumber(volunteerPojo.getMobilephonenumber());
        volunteerValueObject.setEmail(volunteerPojo.getEmail());
        volunteerValueObject.setDateTraining(volunteerPojo.getDatetraining());
        volunteerValueObject.setPostalCode(volunteerPojo.getPostalcode());
        volunteerValueObject.setStreetName(volunteerPojo.getStreetname());
        volunteerValueObject.setHouseNr(volunteerPojo.getHousenr());
        volunteerValueObject.setLog(volunteerPojo.getLog());
        volunteerValueObject.setJob(volunteerPojo.getJob());
        volunteerValueObject.setIsClassAssistant(volunteerPojo.getIsclassassistant());
        volunteerValueObject.setIsTaalmaatje(volunteerPojo.getIstaalmaatje());
    }

    public void fillPojoWithValueObject(VolunteerValueObject volunteerValueObject) {
        volunteerPojo.setExternalidentifier(volunteerValueObject.getExternalIdentifier());
        volunteerPojo.setFirstname(volunteerValueObject.getFirstName());
        volunteerPojo.setInsertion(volunteerValueObject.getInsertion());
        volunteerPojo.setLastname(volunteerValueObject.getLastName());
        volunteerPojo.setDateofbirth(volunteerValueObject.getDateOfBirth());
        volunteerPojo.setGender(volunteerValueObject.getGender());
        volunteerPojo.setPhonenumber(volunteerValueObject.getPhoneNumber());
        volunteerPojo.setMobilephonenumber(volunteerValueObject.getMobilePhoneNumber());
        volunteerPojo.setEmail(volunteerValueObject.getEmail());
        volunteerPojo.setDatetraining(volunteerValueObject.getDateTraining());
        volunteerPojo.setPostalcode(volunteerValueObject.getPostalCode());
        volunteerPojo.setCity(volunteerValueObject.getCity());
        volunteerPojo.setStreetname(volunteerValueObject.getStreetName());
        volunteerPojo.setHousenr(volunteerValueObject.getHouseNr());
        volunteerPojo.setLog(volunteerValueObject.getLog());
        volunteerPojo.setJob(volunteerValueObject.getJob());
        volunteerPojo.setIsclassassistant(volunteerValueObject.getIsClassAssistant());
        volunteerPojo.setIstaalmaatje(volunteerValueObject.getIsTaalmaatje());
    }

    private void addInstance(VolunteerinstancePojo volunteerinstancePojo) {
        VolunteerInstanceValueObject volunteerInstanceValueObject = new VolunteerInstanceValueObject();
        volunteerInstanceValueObject.setVolunteerExtId(volunteerValueObject.getExternalIdentifier());
        volunteerInstanceValueObject.setExternalIdentifier(volunteerinstancePojo.getExternalidentifier());
        volunteerInstanceValueObject.setDateStart(volunteerinstancePojo.getDatestart());
        volunteerInstanceValueObject.setDateEnd(volunteerinstancePojo.getDateend());

        volunteerValueObject.getVolunteerInstanceValueObjects().add(volunteerInstanceValueObject);
    }

    public void setInstanceList(List<VolunteerinstancePojo> listVolunteerInstancePojo) {
        List<VolunteerInstanceValueObject> listVolunteerInstanceValueObject = new ArrayList();
        volunteerValueObject.setVolunteerInstanceValueObjects(listVolunteerInstanceValueObject);

        for (VolunteerinstancePojo volunteerinstancePojo : listVolunteerInstancePojo) {
            addInstance(volunteerinstancePojo);
        }
    }

    private void addMatch(VolunteermatchPojo volunteermatchPojo, StudentMyDao studentMyDao) {
        VolunteerMatchValueObject volunteerMatchValueObject = new VolunteerMatchValueObject();
        volunteerMatchValueObject.setExternalIdentifier(volunteermatchPojo.getExternalidentifier());
        volunteerMatchValueObject.setDateStart(volunteermatchPojo.getDatestart());
        volunteerMatchValueObject.setDateEnd(volunteermatchPojo.getDateend());

        // If current date lies between dateStart and dateEnd, we have an active match. So count it.
        if (DateTimeUtil.isBetween(DateTimeUtil.getCurrentDate(), volunteermatchPojo.getDatestart(), volunteermatchPojo.getDateend())) {
            volunteerValueObject.setNrOfMatchesToday(volunteerValueObject.getNrOfMatchesToday() + 1);
        }

        // Set the student.
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.setStudentPojo(studentMyDao.fetchOneByStudentid(volunteermatchPojo.getStudentid()));
        volunteerMatchValueObject.setStudentExtId(studentMapper.getStudentValueObject().getExternalIdentifier());

        volunteerValueObject.getVolunteerMatchValueObjects().add(volunteerMatchValueObject);
    }

    public void setMatchList(List<VolunteermatchPojo> listVolunteerMatchPojo, StudentMyDao studentMyDao) {
        List<VolunteerMatchValueObject> listVolunteerMatchValueObject = new ArrayList();
        volunteerValueObject.setVolunteerMatchValueObjects(listVolunteerMatchValueObject);

        for (VolunteermatchPojo volunteermatchPojo : listVolunteerMatchPojo) {
            addMatch(volunteermatchPojo, studentMyDao);
        }
    }

    private void addTask(TaskPojo taskPojo) {
        TaskMapper taskMapper = new TaskMapper();
        taskMapper.setTaskPojo(taskPojo);
        volunteerValueObject.getTaskValueObjects().add(taskMapper.getTaskValueObject());
    }

    public void setTaskList(List<TaskPojo> listTaskPojo) {
        List<TaskValueObject> taskValueObjects = new ArrayList();
        volunteerValueObject.setTaskValueObjects(taskValueObjects);
        for (TaskPojo taskPojo : listTaskPojo) {
            addTask(taskPojo);
        }
    }
}
