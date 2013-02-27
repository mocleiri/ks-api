insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Full Open View for ActivityOffering-MaintenanceView', 'Allows the user to Open View for ActivityOffering-MaintenanceView', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Full Edit View for ActivityOffering-MaintenanceView', 'Allows the user to Edit View for ActivityOffering-MaintenanceView', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Conditionally Open View for ActivityOffering-MaintenanceView based on SOC State', 'Allows the user to Open View for ActivityOffering-MaintenanceView based on SOC State', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KS-ENR'), 'KS-ENR',	'Conditionally Edit View for ActivityOffering-MaintenanceView based on SOC State', 'Allows the user to Edit View for ActivityOffering-MaintenanceView based on SOC State', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Full Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'Allows the user to Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView Unconditionally', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Full Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'Allows the user to Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView Unconditionally', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Conditionally Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State', 'Allows the user to Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Conditionally Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State', 'Allows the user to Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'View Group' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'View Group for ActivityOfferingEdit-MainPage-SeatPool', 'Allows the user to View Group for ActivityOfferingEdit-MainPage-SeatPool', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit Group' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Full Edit Group for ActivityOfferingEdit-MainPage-SeatPool', 'Allows the user to Edit Group for ActivityOfferingEdit-MainPage-SeatPool', 'Y')
/

--Permission Details
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Full Open View for ActivityOffering-MaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Full Edit View for ActivityOffering-MaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Full Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'KS-CourseOfferingEditWrapper-EditMaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Full Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'KS-CourseOfferingEditWrapper-EditMaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,	SYS_GUID(),	1, (SELECT perm_id from krim_perm_t where nm = 'Conditionally Open View for ActivityOffering-MaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),	(SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,	SYS_GUID(),	1,	(SELECT perm_id from krim_perm_t where nm = 'Conditionally Open View for ActivityOffering-MaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'),	(SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),	(SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'),	'{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,	SYS_GUID(),	1,	(SELECT perm_id from krim_perm_t where nm = 'Conditionally Edit View for ActivityOffering-MaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'),	(SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),	(SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'),	'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,	SYS_GUID(),	1,	(SELECT perm_id from krim_perm_t where nm = 'Conditionally Edit View for ActivityOffering-MaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'),	(SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),	(SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'),	'{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Conditionally Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'KS-CourseOfferingEditWrapper-EditMaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Conditionally Open View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Conditionally Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'KS-CourseOfferingEditWrapper-EditMaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Conditionally Edit View for KS-CourseOfferingEditWrapper-EditMaintenanceView based on SOC State' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'View Group for ActivityOfferingEdit-MainPage-SeatPool' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'ActivityOfferingEdit-MainPage-SeatPool')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'View Group for ActivityOfferingEdit-MainPage-SeatPool' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Group for ActivityOfferingEdit-MainPage-SeatPool' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'ActivityOfferingEdit-MainPage-SeatPool')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Group for ActivityOfferingEdit-MainPage-SeatPool' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/