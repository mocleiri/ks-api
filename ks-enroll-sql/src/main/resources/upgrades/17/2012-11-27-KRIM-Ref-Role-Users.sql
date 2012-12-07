--Create persona users in ref data
select krim_entity_id_s.nextval from dual
/
insert into KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_ID_S.NEXTVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT) values ( 'martha', SYS_GUID(), 1, 'martha', KRIM_ENTITY_ID_S.CURRVAL, 'martha', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_AFLTN_T (ENTITY_AFLTN_ID, OBJ_ID, VER_NBR, ENTITY_ID, AFLTN_TYP_CD, CAMPUS_CD, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_AFLTN_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'STAFF', 'EA', 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values ('PERSON', KRIM_ENTITY_ID_S.CURRVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_ENTITY_NM_T (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_NM_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'PRFR', 'martha', null, 'martha', null, null, 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_ID_S.NEXTVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT) values ( 'mark', SYS_GUID(), 1, 'mark', KRIM_ENTITY_ID_S.CURRVAL, 'mark', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_AFLTN_T (ENTITY_AFLTN_ID, OBJ_ID, VER_NBR, ENTITY_ID, AFLTN_TYP_CD, CAMPUS_CD, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_AFLTN_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'STAFF', 'EA', 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values ('PERSON', KRIM_ENTITY_ID_S.CURRVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_ENTITY_NM_T (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_NM_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'PRFR', 'mark', null, 'mark', null, null, 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_ID_S.NEXTVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT) values ( 'carol', SYS_GUID(), 1, 'carol', KRIM_ENTITY_ID_S.CURRVAL, 'carol', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_AFLTN_T (ENTITY_AFLTN_ID, OBJ_ID, VER_NBR, ENTITY_ID, AFLTN_TYP_CD, CAMPUS_CD, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_AFLTN_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'STAFF', 'EA', 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values ('PERSON', KRIM_ENTITY_ID_S.CURRVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_ENTITY_NM_T (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_NM_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'PRFR', 'carol', null, 'carol', null, null, 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_ID_S.NEXTVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT) values ( 'carl', SYS_GUID(), 1, 'carl', KRIM_ENTITY_ID_S.CURRVAL, 'carl', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_AFLTN_T (ENTITY_AFLTN_ID, OBJ_ID, VER_NBR, ENTITY_ID, AFLTN_TYP_CD, CAMPUS_CD, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_AFLTN_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'STAFF', 'EA', 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values ('PERSON', KRIM_ENTITY_ID_S.CURRVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_ENTITY_NM_T (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_NM_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'PRFR', 'carl', null, 'carl', null, null, 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_ID_S.NEXTVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT) values ( 'student', SYS_GUID(), 1, 'student', KRIM_ENTITY_ID_S.CURRVAL, 'student', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_AFLTN_T (ENTITY_AFLTN_ID, OBJ_ID, VER_NBR, ENTITY_ID, AFLTN_TYP_CD, CAMPUS_CD, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_AFLTN_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'STAFF', 'EA', 'Y', 'Y', SYSDATE)
/
insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT) values ('PERSON', KRIM_ENTITY_ID_S.CURRVAL, SYS_GUID(), 1, 'Y', SYSDATE)
/
insert into KRIM_ENTITY_NM_T (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT) values (KRIM_ENTITY_NM_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_ENTITY_ID_S.CURRVAL, 'PRFR', 'student', null, 'student', null, null, 'Y', 'Y', SYSDATE)
/

--Role - Member
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator ' and nmspc_cd = 'KS-ENR'), 'admin', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator ' and nmspc_cd = 'KS-ENR'), 'martha', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator ' and nmspc_cd = 'KS-ENR'), 'mark', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org ' and nmspc_cd = 'KS-ENR'), 'carol', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator View Only' and nmspc_cd = 'KS-ENR'), 'carol', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/

--Role Member Details
insert into KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,  SYS_GUID(), 1, (SELECT ROLE_MBR_ID from KRIM_ROLE_MBR_T where ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T where role_nm = 'KS Department Schedule Coordinator - Org ' and nmspc_cd = 'KS-ENR') AND MBR_ID = 'carol'), (SELECT kim_typ_id from krim_typ_t where nm = 'Organization Role Type' and nmspc_cd = 'KS-SYS'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'org' and nmspc_cd = 'KS-SYS'), '1000ENGL')
/