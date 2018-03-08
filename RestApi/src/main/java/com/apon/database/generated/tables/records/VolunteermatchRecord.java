/*
 * This file is generated by jOOQ.
*/
package com.apon.database.generated.tables.records;


import com.apon.database.generated.tables.Volunteermatch;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class VolunteermatchRecord extends UpdatableRecordImpl<VolunteermatchRecord> implements Record6<Integer, Integer, String, Integer, Date, Date> {

    private static final long serialVersionUID = 652528684;

    /**
     * Setter for <code>taalmaatjes.volunteermatch.volunteerId</code>.
     */
    public void setVolunteerid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.volunteerId</code>.
     */
    public Integer getVolunteerid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>taalmaatjes.volunteermatch.volunteerMatchId</code>.
     */
    public void setVolunteermatchid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.volunteerMatchId</code>.
     */
    public Integer getVolunteermatchid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>taalmaatjes.volunteermatch.externalIdentifier</code>.
     */
    public void setExternalidentifier(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.externalIdentifier</code>.
     */
    public String getExternalidentifier() {
        return (String) get(2);
    }

    /**
     * Setter for <code>taalmaatjes.volunteermatch.studentId</code>.
     */
    public void setStudentid(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.studentId</code>.
     */
    public Integer getStudentid() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>taalmaatjes.volunteermatch.dateStart</code>.
     */
    public void setDatestart(Date value) {
        set(4, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.dateStart</code>.
     */
    public Date getDatestart() {
        return (Date) get(4);
    }

    /**
     * Setter for <code>taalmaatjes.volunteermatch.dateEnd</code>.
     */
    public void setDateend(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>taalmaatjes.volunteermatch.dateEnd</code>.
     */
    public Date getDateend() {
        return (Date) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Date, Date> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Date, Date> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Volunteermatch.VOLUNTEERMATCH.STUDENTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field5() {
        return Volunteermatch.VOLUNTEERMATCH.DATESTART;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return Volunteermatch.VOLUNTEERMATCH.DATEEND;
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
    public Integer component2() {
        return getVolunteermatchid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getExternalidentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getStudentid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component5() {
        return getDatestart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component6() {
        return getDateend();
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
    public Integer value2() {
        return getVolunteermatchid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getExternalidentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getStudentid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value5() {
        return getDatestart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value6() {
        return getDateend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value1(Integer value) {
        setVolunteerid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value2(Integer value) {
        setVolunteermatchid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value3(String value) {
        setExternalidentifier(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value4(Integer value) {
        setStudentid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value5(Date value) {
        setDatestart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord value6(Date value) {
        setDateend(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteermatchRecord values(Integer value1, Integer value2, String value3, Integer value4, Date value5, Date value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VolunteermatchRecord
     */
    public VolunteermatchRecord() {
        super(Volunteermatch.VOLUNTEERMATCH);
    }

    /**
     * Create a detached, initialised VolunteermatchRecord
     */
    public VolunteermatchRecord(Integer volunteerid, Integer volunteermatchid, String externalidentifier, Integer studentid, Date datestart, Date dateend) {
        super(Volunteermatch.VOLUNTEERMATCH);

        set(0, volunteerid);
        set(1, volunteermatchid);
        set(2, externalidentifier);
        set(3, studentid);
        set(4, datestart);
        set(5, dateend);
    }
}
