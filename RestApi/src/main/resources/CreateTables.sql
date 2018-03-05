create table Scriptlog (
  script_Name varchar(100) not null,
  ts_Started timestamp not null,
  ts_Finished timestamp,
  is_Completed boolean not null,
  constraint "ScLo_PK" primary key (script_Name)
);

insert into Scriptlog values ('20180105_CreateTables', current_timestamp, null, false);

-- Create all kinds of tables.
create table Volunteer (
  volunteer_Id int not null,
  external_Identifier varchar(10) not null,
  first_Name varchar(100),
  insertion varchar(100),
  last_Name varchar(100) not null,
  date_Of_Birth date not null,
  sex varchar(1) not null, -- M = male, F = female.
  phone_Number varchar(100),
  mobile_Phone_Number varchar(100),
  email varchar(100),
  postal_Code varchar(6),
  city varchar(100),
  street_Name varchar(100),
  house_Nr varchar(10),
  log clob,
  job varchar(100),
  date_Training date,
  is_Class_Assistant boolean not null,
  is_Taalmaatje boolean not null,
  constraint "Volu_PK" primary key (volunteer_Id),
  constraint "Volu_ExtId" unique (external_Identifier)
);

create table VolunteerInstance (
  volunteer_Id int not null,
  volunteer_InstanceId int not null,
  external_Identifier varchar(10) not null,
  date_Start date not null,
  date_End date,
  constraint "VoIn_PK" primary key (volunteer_Id, volunteer_InstanceId),
  constraint "VoIn_ExtId" unique (volunteer_Id, external_Identifier),
  constraint "VoIn_Volu_FK" foreign key (volunteer_Id) references Volunteer (volunteer_Id)
);

create table Student (
  student_Id int not null,
  external_Identifier varchar(10) not null,
  first_Name varchar(100),
  insertion varchar(100),
  last_Name varchar(100),
  sex varchar(1) not null,
  date_Of_Birth date not null,
  group_Identification varchar(100),
  has_Quit boolean not null,
  constraint "Stud_PK" primary key (student_Id),
  constraint "Stud_ExtId" unique(external_Identifier)
);

create table VolunteerMatch (
  volunteer_Id int not null,
  volunteer_Match_Id int not null,
  external_Identifier varchar(10) not null,
  student_Id int not null,
  date_Start date not null,
  date_End date,
  constraint "VoMa_PK" primary key (volunteer_Id, volunteer_Match_Id),
  constraint "VoMa_ExtId" unique (volunteer_Id, external_Identifier),
  constraint "VoMa_Volu_FK" foreign key (volunteer_Id) references Volunteer (volunteer_Id),
  constraint "VoMa_Stud_FK" foreign key (student_Id) references Student (student_Id)
);

create table Task (
  task_Id int not null,
  external_Identifier varchar(10) not null,
  title varchar(100) not null,
  description varchar(1000),
  volunteer_Id int not null,
  is_Finished boolean not null,
  date_To_Be_Finished date,
  constraint "Task_PK" primary key (task_Id),
  constraint "Task_ExtId" unique (external_Identifier),
  constraint "Task_Volu_FK" foreign key (volunteer_Id) references Volunteer (volunteer_Id)
);

create table LogonUser (
  logonUserId int not null,
  username varchar(30) not null,
  password varchar(60) not null,
  constraint "LoUs_PK" primary key (logonUserId),
  constraint "LoUs_UsNa_UC" unique (username)
);

update Scriptlog set is_Completed = true, ts_Finished = current_timestamp where script_Name = 'CreateTables';
