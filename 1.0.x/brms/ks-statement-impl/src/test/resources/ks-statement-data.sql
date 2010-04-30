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

// ----------------------------------------------
// |   Natural Language Translation Test Data   |
// ----------------------------------------------

// Rich text
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.1', '<p>Requirement Component 1</p>', 'Req Comp 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.2', '<p>Requirement Component 2</p>', 'Req Comp 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.3', '<p>Requirement Component 3</p>', 'Req Comp 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.4', '<p>Requirement Component 4</p>', 'Req Comp 4')

INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.5', '<p>RC Tree View 1</p>', 'RC Tree View 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.6', '<p>RC Tree View 2</p>', 'RC Tree View 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.7', '<p>RC Tree View 3</p>', 'RC Tree View 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.8', '<p>RC Tree View 4</p>', 'RC Tree View 4')

// NL - Req Component
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.1of2')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.nof')

// NL - KSST REQ COM FIELD
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-3', 'reqCompFieldType.cluSet', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-4', 'reqCompFieldType.gpa', '3.5')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-5', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-6', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-7', 'reqCompFieldType.clu', 'CLU-NL-3,CLU-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-8', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-9', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-10', 'reqCompFieldType.cluSet', 'CLUSET-NL-2')

// NL - KSST_RC_JN_RC_FIELD
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-2', 'FIELD-4')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-5')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-6')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-7')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-8')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-9')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-10')

// Rich text
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
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.12', '<p>Statement Tree View 1</p>', 'Statement Tree View 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.13', '<p>Statement Tree View 2</p>', 'Statement Tree View 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.14', '<p>Statement Tree View 3</p>', 'Statement Tree View 3')

// LU_STMT
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 1','KSST_STMT.DESC.1','ACTIVE','AND', null ,'kuali.luStatementType.createCourseAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 2','KSST_STMT.DESC.2','ACTIVE','AND','STMT-1','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 3','KSST_STMT.DESC.3','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 4','KSST_STMT.DESC.4','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 5','KSST_STMT.DESC.5','ACTIVE','OR',null,'kuali.luStatementType.prereqAcademicReadiness')

INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-101', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 101','KSST_STMT.DESC.6','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-102', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 102','KSST_STMT.DESC.7','ACTIVE','OR','STMT-101','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-103', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 103','KSST_STMT.DESC.8','ACTIVE','AND','STMT-101','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-104', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 104','KSST_STMT.DESC.9','ACTIVE','AND','STMT-102','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-105', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 105','KSST_STMT.DESC.10','ACTIVE','AND','STMT-102','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-106', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 106','KSST_STMT.DESC.11','ACTIVE','OR','STMT-103','kuali.luStatementType.prereqAcademicReadiness')

INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-TV-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 1','KSST_STMT.DESC.12','ACTIVE','OR',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-TV-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 2','KSST_STMT.DESC.13','ACTIVE','AND','STMT-TV-1','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-TV-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 3','KSST_STMT.DESC.14','ACTIVE','AND','STMT-TV-1','kuali.luStatementType.prereqAcademicReadiness')

// REQ COM
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.grdCondCourseList')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')

INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.6','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.7','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.8','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')

// CLU <-> LU STMT
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-1')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-2','STMT-3')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-5','STMT-4')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-4','STMT-5')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-5')

//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-101')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-2','STMT-102')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-3','STMT-103')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-4','STMT-104')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-5','STMT-105')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-106')

// LU STMT <-> REQ COM
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-1','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-2','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-3','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-2','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-3','STMT-4')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-5')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-4','STMT-5')

INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-104')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-2','STMT-104')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-3','STMT-105')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-106')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-4','STMT-106')

INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-1','STMT-TV-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-2','STMT-TV-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-3','STMT-TV-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-4','STMT-TV-3')

// KSST_REF_STMT_REL
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-1', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-5', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-5', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)
