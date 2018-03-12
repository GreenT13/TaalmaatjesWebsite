/*
 * This file is generated by jOOQ.
*/
package com.apon.database.generated.tables;


import com.apon.database.generated.Indexes;
import com.apon.database.generated.Keys;
import com.apon.database.generated.Taalmaatjes;
import com.apon.database.generated.tables.records.VolunteerRecord;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Volunteer extends TableImpl<VolunteerRecord> {

    private static final long serialVersionUID = -1652715974;

    /**
     * The reference instance of <code>taalmaatjes.volunteer</code>
     */
    public static final Volunteer VOLUNTEER = new Volunteer();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VolunteerRecord> getRecordType() {
        return VolunteerRecord.class;
    }

    /**
     * The column <code>taalmaatjes.volunteer.volunteerId</code>.
     */
    public final TableField<VolunteerRecord, Integer> VOLUNTEERID = createField("volunteerId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.externalIdentifier</code>.
     */
    public final TableField<VolunteerRecord, String> EXTERNALIDENTIFIER = createField("externalIdentifier", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.firstName</code>.
     */
    public final TableField<VolunteerRecord, String> FIRSTNAME = createField("firstName", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.insertion</code>.
     */
    public final TableField<VolunteerRecord, String> INSERTION = createField("insertion", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.lastName</code>.
     */
    public final TableField<VolunteerRecord, String> LASTNAME = createField("lastName", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.dateOfBirth</code>.
     */
    public final TableField<VolunteerRecord, Date> DATEOFBIRTH = createField("dateOfBirth", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.gender</code>.
     */
    public final TableField<VolunteerRecord, String> GENDER = createField("gender", org.jooq.impl.SQLDataType.VARCHAR(1).nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.phoneNumber</code>.
     */
    public final TableField<VolunteerRecord, String> PHONENUMBER = createField("phoneNumber", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.mobilePhoneNumber</code>.
     */
    public final TableField<VolunteerRecord, String> MOBILEPHONENUMBER = createField("mobilePhoneNumber", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.email</code>.
     */
    public final TableField<VolunteerRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.postalCode</code>.
     */
    public final TableField<VolunteerRecord, String> POSTALCODE = createField("postalCode", org.jooq.impl.SQLDataType.VARCHAR(6), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.city</code>.
     */
    public final TableField<VolunteerRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.streetName</code>.
     */
    public final TableField<VolunteerRecord, String> STREETNAME = createField("streetName", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.houseNr</code>.
     */
    public final TableField<VolunteerRecord, String> HOUSENR = createField("houseNr", org.jooq.impl.SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.log</code>.
     */
    public final TableField<VolunteerRecord, String> LOG = createField("log", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>taalmaatjes.volunteer.job</code>.
     */
    public final TableField<VolunteerRecord, String> JOB = createField("job", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.dateTraining</code>.
     */
    public final TableField<VolunteerRecord, Date> DATETRAINING = createField("dateTraining", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>taalmaatjes.volunteer.isClassAssistant</code>.
     */
    public final TableField<VolunteerRecord, Boolean> ISCLASSASSISTANT = createField("isClassAssistant", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>taalmaatjes.volunteer.isTaalmaatje</code>.
     */
    public final TableField<VolunteerRecord, Boolean> ISTAALMAATJE = createField("isTaalmaatje", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>taalmaatjes.volunteer</code> table reference
     */
    public Volunteer() {
        this(DSL.name("volunteer"), null);
    }

    /**
     * Create an aliased <code>taalmaatjes.volunteer</code> table reference
     */
    public Volunteer(String alias) {
        this(DSL.name(alias), VOLUNTEER);
    }

    /**
     * Create an aliased <code>taalmaatjes.volunteer</code> table reference
     */
    public Volunteer(Name alias) {
        this(alias, VOLUNTEER);
    }

    private Volunteer(Name alias, Table<VolunteerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Volunteer(Name alias, Table<VolunteerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Taalmaatjes.TAALMAATJES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.VOLUNTEER_PRIMARY, Indexes.VOLUNTEER_VOLU_EXTID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VolunteerRecord> getPrimaryKey() {
        return Keys.KEY_VOLUNTEER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VolunteerRecord>> getKeys() {
        return Arrays.<UniqueKey<VolunteerRecord>>asList(Keys.KEY_VOLUNTEER_PRIMARY, Keys.KEY_VOLUNTEER_VOLU_EXTID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Volunteer as(String alias) {
        return new Volunteer(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Volunteer as(Name alias) {
        return new Volunteer(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Volunteer rename(String name) {
        return new Volunteer(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Volunteer rename(Name name) {
        return new Volunteer(name, null);
    }
}
