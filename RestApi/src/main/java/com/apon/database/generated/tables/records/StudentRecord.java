/*
 * This file is generated by jOOQ.
*/
package com.apon.database.generated.tables.records;


import com.apon.database.generated.tables.Student;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class StudentRecord extends UpdatableRecordImpl<StudentRecord> implements Record9<Integer, String, String, String, String, String, Date, String, Boolean> {

    private static final long serialVersionUID = -1926503547;

    /**
     * Setter for <code>taalmaatjes.student.studentId</code>.
     */
    public void setStudentid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.studentId</code>.
     */
    public Integer getStudentid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>taalmaatjes.student.externalIdentifier</code>.
     */
    public void setExternalidentifier(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.externalIdentifier</code>.
     */
    public String getExternalidentifier() {
        return (String) get(1);
    }

    /**
     * Setter for <code>taalmaatjes.student.firstName</code>.
     */
    public void setFirstname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.firstName</code>.
     */
    public String getFirstname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>taalmaatjes.student.insertion</code>.
     */
    public void setInsertion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.insertion</code>.
     */
    public String getInsertion() {
        return (String) get(3);
    }

    /**
     * Setter for <code>taalmaatjes.student.lastName</code>.
     */
    public void setLastname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.lastName</code>.
     */
    public String getLastname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>taalmaatjes.student.gender</code>.
     */
    public void setGender(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.gender</code>.
     */
    public String getGender() {
        return (String) get(5);
    }

    /**
     * Setter for <code>taalmaatjes.student.dateOfBirth</code>.
     */
    public void setDateofbirth(Date value) {
        set(6, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.dateOfBirth</code>.
     */
    public Date getDateofbirth() {
        return (Date) get(6);
    }

    /**
     * Setter for <code>taalmaatjes.student.groupIdentification</code>.
     */
    public void setGroupidentification(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.groupIdentification</code>.
     */
    public String getGroupidentification() {
        return (String) get(7);
    }

    /**
     * Setter for <code>taalmaatjes.student.hasQuit</code>.
     */
    public void setHasquit(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>taalmaatjes.student.hasQuit</code>.
     */
    public Boolean getHasquit() {
        return (Boolean) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, String, String, Date, String, Boolean> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, String, String, Date, String, Boolean> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Student.STUDENT.STUDENTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Student.STUDENT.EXTERNALIDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Student.STUDENT.FIRSTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Student.STUDENT.INSERTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Student.STUDENT.LASTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Student.STUDENT.GENDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field7() {
        return Student.STUDENT.DATEOFBIRTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Student.STUDENT.GROUPIDENTIFICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field9() {
        return Student.STUDENT.HASQUIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getStudentid();
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
    public String component6() {
        return getGender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component7() {
        return getDateofbirth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getGroupidentification();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component9() {
        return getHasquit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getStudentid();
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
    public String value6() {
        return getGender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value7() {
        return getDateofbirth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getGroupidentification();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value9() {
        return getHasquit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value1(Integer value) {
        setStudentid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value2(String value) {
        setExternalidentifier(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value3(String value) {
        setFirstname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value4(String value) {
        setInsertion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value5(String value) {
        setLastname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value6(String value) {
        setGender(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value7(Date value) {
        setDateofbirth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value8(String value) {
        setGroupidentification(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value9(Boolean value) {
        setHasquit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, Date value7, String value8, Boolean value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StudentRecord
     */
    public StudentRecord() {
        super(Student.STUDENT);
    }

    /**
     * Create a detached, initialised StudentRecord
     */
    public StudentRecord(Integer studentid, String externalidentifier, String firstname, String insertion, String lastname, String gender, Date dateofbirth, String groupidentification, Boolean hasquit) {
        super(Student.STUDENT);

        set(0, studentid);
        set(1, externalidentifier);
        set(2, firstname);
        set(3, insertion);
        set(4, lastname);
        set(5, gender);
        set(6, dateofbirth);
        set(7, groupidentification);
        set(8, hasquit);
    }
}
