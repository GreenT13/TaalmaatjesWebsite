-- noinspection SqlResolve
SET sql_mode = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

create table scriptlog (
  scriptName varchar(100) not null,
  tsStarted timestamp not null,
  tsFinished timestamp,
  isCompleted boolean not null,
  constraint `ScLo_PK` primary key (scriptName)
);

insert into scriptlog values ('20180105_CreateTables', current_timestamp, null, false);

-- Create all kinds of tables.
create table volunteer (
  volunteerId int not null,
  externalIdentifier varchar(10) not null,
  firstName varchar(100),
  insertion varchar(100),
  lastName varchar(100) not null,
  dateOfBirth date not null,
  gender varchar(1) not null, -- M = male, F = female.
  phoneNumber varchar(100),
  mobilePhoneNumber varchar(100),
  email varchar(100),
  postalCode varchar(6),
  city varchar(100),
  streetName varchar(100),
  houseNr varchar(10),
  log TEXT,
  job varchar(100),
  dateTraining date,
  isClassAssistant boolean not null,
  isTaalmaatje boolean not null,
  constraint `Volu_PK` primary key (volunteerId),
  constraint `Volu_ExtId` unique (externalIdentifier)
);

create table volunteerinstance (
  volunteerId int not null,
  volunteerInstanceId int not null,
  externalIdentifier varchar(10) not null,
  dateStart date not null,
  dateEnd date,
  constraint `VoIn_PK` primary key (volunteerId, volunteerInstanceId),
  constraint `VoIn_ExtId` unique (volunteerId, externalIdentifier),
  constraint `VoIn_Volu_FK` foreign key (volunteerId) references volunteer (volunteerId)
);

create table student (
  studentId int not null,
  externalIdentifier varchar(10) not null,
  firstName varchar(100),
  insertion varchar(100),
  lastName varchar(100),
  gender varchar(1) not null,
  dateOfBirth date not null,
  groupIdentification varchar(100),
  hasQuit boolean not null,
  log TEXT,
  constraint `Stud_PK` primary key (studentId),
  constraint `Stud_ExtId` unique(externalIdentifier)
);

create table volunteermatch (
  volunteerId int not null,
  volunteerMatchId int not null,
  externalIdentifier varchar(10) not null,
  studentId int not null,
  dateStart date not null,
  dateEnd date,
  constraint `VoMa_PK` primary key (volunteerId, volunteerMatchId),
  constraint `VoMa_ExtId` unique (volunteerId, externalIdentifier),
  constraint `VoMa_Volu_FK` foreign key (volunteerId) references volunteer (volunteerId),
  constraint `VoMa_Stud_FK` foreign key (studentId) references student (studentId)
);

create table task (
  taskId int not null,
  externalIdentifier varchar(10) not null,
  title varchar(100) not null,
  description varchar(1000),
  volunteerId int not null,
  isFinished boolean not null,
  dateToBeFinished date not null,
  constraint `Task_PK` primary key (taskId),
  constraint `Task_ExtId` unique (externalIdentifier),
  constraint `Task_Volu_FK` foreign key (volunteerId) references volunteer (volunteerid)
);

create table logonuser (
  logonUserId int not null,
  username varchar(30) not null,
  password varchar(60) not null,
  nrOfLogonAttempts int not null,
  dateEndValid date,
  constraint `LoUs_PK` primary key (logonUserId),
  constraint `LoUs_UsNa_UC` unique (username)
);

update scriptlog set isCompleted = true, tsFinished = current_timestamp where scriptName = '20180105_CreateTables';
