/*
 * This file is generated by jOOQ.
*/
package com.apon.database.generated.tables.records;


import com.apon.database.generated.tables.Volunteer;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VolunteerRecord extends UpdatableRecordImpl<VolunteerRecord> implements Record19<Integer, String, String, String, String, Date, String, String, String, String, String, String, String, String, String, String, Date, Boolean, Boolean> {

    private static final long serialVersionUID = -1943351979;

    /**
     * Setter for <code>volunteer.volunteerId</code>.
     */
    public void setVolunteerid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>volunteer.volunteerId</code>.
     */
    public Integer getVolunteerid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>volunteer.externalIdentifier</code>.
     */
    public void setExternalidentifier(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>volunteer.externalIdentifier</code>.
     */
    public String getExternalidentifier() {
        return (String) get(1);
    }

    /**
     * Setter for <code>volunteer.firstName</code>.
     */
    public void setFirstname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>volunteer.firstName</code>.
     */
    public String getFirstname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>volunteer.insertion</code>.
     */
    public void setInsertion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>volunteer.insertion</code>.
     */
    public String getInsertion() {
        return (String) get(3);
    }

    /**
     * Setter for <code>volunteer.lastName</code>.
     */
    public void setLastname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>volunteer.lastName</code>.
     */
    public String getLastname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>volunteer.dateOfBirth</code>.
     */
    public void setDateofbirth(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>volunteer.dateOfBirth</code>.
     */
    public Date getDateofbirth() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>volunteer.gender</code>.
     */
    public void setGender(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>volunteer.gender</code>.
     */
    public String getGender() {
        return (String) get(6);
    }

    /**
     * Setter for <code>volunteer.phoneNumber</code>.
     */
    public void setPhonenumber(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>volunteer.phoneNumber</code>.
     */
    public String getPhonenumber() {
        return (String) get(7);
    }

    /**
     * Setter for <code>volunteer.mobilePhoneNumber</code>.
     */
    public void setMobilephonenumber(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>volunteer.mobilePhoneNumber</code>.
     */
    public String getMobilephonenumber() {
        return (String) get(8);
    }

    /**
     * Setter for <code>volunteer.email</code>.
     */
    public void setEmail(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>volunteer.email</code>.
     */
    public String getEmail() {
        return (String) get(9);
    }

    /**
     * Setter for <code>volunteer.postalCode</code>.
     */
    public void setPostalcode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>volunteer.postalCode</code>.
     */
    public String getPostalcode() {
        return (String) get(10);
    }

    /**
     * Setter for <code>volunteer.city</code>.
     */
    public void setCity(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>volunteer.city</code>.
     */
    public String getCity() {
        return (String) get(11);
    }

    /**
     * Setter for <code>volunteer.streetName</code>.
     */
    public void setStreetname(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>volunteer.streetName</code>.
     */
    public String getStreetname() {
        return (String) get(12);
    }

    /**
     * Setter for <code>volunteer.houseNr</code>.
     */
    public void setHousenr(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>volunteer.houseNr</code>.
     */
    public String getHousenr() {
        return (String) get(13);
    }

    /**
     * Setter for <code>volunteer.log</code>.
     */
    public void setLog(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>volunteer.log</code>.
     */
    public String getLog() {
        return (String) get(14);
    }

    /**
     * Setter for <code>volunteer.job</code>.
     */
    public void setJob(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>volunteer.job</code>.
     */
    public String getJob() {
        return (String) get(15);
    }

    /**
     * Setter for <code>volunteer.dateTraining</code>.
     */
    public void setDatetraining(Date value) {
        set(16, value);
    }

    /**
     * Getter for <code>volunteer.dateTraining</code>.
     */
    public Date getDatetraining() {
        return (Date) get(16);
    }

    /**
     * Setter for <code>volunteer.isClassAssistant</code>.
     */
    public void setIsclassassistant(Boolean value) {
        set(17, value);
    }

    /**
     * Getter for <code>volunteer.isClassAssistant</code>.
     */
    public Boolean getIsclassassistant() {
        return (Boolean) get(17);
    }

    /**
     * Setter for <code>volunteer.isTaalmaatje</code>.
     */
    public void setIstaalmaatje(Boolean value) {
        set(18, value);
    }

    /**
     * Getter for <code>volunteer.isTaalmaatje</code>.
     */
    public Boolean getIstaalmaatje() {
        return (Boolean) get(18);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, String, String, String, Date, String, String, String, String, String, String, String, String, String, String, Date, Boolean, Boolean> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, String, String, String, Date, String, String, String, String, String, String, String, String, String, String, Date, Boolean, Boolean> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Volunteer.VOLUNTEER.VOLUNTEERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Volunteer.VOLUNTEER.EXTERNALIDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Volunteer.VOLUNTEER.FIRSTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Volunteer.VOLUNTEER.INSERTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Volunteer.VOLUNTEER.LASTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return Volunteer.VOLUNTEER.DATEOFBIRTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Volunteer.VOLUNTEER.GENDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Volunteer.VOLUNTEER.PHONENUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return Volunteer.VOLUNTEER.MOBILEPHONENUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return Volunteer.VOLUNTEER.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return Volunteer.VOLUNTEER.POSTALCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return Volunteer.VOLUNTEER.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return Volunteer.VOLUNTEER.STREETNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return Volunteer.VOLUNTEER.HOUSENR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return Volunteer.VOLUNTEER.LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return Volunteer.VOLUNTEER.JOB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field17() {
        return Volunteer.VOLUNTEER.DATETRAINING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field18() {
        return Volunteer.VOLUNTEER.ISCLASSASSISTANT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field19() {
        return Volunteer.VOLUNTEER.ISTAALMAATJE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getVolunteerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getExternalidentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getFirstname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getInsertion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getLastname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component6() {
        return getDateofbirth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getGender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getPhonenumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getMobilephonenumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getPostalcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getStreetname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getHousenr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getLog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getJob();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component17() {
        return getDatetraining();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component18() {
        return getIsclassassistant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component19() {
        return getIstaalmaatje();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getVolunteerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getExternalidentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFirstname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getInsertion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getLastname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value6() {
        return getDateofbirth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getGender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getPhonenumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getMobilephonenumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getPostalcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getStreetname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getHousenr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getLog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getJob();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value17() {
        return getDatetraining();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value18() {
        return getIsclassassistant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value19() {
        return getIstaalmaatje();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value1(Integer value) {
        setVolunteerid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value2(String value) {
        setExternalidentifier(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value3(String value) {
        setFirstname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value4(String value) {
        setInsertion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value5(String value) {
        setLastname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value6(Date value) {
        setDateofbirth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value7(String value) {
        setGender(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value8(String value) {
        setPhonenumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value9(String value) {
        setMobilephonenumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value10(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value11(String value) {
        setPostalcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value12(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value13(String value) {
        setStreetname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value14(String value) {
        setHousenr(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value15(String value) {
        setLog(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value16(String value) {
        setJob(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value17(Date value) {
        setDatetraining(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value18(Boolean value) {
        setIsclassassistant(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord value19(Boolean value) {
        setIstaalmaatje(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteerRecord values(Integer value1, String value2, String value3, String value4, String value5, Date value6, String value7, String value8, String value9, String value10, String value11, String value12, String value13, String value14, String value15, String value16, Date value17, Boolean value18, Boolean value19) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VolunteerRecord
     */
    public VolunteerRecord() {
        super(Volunteer.VOLUNTEER);
    }

    /**
     * Create a detached, initialised VolunteerRecord
     */
    public VolunteerRecord(Integer volunteerid, String externalidentifier, String firstname, String insertion, String lastname, Date dateofbirth, String gender, String phonenumber, String mobilephonenumber, String email, String postalcode, String city, String streetname, String housenr, String log, String job, Date datetraining, Boolean isclassassistant, Boolean istaalmaatje) {
        super(Volunteer.VOLUNTEER);

        set(0, volunteerid);
        set(1, externalidentifier);
        set(2, firstname);
        set(3, insertion);
        set(4, lastname);
        set(5, dateofbirth);
        set(6, gender);
        set(7, phonenumber);
        set(8, mobilephonenumber);
        set(9, email);
        set(10, postalcode);
        set(11, city);
        set(12, streetname);
        set(13, housenr);
        set(14, log);
        set(15, job);
        set(16, datetraining);
        set(17, isclassassistant);
        set(18, istaalmaatje);
    }
}
