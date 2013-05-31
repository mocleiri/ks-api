--CE ACAL
insert into KSEN_ATP (ID, OBJ_ID, ATP_TYPE, ATP_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, ATP_CD, END_DT, START_DT, ADMIN_ORG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('b58c25aa-5d7f-4c85-academic-calendar', '8728c82e-9db4-4eb8-92a3-ac9c85addc88', 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Official', '2012-2013 Continuing Education Calendar', 'Continuing Education Calendar for 2012-2013', 'Continuing Education Calendar for 2012-2013', null, TIMESTAMP '2013-08-23 20:00:00', TIMESTAMP '2012-08-27 20:00:00', null, 0, TIMESTAMP '2013-01-02 05:12:50', 'batchjob', TIMESTAMP '2013-01-02 05:12:50', 'batchjob')
/

--MSTONES
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('a17db92d-2f5d-45a6-afbc-3345ffa8ce54', 'c32d69ef-66cc-42a5-8103-5f12592109fa', 'kuali.atp.milestone.AdvancedRegistrationPeriod', 'kuali.atp.state.Official', 'Registration Period', 'Registration Period', 'Registration Period', 0, 0, 0, null, 1, TIMESTAMP '2012-08-01 00:00:00', TIMESTAMP '2012-08-26 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('8b25483a-d9ad-4f57-b2a2-537563f9f701', 'a889014b-278f-490e-a360-b7d56f99f769', 'kuali.atp.milestone.CourseSelectionPeriodEnd', 'kuali.atp.state.Official', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 0, 0, 0, null, 0, TIMESTAMP '2012-09-07 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('d2face7c-1674-42ec-aa6f-d155c755b54e', '104ce050-69d3-4402-bd5a-41260a2e11e8', 'kuali.atp.milestone.DropDate', 'kuali.atp.state.Official', 'Last Day to Drop', 'Last Day to Drop', 'Last Day to Drop', 0, 0, 0, null, 0, TIMESTAMP '2012-10-14 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('4fe98b12-7754-4753-878c-570aeb054c41', '2c09f27e-bd0f-4760-8131-5f3730cd58ef', 'kuali.atp.milestone.GradesDue', 'kuali.atp.state.Official', 'Grades Due', 'Grades Due', 'Grades Due', 0, 0, 0, null, 0, TIMESTAMP '2012-11-28 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('1c684cba-1fb6-415c-8968-2bb8e1670a44', '05ab6c73-23fa-4668-95b4-a7038897c03c', 'kuali.atp.milestone.InstructionalPeriod', 'kuali.atp.state.Official', 'Instructional period', 'Instructional period', 'Instructional period', 0, 0, 0, null, 1, TIMESTAMP '2012-08-27 00:00:00', TIMESTAMP '2012-11-23 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('19894f26-7e85-437f-bb68-0383bec4649b', '227daee5-34ad-4c94-97ff-86b2c73a2fa3', 'kuali.atp.milestone.firstdayofclasses', 'kuali.atp.state.Official', 'First Day of Classes', 'First Day of Classes', 'First Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2012-08-27 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('807714f5-1d2b-43b4-8ae4-f765d97d0935', '88580279-d531-4dd4-b741-d2d6f2951581', 'kuali.atp.milestone.lastdayofclasses', 'kuali.atp.state.Official', 'Last Day of Classes', 'Last Day of Classes', 'Last Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2012-11-23 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('d2af6eb6-7040-4260-a5db-e96d25c161ea', '2f96e58b-4ef8-4110-9ff4-6ef787f9e369', 'kuali.atp.milestone.AdvancedRegistrationPeriod', 'kuali.atp.state.Official', 'Registration Period', 'Registration Period', 'Registration Period', 0, 0, 0, null, 1, TIMESTAMP '2012-11-01 00:00:00', TIMESTAMP '2012-11-25 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('00723874-e6ba-4067-b923-f30582f0f53f', 'e8bd5140-a132-464f-9909-5df04212ad51', 'kuali.atp.milestone.CourseSelectionPeriodEnd', 'kuali.atp.state.Official', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 0, 0, 0, null, 0, TIMESTAMP '2012-12-06 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('f4edde9e-cf0c-4bea-991a-f3b3a8b5d8a2', '758be0c1-8fd9-4fd7-8bab-89257b3bf754', 'kuali.atp.milestone.DropDate', 'kuali.atp.state.Official', 'Last Day to Drop', 'Last Day to Drop', 'Last Day to Drop', 0, 0, 0, null, 0, TIMESTAMP '2013-01-20 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('7db01a6f-5208-41df-b7a2-bcf1d5d58dee', 'd64aceb5-7f17-4ee2-96f7-ade69fb22afa', 'kuali.atp.milestone.GradesDue', 'kuali.atp.state.Official', 'Grades Due', 'Grades Due', 'Grades Due', 0, 0, 0, null, 0, TIMESTAMP '2013-02-24 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('bc26c0eb-7111-46d0-831c-f543e1e3d2a0', '35549914-107f-4732-a2e8-7f92af4fab6f', 'kuali.atp.milestone.InstructionalPeriod', 'kuali.atp.state.Official', 'Instructional period', 'Instructional period', 'Instructional period', 0, 0, 0, null, 1, TIMESTAMP '2012-11-26 00:00:00', TIMESTAMP '2013-02-22 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('9b79a299-4d02-48db-859b-ed484b0f6403', '6d745bc4-67cf-406a-a1b3-65816bb45ee0', 'kuali.atp.milestone.firstdayofclasses', 'kuali.atp.state.Official', 'First Day of Classes', 'First Day of Classes', 'First Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2012-11-26 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('612f6ba8-5740-4d85-a961-4fc2833e64eb', '87498173-aebb-43af-8ba7-f21df2bc84c5', 'kuali.atp.milestone.lastdayofclasses', 'kuali.atp.state.Official', 'Last Day of Classes', 'Last Day of Classes', 'Last Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2013-02-22 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('c1ba2b7d-298d-420e-adf2-928ade1fdb9e', '5640b801-2e2e-4e54-b562-4926e550731f', 'kuali.atp.milestone.AdvancedRegistrationPeriod', 'kuali.atp.state.Official', 'Registration Period', 'Registration Period', 'Registration Period', 0, 0, 0, null, 1, TIMESTAMP '2013-02-01 00:00:00', TIMESTAMP '2013-02-24 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('baafb7c2-40d5-449f-ad77-da1a8ee86d22', '5af4d3df-1a3b-405c-b0e1-0748740276af', 'kuali.atp.milestone.CourseSelectionPeriodEnd', 'kuali.atp.state.Official', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 0, 0, 0, null, 0, TIMESTAMP '2013-03-06 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('a89ceb53-2290-4bc1-9361-cf07968616d6', 'd3151cc4-0444-4282-9f61-edf2aa676121', 'kuali.atp.milestone.DropDate', 'kuali.atp.state.Official', 'Last Day to Drop', 'Last Day to Drop', 'Last Day to Drop', 0, 0, 0, null, 0, TIMESTAMP '2013-04-20 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('87f2a439-ba42-4bcb-8df8-a4251ca3caae', 'c69e56a0-9d78-4806-898a-f422495b168b', 'kuali.atp.milestone.GradesDue', 'kuali.atp.state.Official', 'Grades Due', 'Grades Due', 'Grades Due', 0, 0, 0, null, 0, TIMESTAMP '2013-05-21 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('8484d150-fa93-4d5b-9803-2a01a077c70a', '67afc95d-d0bd-47ee-9f03-0d531b650c5e', 'kuali.atp.milestone.InstructionalPeriod', 'kuali.atp.state.Official', 'Instructional period', 'Instructional period', 'Instructional period', 0, 0, 0, null, 1, TIMESTAMP '2013-02-25 00:00:00', TIMESTAMP '2013-02-25 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('822e5465-2567-467d-9e53-e4bd94a43ffb', 'c7ad3520-ed94-40da-b829-03b9e212b46e', 'kuali.atp.milestone.firstdayofclasses', 'kuali.atp.state.Official', 'First Day of Classes', 'First Day of Classes', 'First Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2013-02-25 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('9daba2ad-833d-427b-a3a7-36b3a0c91f7d', 'deb13e8e-9abe-483f-a945-b8f7bf692fbd', 'kuali.atp.milestone.lastdayofclasses', 'kuali.atp.state.Official', 'Last Day of Classes', 'Last Day of Classes', 'Last Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2013-05-17 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('1e3c63c6-3d9f-4ee9-a1b8-556c96c662c5', '7e9afd08-fac8-44d0-8c85-e151dcf99417', 'kuali.atp.milestone.AdvancedRegistrationPeriod', 'kuali.atp.state.Official', 'Registration Period', 'Registration Period', 'Registration Period', 0, 0, 0, null, 1, TIMESTAMP '2013-05-01 00:00:00', TIMESTAMP '2013-05-26 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('a588be76-f8a3-421e-aaee-af0dbece43c8', '5e3e92e3-1d73-46ee-bdf6-346b3a2a40e1', 'kuali.atp.milestone.CourseSelectionPeriodEnd', 'kuali.atp.state.Official', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 'Schedule Adjustment Deadline', 0, 0, 0, null, 0, TIMESTAMP '2013-06-06 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('9a3d4b8e-6e75-4310-8f16-c3879931c86d', '7937bbdb-8f58-4d11-b3af-ce648e603ceb', 'kuali.atp.milestone.DropDate', 'kuali.atp.state.Official', 'Last Day to Drop', 'Last Day to Drop', 'Last Day to Drop', 0, 0, 0, null, 0, TIMESTAMP '2013-07-20 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('5df95af9-5f6a-40ae-82ac-e59022e91f8d', 'c04927da-35ec-4c41-bc58-3b74d4d598a3', 'kuali.atp.milestone.GradesDue', 'kuali.atp.state.Official', 'Grades Due', 'Grades Due', 'Grades Due', 0, 0, 0, null, 0, TIMESTAMP '2013-08-27 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('7a7a1f47-4804-45f4-9b61-c98adb80e931', 'e1f586b1-4803-44e1-9398-a578a7dbc3d3', 'kuali.atp.milestone.InstructionalPeriod', 'kuali.atp.state.Official', 'Instructional period', 'Instructional period', 'Instructional period', 0, 0, 0, null, 1, TIMESTAMP '2013-05-27 00:00:00', TIMESTAMP '2013-08-23 00:00:00', 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('b46b527b-47b7-4867-881e-7b5cdc3602dc', '1be7c97b-2d58-494e-8c94-3a7aa97b634b', 'kuali.atp.milestone.firstdayofclasses', 'kuali.atp.state.Official', 'First Day of Classes', 'First Day of Classes', 'First Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2013-05-27 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/
insert into KSEN_MSTONE (ID, OBJ_ID, MSTONE_TYPE, MSTONE_STATE, NAME, DESCR_PLAIN, DESCR_FORMATTED, IS_ALL_DAY, IS_INSTRCT_DAY, IS_RELATIVE, RELATIVE_ANCHOR_MSTONE_ID, IS_DATE_RANGE, START_DT, END_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('bb8269f2-7dde-42a5-94ca-a55715354d60', 'aa6993d1-b018-4aaf-b78e-ed446caa4db2', 'kuali.atp.milestone.lastdayofclasses', 'kuali.atp.state.Official', 'Last Day of Classes', 'Last Day of Classes', 'Last Day of Classes', 0, 0, 0, null, 0, TIMESTAMP '2013-08-23 00:00:00', null, 0, TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob')
/

--ATP-MSTONE
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('112dbeb0-67bc-4083-bb80-aff64958f72f', '3e0d95a5-6e13-4ba8-b3bd-f043e207c7c7', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', 'a17db92d-2f5d-45a6-afbc-3345ffa8ce54')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('5ec8e93d-131b-48b0-8cb8-4fceda670dae', 'df434980-f7f8-42d8-ae84-049aba50b4f6', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', '8b25483a-d9ad-4f57-b2a2-537563f9f701')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('ec09da61-02a0-4a63-8b80-f16b1d6b2fe3', '9b8ba29d-3d55-4524-bdc9-bdd61b64a9a9', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', 'd2face7c-1674-42ec-aa6f-d155c755b54e')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('e68ee91c-14fb-4f4a-8063-be35650c2181', '02c0a492-235e-42d2-9240-0e7344bad6c9', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', '4fe98b12-7754-4753-878c-570aeb054c41')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('07aa024b-806c-4901-ba94-1952c81ac603', 'd16d662e-7496-437c-83e4-641cd632c8d9', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', '1c684cba-1fb6-415c-8968-2bb8e1670a44')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('bdbb4b86-9e1b-4c9c-b229-1970e43fab48', 'aaccad41-3eda-4335-9bbb-3ae99f453ca1', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', '19894f26-7e85-437f-bb68-0383bec4649b')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('65180a99-a887-40c6-9c0e-f86c8a0e686a', '48635a34-6ae5-4316-a720-8105ef0b8235', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm1', '807714f5-1d2b-43b4-8ae4-f765d97d0935')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('9abb8e0a-8a44-407c-8266-8749ea06174c', '6d1f9f34-a228-40c8-8bd9-fa171df63313', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', 'd2af6eb6-7040-4260-a5db-e96d25c161ea')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('067629b1-ad52-46aa-88d2-60626a52d57d', 'c4ce6a91-224e-48b1-8036-d0fd42fe82ad', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', '00723874-e6ba-4067-b923-f30582f0f53f')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('58375650-dad6-4281-8277-e062e5c91078', '6fef89bf-61d2-4c39-adb9-68f1911700a5', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', 'f4edde9e-cf0c-4bea-991a-f3b3a8b5d8a2')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('98bfcd2d-ea08-4f6a-b0ed-03628504739e', 'aa0ee4fd-3c41-4968-b3dd-55175f7611ea', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', '7db01a6f-5208-41df-b7a2-bcf1d5d58dee')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('a869db29-b902-4b3f-9054-abb1f9c51dc9', '1916bb23-b41f-460d-887c-6cd5aaea3367', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', 'bc26c0eb-7111-46d0-831c-f543e1e3d2a0')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('56e6a249-ed31-4ed3-8416-2c8bc17ab9b0', 'fd3ac347-c3be-44fa-8b1a-27b71e48af8c', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', '9b79a299-4d02-48db-859b-ed484b0f6403')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('e6c29b26-723d-4fe6-a951-6bb76c76516a', '1a52381b-1846-4195-afb5-f4f1d1afb203', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2012CETerm2', '612f6ba8-5740-4d85-a961-4fc2833e64eb')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('72220579-6290-48f0-8dc0-4318882d4cdc', 'bfa2446a-0d8a-4f0a-b008-fa18a8cbfb27', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', 'c1ba2b7d-298d-420e-adf2-928ade1fdb9e')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('0c80bf42-bd6d-4ad9-b158-10ea050e3c46', '1207eea8-4a80-4eae-9282-d5a6006a702a', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', 'baafb7c2-40d5-449f-ad77-da1a8ee86d22')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('45de08eb-f327-4071-a362-630a1953b67e', 'da9ee100-01dc-4c87-8ba7-d52cffe97c00', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', 'a89ceb53-2290-4bc1-9361-cf07968616d6')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('7166b40e-2264-408a-845f-598baf245a40', '570a8e82-da9c-45e7-8e05-346e1ea84c20', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', '87f2a439-ba42-4bcb-8df8-a4251ca3caae')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('53fabae9-9d6c-4c35-b39c-cf70c056fb60', 'fc5d0119-675d-4fd3-9afc-b028516f6ec4', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', '8484d150-fa93-4d5b-9803-2a01a077c70a')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('d0c0e048-50bb-4c2c-9a48-90e93eb1c014', 'fec7ddf9-4b10-46c4-8816-ca376d933c79', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', '822e5465-2567-467d-9e53-e4bd94a43ffb')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('b088cb30-812b-4410-9a95-1b9fce5d0c4c', '3bfa326f-b196-4ace-a77f-73632985245f', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm3', '9daba2ad-833d-427b-a3a7-36b3a0c91f7d')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('5064a489-3660-4553-99e3-8165c73f8157', '82916845-e0e4-4f28-8087-5b80176db273', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', '1e3c63c6-3d9f-4ee9-a1b8-556c96c662c5')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('ae27bf9a-942e-43c2-97e9-a0cb6301dcb7', '23cf5fe7-2100-48dd-8c0b-f1f7589a26f0', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', 'a588be76-f8a3-421e-aaee-af0dbece43c8')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('48d0d078-4770-45c3-bf20-c8009747d5b1', '48b26abf-78df-4738-b6a2-5990f3b9ed31', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', '9a3d4b8e-6e75-4310-8f16-c3879931c86d')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('68f4bd2c-e8b6-4420-9cab-5495976a83b3', 'da73ceea-4c08-44f2-95db-e119a4739068', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', '5df95af9-5f6a-40ae-82ac-e59022e91f8d')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('331bed2a-f29c-4fa4-ba7b-4e595bee4734', '943a880a-6c90-4305-ba83-407ade87be43', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', '7a7a1f47-4804-45f4-9b61-c98adb80e931')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('fd7f2879-dff2-47e0-b422-276a7054ef40', 'add78a6a-0e4c-4d2d-853c-ab25890c3810', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', 'b46b527b-47b7-4867-881e-7b5cdc3602dc')
/
insert into KSEN_ATPMSTONE_RELTN (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID) values ('6cb71a3d-5c2f-42a9-b4cb-e7098e26e838', '9d0122cd-54d1-4b2f-86cc-221ad2c44925', 0, 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'batchjob', TIMESTAMP '2013-05-02 05:13:33', 'kuali.atp.2013CETerm4', 'bb8269f2-7dde-42a5-94ca-a55715354d60')
/

--Tie CE Terms to CE ACAL
insert into KSEN_ATPATP_RELTN (ID, OBJ_ID, ATP_TYPE, ATP_STATE, ATP_ID, RELATED_ATP_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('1b8f7310-9b1b-4ba5-9b6b-b90d94e5c733', 'edd52ffc-e645-4474-b7e1-9781af817781', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.state.active', 'b58c25aa-5d7f-4c85-academic-calendar', 'kuali.atp.2012CETerm1', TIMESTAMP '2013-02-02 05:13:18', null, 0, TIMESTAMP '2013-05-02 05:12:50', 'batchjob', TIMESTAMP '2013-05-02 05:12:50', 'batchjob')
/
insert into KSEN_ATPATP_RELTN (ID, OBJ_ID, ATP_TYPE, ATP_STATE, ATP_ID, RELATED_ATP_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('145181f4-0e6a-4b30-a593-dc65ec20827f', 'fecfa52d-afec-430a-8db0-c11c73008faa', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.state.active', 'b58c25aa-5d7f-4c85-academic-calendar', 'kuali.atp.2012CETerm2', TIMESTAMP '2013-02-02 05:13:18', null, 0, TIMESTAMP '2013-05-02 05:12:50', 'batchjob', TIMESTAMP '2013-05-02 05:12:50', 'batchjob')
/
insert into KSEN_ATPATP_RELTN (ID, OBJ_ID, ATP_TYPE, ATP_STATE, ATP_ID, RELATED_ATP_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('f1a30c5e-fbbc-4275-95fb-37e22c3bff83', 'f701ed07-f968-4257-bef9-20a32b98b3ec', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.state.active', 'b58c25aa-5d7f-4c85-academic-calendar', 'kuali.atp.2013CETerm3', TIMESTAMP '2013-02-02 05:13:18', null, 0, TIMESTAMP '2013-05-02 05:12:50', 'batchjob', TIMESTAMP '2013-05-02 05:12:50', 'batchjob')
/
insert into KSEN_ATPATP_RELTN (ID, OBJ_ID, ATP_TYPE, ATP_STATE, ATP_ID, RELATED_ATP_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('a965c892-5b6c-4558-a7f4-f1beb34c77a1', '8914aeb0-901c-4d8f-843a-3c337cf5fd2f', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.state.active', 'b58c25aa-5d7f-4c85-academic-calendar', 'kuali.atp.2013CETerm4', TIMESTAMP '2013-02-02 05:13:18', null, 0, TIMESTAMP '2013-05-02 05:12:50', 'batchjob', TIMESTAMP '2013-05-02 05:12:50', 'batchjob')
/

