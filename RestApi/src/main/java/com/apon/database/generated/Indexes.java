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

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling indexes of tables of the <code>taalmaatjes</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index LOGONUSER_LOUS_USNA_UC = Indexes0.LOGONUSER_LOUS_USNA_UC;
    public static final Index LOGONUSER_PRIMARY = Indexes0.LOGONUSER_PRIMARY;
    public static final Index SCRIPTLOG_PRIMARY = Indexes0.SCRIPTLOG_PRIMARY;
    public static final Index STUDENT_PRIMARY = Indexes0.STUDENT_PRIMARY;
    public static final Index STUDENT_STUD_EXTID = Indexes0.STUDENT_STUD_EXTID;
    public static final Index TASK_PRIMARY = Indexes0.TASK_PRIMARY;
    public static final Index TASK_TASK_EXTID = Indexes0.TASK_TASK_EXTID;
    public static final Index TASK_TASK_VOLU_FK = Indexes0.TASK_TASK_VOLU_FK;
    public static final Index VOLUNTEER_PRIMARY = Indexes0.VOLUNTEER_PRIMARY;
    public static final Index VOLUNTEER_VOLU_EXTID = Indexes0.VOLUNTEER_VOLU_EXTID;
    public static final Index VOLUNTEERINSTANCE_PRIMARY = Indexes0.VOLUNTEERINSTANCE_PRIMARY;
    public static final Index VOLUNTEERINSTANCE_VOIN_EXTID = Indexes0.VOLUNTEERINSTANCE_VOIN_EXTID;
    public static final Index VOLUNTEERMATCH_PRIMARY = Indexes0.VOLUNTEERMATCH_PRIMARY;
    public static final Index VOLUNTEERMATCH_VOMA_EXTID = Indexes0.VOLUNTEERMATCH_VOMA_EXTID;
    public static final Index VOLUNTEERMATCH_VOMA_STUD_FK = Indexes0.VOLUNTEERMATCH_VOMA_STUD_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 extends AbstractKeys {
        public static Index LOGONUSER_LOUS_USNA_UC = createIndex("LoUs_UsNa_UC", Logonuser.LOGONUSER, new OrderField[] { Logonuser.LOGONUSER.USERNAME }, true);
        public static Index LOGONUSER_PRIMARY = createIndex("PRIMARY", Logonuser.LOGONUSER, new OrderField[] { Logonuser.LOGONUSER.LOGONUSERID }, true);
        public static Index SCRIPTLOG_PRIMARY = createIndex("PRIMARY", Scriptlog.SCRIPTLOG, new OrderField[] { Scriptlog.SCRIPTLOG.SCRIPTNAME }, true);
        public static Index STUDENT_PRIMARY = createIndex("PRIMARY", Student.STUDENT, new OrderField[] { Student.STUDENT.STUDENTID }, true);
        public static Index STUDENT_STUD_EXTID = createIndex("Stud_ExtId", Student.STUDENT, new OrderField[] { Student.STUDENT.EXTERNALIDENTIFIER }, true);
        public static Index TASK_PRIMARY = createIndex("PRIMARY", Task.TASK, new OrderField[] { Task.TASK.TASKID }, true);
        public static Index TASK_TASK_EXTID = createIndex("Task_ExtId", Task.TASK, new OrderField[] { Task.TASK.EXTERNALIDENTIFIER }, true);
        public static Index TASK_TASK_VOLU_FK = createIndex("Task_Volu_FK", Task.TASK, new OrderField[] { Task.TASK.VOLUNTEERID }, false);
        public static Index VOLUNTEER_PRIMARY = createIndex("PRIMARY", Volunteer.VOLUNTEER, new OrderField[] { Volunteer.VOLUNTEER.VOLUNTEERID }, true);
        public static Index VOLUNTEER_VOLU_EXTID = createIndex("Volu_ExtId", Volunteer.VOLUNTEER, new OrderField[] { Volunteer.VOLUNTEER.EXTERNALIDENTIFIER }, true);
        public static Index VOLUNTEERINSTANCE_PRIMARY = createIndex("PRIMARY", Volunteerinstance.VOLUNTEERINSTANCE, new OrderField[] { Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID, Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID }, true);
        public static Index VOLUNTEERINSTANCE_VOIN_EXTID = createIndex("VoIn_ExtId", Volunteerinstance.VOLUNTEERINSTANCE, new OrderField[] { Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID, Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER }, true);
        public static Index VOLUNTEERMATCH_PRIMARY = createIndex("PRIMARY", Volunteermatch.VOLUNTEERMATCH, new OrderField[] { Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID, Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID }, true);
        public static Index VOLUNTEERMATCH_VOMA_EXTID = createIndex("VoMa_ExtId", Volunteermatch.VOLUNTEERMATCH, new OrderField[] { Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID, Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER }, true);
        public static Index VOLUNTEERMATCH_VOMA_STUD_FK = createIndex("VoMa_Stud_FK", Volunteermatch.VOLUNTEERMATCH, new OrderField[] { Volunteermatch.VOLUNTEERMATCH.STUDENTID }, false);
    }
}
