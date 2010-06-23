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

// ------------------------------------------
// | Natural Language Translation Test Data |
// ------------------------------------------

// CLU/CLU Set Configuration

// CluInstructor
INSERT INTO KSLU_CLU_INSTR (ID, ORG_ID, PERS_ID) VALUES ('INSTR-1', 'ORG-1', 'PersonID')
INSERT INTO KSLU_CLU_INSTR (ID, ORG_ID, PERS_ID) VALUES ('INSTR-2', 'ORG-2', 'PersonID')

// CluPublishing
INSERT INTO KSLU_CLU_PUBL (ID, END_CYCLE, START_CYCLE, ST, TYPE, PRI_INSTR_ID) VALUES ('PUBL-1', 'Fall', 'Spring', 'State', 'Type', 'INSTR-1')
INSERT INTO KSLU_CLU_PUBL (ID, END_CYCLE, START_CYCLE, ST, TYPE, PRI_INSTR_ID) VALUES ('PUBL-2', 'Fall', 'Summer', 'State', 'Type', 'INSTR-2')

// RichText
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-1', '<p>Desc</p>', 'Desc')
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-2', '<p>Marketing Desc</p>', 'Marketing Desc')
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-3', '<p>Desc2</p>', 'Desc2')
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-4', '<p>Marketing Desc2</p>', 'Marketing Desc2')
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-5', '<p>Core CluSet</p>', 'Core CluSet')
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-6', '<p>CoreEnglish CluSet</p>', 'CoreEnglish CluSet')

// CluCredit
INSERT INTO KSLU_CLU_CR (ID, INSTR_UNIT, MAX_ALOW_INACV_ATP, MAX_ALOW_INACV_TMQ, MAX_TM_RSLT_RCGZ_ATP, MAX_TM_RSLT_RCGZ_TMQ, MAX_TM_TO_COMP_ATP, MAX_TM_TO_COMP_TMQ, MAX_TOT_UNIT, MIN_TM_TO_COMP_ATP, MIN_TM_TO_COMP_TMQ, MIN_TOT_UNIT, REPEAT_CNT, REPEAT_TM_ATP, REPEAT_TM_TMQ, REPEAT_UNIT) VALUES ('CR-1', 0, 'ATP-INACT-1', 0, 'ATP-RECOG-1', 0, 'ATP-MAXCOMPLETE-1', 0, 0, 'ATP-MINCOMPLETE-1', 0, 0, 'Repeat Count', 'ATP-REPEAT-1', 0, 'Repeat Units')
INSERT INTO KSLU_CLU_CR (ID, INSTR_UNIT, MAX_ALOW_INACV_ATP, MAX_ALOW_INACV_TMQ, MAX_TM_RSLT_RCGZ_ATP, MAX_TM_RSLT_RCGZ_TMQ, MAX_TM_TO_COMP_ATP, MAX_TM_TO_COMP_TMQ, MAX_TOT_UNIT, MIN_TM_TO_COMP_ATP, MIN_TM_TO_COMP_TMQ, MIN_TOT_UNIT, REPEAT_CNT, REPEAT_TM_ATP, REPEAT_TM_TMQ, REPEAT_UNIT) VALUES ('CR-2', 0, 'ATP-INACT-2', 0, 'ATP-RECOG-2', 0, 'ATP-MAXCOMPLETE-2', 0, 0, 'ATP-MINCOMPLETE-2', 0, 0, 'Repeat Count', 'ATP-REPEAT-2', 0, 'Repeat Units')

// CluAccounting
INSERT INTO KSLU_CLU_ACCT (ID) VALUES ('ACCT-1')
INSERT INTO KSLU_CLU_ACCT (ID) VALUES ('ACCT-2')

// CluFee
INSERT INTO KSLU_CLU_FEE (ID, VERSIONIND) VALUES ('FEE-1', 0)
INSERT INTO KSLU_CLU_FEE (ID, VERSIONIND) VALUES ('FEE-2', 0)

// LU Type
INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.lu.type.CreditCourse', 'A Credit Course', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Course')
INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('luType.shell.program', 'A Shell Program', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program')

// NL - Clu - MATH152
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-1', 'MATH152', 'MATH', '152', 'MATH 152 Linear Systems', 'MATH 152', 'State-1', 'Type-1', 'Variation-1')
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-1', 'INSTR-1')

// NL - Clu - MATH221
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-2', 'MATH221', 'MATH', '221', 'MATH 221 Matrix Algebra', 'MATH 221', 'State-1', 'Type-1', 'Variation-1')
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-2', 'INSTR-1')

// NL - Clu - MATH180
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-3', 'MATH180', 'MATH', '180', 'MATH 180 Differential Calculus with Physical Applications', 'MATH 180', 'State-1', 'Type-1', 'Variation-1')
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-3', 'INSTR-1')

// NL - Clu - MATH200
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-4', 'MATH200', 'MATH', '200', 'MATH 200 Calculus III', 'MATH 200', 'State-1', 'Type-1', 'Variation-1')
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-4', 'INSTR-1')

// NL - Clu - MATH215
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-5', 'MATH215', 'MATH', '215', 'MATH 215 Elementary Differential Equations I', 'MATH 215', 'State-1', 'Type-1', 'Variation-1')
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-5', 'INSTR-1')

// NL - CluSet - Math 152,221 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221 CLU Set', 'RICHTEXT-5', 'kuali.cluSet.type.creditCourse', 'active', '63', 1, 1)

// NL - CluSet - Math 152,221,180 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221,180 CLU Set', 'RICHTEXT-5', 'kuali.cluSet.type.creditCourse', 'active', '63', 1, 1)

// NL - CluSet - Math 221,180,200,215 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221,180 CLU Set', 'RICHTEXT-5', 'kuali.cluSet.type.creditCourse', 'active', '63', 1, 1)

// NL - CluSet - Math 152 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158 CLU Set', 'RICHTEXT-5', 'kuali.cluSet.type.creditCourse', 'active', '63', 1, 1)

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-1', 'CLU-NL-1')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-1', 'CLU-NL-3')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-1')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-2')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-3')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-2')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-3')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-4')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-5')

INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-4', 'CLU-NL-1')

// ---------- Programs ----------

// NL - Clu - Sociology Program
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-PRG-1', 'SOCY', 'Sociology', null, 'Sociology', 'Sociology', 'Sociology', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-PROGRAM-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'luType.shell.program', 'IDENT-NL-PRG-1', 'INSTR-1')
// NL - Clu - Geology Program
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-PRG-2', 'GEOG', 'Geology', null, 'Geology', 'Geology', 'Geology', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-PROGRAM-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-1', 'FEE-1', 'luType.shell.program', 'IDENT-NL-PRG-2', 'INSTR-1')

// NL - CluSet - Geology and Sociology CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-PROGRAMS-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Geology and Sociology CLU Set', 'RICHTEXT-5', 'luType.shell.program', 'active', '63', 1, 1)
// NL - CluSet - Sociology CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, TYPE, ST, ADMIN_ORG_ID, REUSABLE, REFERENCEABLE) VALUES ('CLUSET-NL-PROGRAMS-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Sociology CLU Set', 'RICHTEXT-5', 'luType.shell.program', 'active', '63', 1, 1)

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-PROGRAMS-1', 'CLU-NL-PROGRAM-1')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-PROGRAMS-1', 'CLU-NL-PROGRAM-2')

INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-PROGRAMS-2', 'CLU-NL-PROGRAM-1')
