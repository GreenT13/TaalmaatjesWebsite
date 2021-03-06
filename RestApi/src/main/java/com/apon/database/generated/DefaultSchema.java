/*
 * This file is generated by jOOQ.
*/
package com.apon.database.generated;


import com.apon.database.generated.tables.Logonuser;
import com.apon.database.generated.tables.Scriptlog;
import com.apon.database.generated.tables.Student;
import com.apon.database.generated.tables.Task;
import com.apon.database.generated.tables.Volunteer;
import com.apon.database.generated.tables.Volunteerinstance;
import com.apon.database.generated.tables.Volunteermatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 816689484;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>logonuser</code>.
     */
    public final Logonuser LOGONUSER = com.apon.database.generated.tables.Logonuser.LOGONUSER;

    /**
     * The table <code>scriptlog</code>.
     */
    public final Scriptlog SCRIPTLOG = com.apon.database.generated.tables.Scriptlog.SCRIPTLOG;

    /**
     * The table <code>student</code>.
     */
    public final Student STUDENT = com.apon.database.generated.tables.Student.STUDENT;

    /**
     * The table <code>task</code>.
     */
    public final Task TASK = com.apon.database.generated.tables.Task.TASK;

    /**
     * The table <code>volunteer</code>.
     */
    public final Volunteer VOLUNTEER = com.apon.database.generated.tables.Volunteer.VOLUNTEER;

    /**
     * The table <code>volunteerinstance</code>.
     */
    public final Volunteerinstance VOLUNTEERINSTANCE = com.apon.database.generated.tables.Volunteerinstance.VOLUNTEERINSTANCE;

    /**
     * The table <code>volunteermatch</code>.
     */
    public final Volunteermatch VOLUNTEERMATCH = com.apon.database.generated.tables.Volunteermatch.VOLUNTEERMATCH;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Logonuser.LOGONUSER,
            Scriptlog.SCRIPTLOG,
            Student.STUDENT,
            Task.TASK,
            Volunteer.VOLUNTEER,
            Volunteerinstance.VOLUNTEERINSTANCE,
            Volunteermatch.VOLUNTEERMATCH);
    }
}
