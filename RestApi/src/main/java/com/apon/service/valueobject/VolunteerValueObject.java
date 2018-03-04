package com.apon.service.valueobject;

import java.sql.Date;

public class VolunteerValueObject {
    // Variables extracted from the database.
    private String externalIdentifier;
    private String firstName;
    private String insertion;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Boolean getClassAssistant() {
        return isClassAssistant;
    }

    public void setClassAssistant(Boolean classAssistant) {
        isClassAssistant = classAssistant;
    }

    public Boolean getTaalmaatje() {
        return isTaalmaatje;
    }

    public void setTaalmaatje(Boolean taalmaatje) {
        isTaalmaatje = taalmaatje;
    }

    public Integer getNrOfMatchesToday() {
        return nrOfMatchesToday;
    }

    public void setNrOfMatchesToday(Integer nrOfMatchesToday) {
        this.nrOfMatchesToday = nrOfMatchesToday;
    }
}
