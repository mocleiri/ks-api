//StateEntity
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Draft', 'Draft', 'kuali.atp.process', 'Indicates that this Time Period is just tentative', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Official', 'Official', 'kuali.atp.process', 'Indicates that this Time Period has been established', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Draft', 'Draft', 'kuali.milestone.process', 'Indicates that this milestone is just tentative', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Official', 'Official', 'kuali.milestone.process', 'Indicates that this milestone has been established', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.active', 'Active', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is active', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.inactive', 'Inactive', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is inactive', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.active', 'Active', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is active', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.inactive', 'Inactive', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is inactive', 0)
//HOLD States
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.active', 'Active', 'kuali.hold.restriction.process', 'This restriction is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.inactive', 'Inactive', 'kuali.hold.restriction.process', 'The restriction is inactive and should not be enforced', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.active', 'Active', 'kuali.hold.issue.process', 'This issue is active and can be attached to holds ', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.inactive', 'Inactive', 'kuali.hold.issue.process', 'The hold is inactive an cannot be attached to new holds ', 0)

//StateProcessEntity
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.process', 'kuali.atp.process', 'kuali atp state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.milestone.process', 'kuali.milestone.process', 'kuali milestone state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.process', 'kuali.atp.atp.relation.process', 'kuali atp-atp relation state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.process', 'kuali.atp.milestone.relation.process', 'kuali atp-milestone relation state process', 0)

//HOLD Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.process', 'kuali.hold.restriction.process', 'kuali hold restriction process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.issue.process', 'kuali.hold.issue.process', 'kuali hold issue process', 0)

//StateProcessRelationEntity
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-1', 'kuali.atp.process', null, 'kuali.atp.state.Draft', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-2', 'kuali.atp.process', 'kuali.atp.state.Draft', 'kuali.atp.state.Official', 0)

//HOLD StateProcessRelation
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-3', 'kuali.hold.restriction.process', null, 'kuali.hold.restriction.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-4', 'kuali.hold.restriction.process', 'kuali.hold.restriction.state.active', 'kuali.hold.restriction.state.inactive', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-5', 'kuali.hold.issue.process', null, 'kuali.hold.issue.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-6', 'kuali.hold.issue.process', 'kuali.hold.issue.state.active', 'kuali.hold.issue.state.inactive', 0)

// HoldRichTextEntity
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-1-Desc', '<p>Issue Desc 101</p>', 'Issue Desc 101',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-2-Desc', '<p>Issue Desc for deletion test</p>', 'Issue Desc for deletion test',0)

//IssueEntity
INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-1', 'Issue one', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '1', 'Issue-1-Desc', 0)
INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-2', 'Issue two', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '2', 'Issue-2-Desc', 0)

// TypeTypeRelationEntity - TODO: move to object-URI-neutral sql file
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-0', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.atp.atp.relation.precedes', 'kuali.atp.type.Fall', 'kuali.atp.type.Spring', 0, 'Fall precedes Spring')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Fall', 0, 'Academic year contains semester')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Spring', 1, 'Academic year contains semester')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-3', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Summer', 2, 'Academic year contains semester')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-4', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.SpringBreak' , 0, 'Spring break is a holiday')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-5', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.Thanksgiving' , 0, 'Thanksgiving is a holiday')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.3', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.4', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.5', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.6', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.7', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.8', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.9', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.10', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.11', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.12', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.13', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.14', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.15', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.16', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.17', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.18', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.19', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.0', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.3', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.4', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.5', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.6', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.7', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.8', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.9', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.10', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.11', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.12', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.13', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.14', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.15', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.16', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.17', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter can be associated with an Academic Calendar')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.acal.relation.allowed.18', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.allowed', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc can be associated with an Academic Calendar')

// TypeEntity
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.AcademicCalendar', 'Academic Calendar', 'Academic Calendar', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.CampusCalendar', 'Campus Calendar', 'Campus Calendar', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Fall', 'Fall', 'Fall Semester', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Spring', 'Spring', 'Spring Semester', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.AY', 'AY', 'Full Academic Year', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.FY', 'FY', 'Fiscal Year', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Holiday', 'Holiday', 'Holiday', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.SpringBreak', 'SpringBreak', 'Spring Break', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Thanksgiving', 'Thanksgiving', 'Thanksgiving', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.AdvanceRegistrationPeriod', 'Advance Registration Period', 'Advance Registration Period', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0) 
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.RegistrationPeriod', 'Registration Period', 'Registration Period', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.DropDate', 'Drop Date', 'Drop Period Ends', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.GradesDue', 'Grades Due', 'Grades Due', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.AdvanceRegistrationPeriod', 'Advance Registration Period', 'Advance Registration Period', 0) 
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.RegistrationPeriod', 'Registration Period', 'Registration Period', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.DropDate', 'Drop Date', 'Drop Period Ends', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.GradesDue', 'Grades Due', 'Grades Due', 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 'http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.atp.relation.associated', 'kuali.atp.atp.relation.associated', 'kuali.atp.atp.relation.associated', 'http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.atp.relation.precedes', 'kuali.atp.atp.relation.precedes', 'kuali.atp.atp.relation.precedes', 'http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.milestone.relation.owns', 'Owns', 'Indicates the ATP owns the specified milestone', 'http://student.kuali.org/wsdl/atp/AtpMilestoneRelationInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.milestone.relation.reuses', 'Reuses', 'Indicates the ATP reuses the specified milestone that another ATP owns ', 'http://student.kuali.org/wsdl/atp/AtpMilestoneRelationInfo', 0)
--
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Fall', 'Fall', 'Fall', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.HalfFall1', 'Half Fall 1', 'Half Fall 1', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.HalfFall2', 'Half Fall 2', 'Half Fall 2', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.HalfSpring1', 'Half Spring 1', 'Half Spring 1', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.HalfSpring2', 'Half Spring 2', 'Half Spring 2', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Mini-mester1A', 'Mini-mester 1A', 'Mini-mester 1A', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Mini-mester1B', 'Mini-mester 1B', 'Mini-mester 1B', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Mini-mester2C', 'Mini-mester 2C', 'Mini-mester 2C', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Mini-mester2D', 'Mini-mester 2D', 'Mini-mester 2D', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Session1', 'Session 1', 'Session 1', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Session2', 'Session 2', 'Session 2', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.SessionG1', 'Session G1', '', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.SessionG2', 'Session G2', 'Session G2', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Spring', 'Spring', 'Spring', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.SpringBreak', 'Spring Break', 'Spring Break', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Summer', 'Summer', 'Summer', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.SummerEve', 'Summer Eve', 'Summer Eve', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Winter', 'Winter', 'Winter', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.type.Adhoc', 'Ad hoc', 'Ad hoc', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
