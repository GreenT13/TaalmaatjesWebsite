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
import com.apon.database.generated.tables.records.LogonuserRecord;
import com.apon.database.generated.tables.records.ScriptlogRecord;
import com.apon.database.generated.tables.records.StudentRecord;
import com.apon.database.generated.tables.records.TaskRecord;
import com.apon.database.generated.tables.records.VolunteerRecord;
import com.apon.database.generated.tables.records.VolunteerinstanceRecord;
import com.apon.database.generated.tables.records.VolunteermatchRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>taalmaatjes</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<LogonuserRecord> KEY_LOGONUSER_PRIMARY = UniqueKeys0.KEY_LOGONUSER_PRIMARY;
    public static final UniqueKey<LogonuserRecord> KEY_LOGONUSER_LOUS_USNA_UC = UniqueKeys0.KEY_LOGONUSER_LOUS_USNA_UC;
    public static final UniqueKey<ScriptlogRecord> KEY_SCRIPTLOG_PRIMARY = UniqueKeys0.KEY_SCRIPTLOG_PRIMARY;
    public static final UniqueKey<StudentRecord> KEY_STUDENT_PRIMARY = UniqueKeys0.KEY_STUDENT_PRIMARY;
    public static final UniqueKey<StudentRecord> KEY_STUDENT_STUD_EXTID = UniqueKeys0.KEY_STUDENT_STUD_EXTID;
    public static final UniqueKey<TaskRecord> KEY_TASK_PRIMARY = UniqueKeys0.KEY_TASK_PRIMARY;
    public static final UniqueKey<TaskRecord> KEY_TASK_TASK_EXTID = UniqueKeys0.KEY_TASK_TASK_EXTID;
    public static final UniqueKey<VolunteerRecord> KEY_VOLUNTEER_PRIMARY = UniqueKeys0.KEY_VOLUNTEER_PRIMARY;
    public static final UniqueKey<VolunteerRecord> KEY_VOLUNTEER_VOLU_EXTID = UniqueKeys0.KEY_VOLUNTEER_VOLU_EXTID;
    public static final UniqueKey<VolunteerinstanceRecord> KEY_VOLUNTEERINSTANCE_PRIMARY = UniqueKeys0.KEY_VOLUNTEERINSTANCE_PRIMARY;
    public static final UniqueKey<VolunteerinstanceRecord> KEY_VOLUNTEERINSTANCE_VOIN_EXTID = UniqueKeys0.KEY_VOLUNTEERINSTANCE_VOIN_EXTID;
    public static final UniqueKey<VolunteermatchRecord> KEY_VOLUNTEERMATCH_PRIMARY = UniqueKeys0.KEY_VOLUNTEERMATCH_PRIMARY;
    public static final UniqueKey<VolunteermatchRecord> KEY_VOLUNTEERMATCH_VOMA_EXTID = UniqueKeys0.KEY_VOLUNTEERMATCH_VOMA_EXTID;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TaskRecord, VolunteerRecord> TASK_VOLU_FK = ForeignKeys0.TASK_VOLU_FK;
    public static final ForeignKey<VolunteerinstanceRecord, VolunteerRecord> VOIN_VOLU_FK = ForeignKeys0.VOIN_VOLU_FK;
    public static final ForeignKey<VolunteermatchRecord, VolunteerRecord> VOMA_VOLU_FK = ForeignKeys0.VOMA_VOLU_FK;
    public static final ForeignKey<VolunteermatchRecord, StudentRecord> VOMA_STUD_FK = ForeignKeys0.VOMA_STUD_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<LogonuserRecord> KEY_LOGONUSER_PRIMARY = createUniqueKey(Logonuser.LOGONUSER, "KEY_logonuser_PRIMARY", Logonuser.LOGONUSER.LOGONUSERID);
        public static final UniqueKey<LogonuserRecord> KEY_LOGONUSER_LOUS_USNA_UC = createUniqueKey(Logonuser.LOGONUSER, "KEY_logonuser_LoUs_UsNa_UC", Logonuser.LOGONUSER.USERNAME);
        public static final UniqueKey<ScriptlogRecord> KEY_SCRIPTLOG_PRIMARY = createUniqueKey(Scriptlog.SCRIPTLOG, "KEY_scriptlog_PRIMARY", Scriptlog.SCRIPTLOG.SCRIPTNAME);
        public static final UniqueKey<StudentRecord> KEY_STUDENT_PRIMARY = createUniqueKey(Student.STUDENT, "KEY_student_PRIMARY", Student.STUDENT.STUDENTID);
        public static final UniqueKey<StudentRecord> KEY_STUDENT_STUD_EXTID = createUniqueKey(Student.STUDENT, "KEY_student_Stud_ExtId", Student.STUDENT.EXTERNALIDENTIFIER);
        public static final UniqueKey<TaskRecord> KEY_TASK_PRIMARY = createUniqueKey(Task.TASK, "KEY_task_PRIMARY", Task.TASK.TASKID);
        public static final UniqueKey<TaskRecord> KEY_TASK_TASK_EXTID = createUniqueKey(Task.TASK, "KEY_task_Task_ExtId", Task.TASK.EXTERNALIDENTIFIER);
        public static final UniqueKey<VolunteerRecord> KEY_VOLUNTEER_PRIMARY = createUniqueKey(Volunteer.VOLUNTEER, "KEY_volunteer_PRIMARY", Volunteer.VOLUNTEER.VOLUNTEERID);
        public static final UniqueKey<VolunteerRecord> KEY_VOLUNTEER_VOLU_EXTID = createUniqueKey(Volunteer.VOLUNTEER, "KEY_volunteer_Volu_ExtId", Volunteer.VOLUNTEER.EXTERNALIDENTIFIER);
        public static final UniqueKey<VolunteerinstanceRecord> KEY_VOLUNTEERINSTANCE_PRIMARY = createUniqueKey(Volunteerinstance.VOLUNTEERINSTANCE, "KEY_volunteerinstance_PRIMARY", Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID, Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERINSTANCEID);
        public static final UniqueKey<VolunteerinstanceRecord> KEY_VOLUNTEERINSTANCE_VOIN_EXTID = createUniqueKey(Volunteerinstance.VOLUNTEERINSTANCE, "KEY_volunteerinstance_VoIn_ExtId", Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID, Volunteerinstance.VOLUNTEERINSTANCE.EXTERNALIDENTIFIER);
        public static final UniqueKey<VolunteermatchRecord> KEY_VOLUNTEERMATCH_PRIMARY = createUniqueKey(Volunteermatch.VOLUNTEERMATCH, "KEY_volunteermatch_PRIMARY", Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID, Volunteermatch.VOLUNTEERMATCH.VOLUNTEERMATCHID);
        public static final UniqueKey<VolunteermatchRecord> KEY_VOLUNTEERMATCH_VOMA_EXTID = createUniqueKey(Volunteermatch.VOLUNTEERMATCH, "KEY_volunteermatch_VoMa_ExtId", Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID, Volunteermatch.VOLUNTEERMATCH.EXTERNALIDENTIFIER);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<TaskRecord, VolunteerRecord> TASK_VOLU_FK = createForeignKey(com.apon.database.generated.Keys.KEY_VOLUNTEER_PRIMARY, Task.TASK, "Task_Volu_FK", Task.TASK.VOLUNTEERID);
        public static final ForeignKey<VolunteerinstanceRecord, VolunteerRecord> VOIN_VOLU_FK = createForeignKey(com.apon.database.generated.Keys.KEY_VOLUNTEER_PRIMARY, Volunteerinstance.VOLUNTEERINSTANCE, "VoIn_Volu_FK", Volunteerinstance.VOLUNTEERINSTANCE.VOLUNTEERID);
        public static final ForeignKey<VolunteermatchRecord, VolunteerRecord> VOMA_VOLU_FK = createForeignKey(com.apon.database.generated.Keys.KEY_VOLUNTEER_PRIMARY, Volunteermatch.VOLUNTEERMATCH, "VoMa_Volu_FK", Volunteermatch.VOLUNTEERMATCH.VOLUNTEERID);
        public static final ForeignKey<VolunteermatchRecord, StudentRecord> VOMA_STUD_FK = createForeignKey(com.apon.database.generated.Keys.KEY_STUDENT_PRIMARY, Volunteermatch.VOLUNTEERMATCH, "VoMa_Stud_FK", Volunteermatch.VOLUNTEERMATCH.STUDENTID);
    }
}
