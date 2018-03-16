package com.apon.service.valueobject;

import java.sql.Date;
import java.util.List;

public class VolunteerValueObject {
    // Variables extracted from the database.
    private String externalIdentifier;
    private String firstName;
    private String insertion;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String email;
    private Date dateTraining;
    private String postalCode;
    private String city;
    private String streetName;
    private String houseNr;
    private String log;
    private String job;
    private Boolean isClassAssistant;
    private Boolean isTaalmaatje;

    // Variables determined by logic.
    private Integer nrOfMatchesToday = 0;

    // Lists of instances, matches and tasks.
    private List<VolunteerInstanceValueObject> volunteerInstanceValueObjects;
    private List<VolunteerMatchValueObject> volunteerMatchValueObjects;
    private List<TaskValueObject> taskValueObjects;

    public VolunteerValueObject() { }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateTraining() {
        return dateTraining;
    }

    public void setDateTraining(Date dateTraining) {
        this.dateTraining = dateTraining;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Boolean getIsClassAssistant() {
        return isClassAssistant;
    }

    public void setIsClassAssistant(Boolean classAssistant) {
        isClassAssistant = classAssistant;
    }

    public Boolean getIsTaalmaatje() {
        return isTaalmaatje;
    }

    public void setIsTaalmaatje(Boolean taalmaatje) {
        isTaalmaatje = taalmaatje;
    }

    public Integer getNrOfMatchesToday() {
        return nrOfMatchesToday;
    }

    public void setNrOfMatchesToday(Integer nrOfMatchesToday) {
        this.nrOfMatchesToday = nrOfMatchesToday;
    }

    public List<VolunteerInstanceValueObject> getVolunteerInstanceValueObjects() {
        return volunteerInstanceValueObjects;
    }

    public void setVolunteerInstanceValueObjects(List<VolunteerInstanceValueObject> volunteerInstanceValueObjects) {
        this.volunteerInstanceValueObjects = volunteerInstanceValueObjects;
    }

    public List<VolunteerMatchValueObject> getVolunteerMatchValueObjects() {
        return volunteerMatchValueObjects;
    }

    public void setVolunteerMatchValueObjects(List<VolunteerMatchValueObject> volunteerMatchValueObjects) {
        this.volunteerMatchValueObjects = volunteerMatchValueObjects;
    }

    public List<TaskValueObject> getTaskValueObjects() {
        return taskValueObjects;
    }

    public void setTaskValueObjects(List<TaskValueObject> taskValueObjects) {
        this.taskValueObjects = taskValueObjects;
    }
}
