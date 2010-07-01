--
-- Copyright 2010 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

// Natural Language Translation Test Data

// RICH TEXT
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.1', '<p>Requirement Component 1</p>', 'Req Comp 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.2', '<p>Requirement Component 2</p>', 'Req Comp 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.3', '<p>Requirement Component 3</p>', 'Req Comp 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.4', '<p>Requirement Component 4</p>', 'Req Comp 4')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.5', '<p>Requirement Component 5</p>', 'Req Comp 4')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.1', '<p>Statement 1</p>', 'Statement 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.2', '<p>Statement 2</p>', 'Statement 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.3', '<p>Statement 3</p>', 'Statement 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.4', '<p>Statement 4</p>', 'Statement 4')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.5', '<p>Statement 5</p>', 'Statement 5')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.6', '<p>Statement 101</p>', 'Statement 101')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.7', '<p>Statement 102</p>', 'Statement 102')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.8', '<p>Statement 103</p>', 'Statement 103')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.9', '<p>Statement 104</p>', 'Statement 104')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.10', '<p>Statement 105</p>', 'Statement 105')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.11', '<p>Statement 106</p>', 'Statement 106')

// REQ COM
// Courses
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.none')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.none')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-6', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-7', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-8', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.enrolled.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-9', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.enrolled.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-10', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.credits.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-11', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.credits.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-12', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.credits.completed.none')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-13', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.credits.completed.max')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-14', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.credits.completed.max')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-15', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.gpa.min')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-16', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.grade.min')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-17', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.grade.max')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-18', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.permission.org.required')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-19', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.permission.instructor.required')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-20', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.course.courseset.completed.all')
// Programs
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-101', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-102', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.notcompleted.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-103', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.completed.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-104', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.coursecompleted.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-105', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-106', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.coursecompleted.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-107', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.completed.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-108', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.notcompleted.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-109', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.program.programset.coursecompleted.nof')


// KSST REQ COM FIELD
// Courses
//INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-x', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-1-1', 'reqCompFieldType.requiredCount', '0')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-1-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-1-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-2-1', 'reqCompFieldType.requiredCount', '0')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-2-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-2-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-3-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-3-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-3-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-4-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-4-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-4-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-5-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-5-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-5-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-6-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-6-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-6-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-7-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-7-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-7-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-8-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-8-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-8-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-9-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-9-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-9-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-10-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-10-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-10-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-11-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-11-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-11-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-12-1', 'reqCompFieldType.requiredCount', '0')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-12-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-12-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-13-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-13-2', 'reqCompFieldType.operator', 'less_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-13-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-14-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-14-2', 'reqCompFieldType.operator', 'less_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-14-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-15-1', 'reqCompFieldType.gpa', '3.5')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-15-2', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-16-1', 'reqCompFieldType.grade', 'B')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-16-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-16-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-16-4', 'reqCompFieldType.gradeType', 'letter')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-17-1', 'reqCompFieldType.grade', 'C')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-17-2', 'reqCompFieldType.operator', 'less_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-17-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-17-4', 'reqCompFieldType.gradeType', 'letter')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-18', 'reqCompFieldType.orgid', 'English Department')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-19', 'reqCompFieldType.personid', 'Smith')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-20-1', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-20-2', 'reqCompFieldType.operator', 'equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-20-3', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-CLUSET-1')
// Programs
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-101', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-102', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-103', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-104', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-105', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-106', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-107', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-108', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-109', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-110', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-111', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-112', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-113', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-114', 'reqCompFieldType.cluSet.id', 'CLUSET-NL-PROGRAMS-2')

// KSST_RC_JN_RC_FIELD
// Courses
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-1', 'FIELD-1-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-1', 'FIELD-1-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-1', 'FIELD-1-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-2', 'FIELD-2-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-2', 'FIELD-2-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-2', 'FIELD-2-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-3', 'FIELD-3-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-3', 'FIELD-3-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-3', 'FIELD-3-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-4', 'FIELD-4-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-4', 'FIELD-4-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-4', 'FIELD-4-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-5', 'FIELD-5-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-5', 'FIELD-5-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-5', 'FIELD-5-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-6', 'FIELD-6-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-6', 'FIELD-6-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-6', 'FIELD-6-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-7', 'FIELD-7-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-7', 'FIELD-7-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-7', 'FIELD-7-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-8', 'FIELD-8-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-8', 'FIELD-8-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-8', 'FIELD-8-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-9', 'FIELD-9-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-9', 'FIELD-9-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-9', 'FIELD-9-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-10', 'FIELD-10-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-10', 'FIELD-10-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-10', 'FIELD-10-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-11', 'FIELD-11-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-11', 'FIELD-11-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-11', 'FIELD-11-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-12', 'FIELD-12-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-12', 'FIELD-12-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-12', 'FIELD-12-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-13', 'FIELD-13-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-13', 'FIELD-13-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-13', 'FIELD-13-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-14', 'FIELD-14-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-14', 'FIELD-14-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-14', 'FIELD-14-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-15', 'FIELD-15-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-15', 'FIELD-15-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-16', 'FIELD-16-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-16', 'FIELD-16-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-16', 'FIELD-16-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-16', 'FIELD-16-4')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-17', 'FIELD-17-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-17', 'FIELD-17-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-17', 'FIELD-17-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-17', 'FIELD-17-4')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-18', 'FIELD-18')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-19', 'FIELD-19')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-20', 'FIELD-20-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-20', 'FIELD-20-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-20', 'FIELD-20-3')
// Programs
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-101', 'FIELD-101')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-101', 'FIELD-102')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-102', 'FIELD-103')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-103', 'FIELD-104')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-104', 'FIELD-105')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-104', 'FIELD-106')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-105', 'FIELD-107')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-105', 'FIELD-108')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-106', 'FIELD-109')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-106', 'FIELD-110')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-107', 'FIELD-111')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-108', 'FIELD-112')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-109', 'FIELD-113')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-109', 'FIELD-114')

// STMT
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 1','KSST_STMT.DESC.1','ACTIVE','AND', null ,'kuali.luStatementType.course')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 2','KSST_STMT.DESC.2','ACTIVE','AND','STMT-1','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 3','KSST_STMT.DESC.3','ACTIVE','AND','STMT-1','kuali.luStatementType.coreqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 4','KSST_STMT.DESC.4','ACTIVE','AND','STMT-1','kuali.luStatementType.antireqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 5','KSST_STMT.DESC.5','ACTIVE','AND','STMT-1','kuali.luStatementType.enrollAcademicReadiness')

// STMT <-> REQ COM
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-1','STMT-4')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-2','STMT-4')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-3','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-4','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-5','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-6','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-7','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-8','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-9','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-10','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-11','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-12','STMT-4')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-13','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-14','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-15','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-16','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-17','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-18','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-19','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-20','STMT-2')

// KSST_REF_STMT_REL
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-1', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-5', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-5', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)


