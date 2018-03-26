package com.apon.service.valueobject;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class StudentValueObject {
    private String externalIdentifier;
    private String firstName;
    private String insertion;
    private String lastName;
    private String gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private String groupIdentification;
    private Boolean hasQuit;

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

    public String getGroupIdentification() {
        return groupIdentification;
    }

    public void setGroupIdentification(String groupIdentification) {
        this.groupIdentification = groupIdentification;
    }

    public Boolean getHasQuit() {
        return hasQuit;
    }

    public void setHasQuit(Boolean hasQuit) {
        this.hasQuit = hasQuit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
