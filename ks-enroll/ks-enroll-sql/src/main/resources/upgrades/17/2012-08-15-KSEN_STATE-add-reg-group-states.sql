insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.course.registration.group.lifecycle', null, 'kuali.course.registration.group.lifecycle', 'Registration Group State Lifecycle', 'Registration Group State Lifecycle', null, 1, to_date('2012-08-14', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.registration.group.state.open', null, 'Active', 'Reg Group is open', 'Reg Group is open', 'kuali.course.registration.group.lifecycle', null, null, 0, to_date('2012-08-14', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.registration.group.state.closed', null, 'Active', 'Reg Group is closed', 'Reg Group is closed', 'kuali.course.registration.group.lifecycle', null, null, 0, to_date('2012-08-14', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.registration.group.state.suspended', null, 'Active', 'Reg Group is suspended', 'Reg Group is suspended', 'kuali.course.registration.group.lifecycle', null, null, 0, to_date('2012-08-14', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.registration.group.state.invalid', null, 'Active', 'Reg Group is invalid', 'Reg Group is invalid', 'kuali.course.registration.group.lifecycle', null, null, 0, to_date('2012-08-14', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/