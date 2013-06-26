-- KSENROLL-7788 View permission for Holiday Calendar
-- admin view
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1084', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for Create Holiday Calendar for Admin', 'Allows the admin to Open View for Create Holiday Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1192', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for Create Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarFlowView')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1126', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Create Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- admin edit
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1085', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Edit View for Create Holiday Calendar for Admin', 'Allows the admin to Edit View for Create Holiday Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1193', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit View for Create Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarFlowView')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1127', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Edit View for Create Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- View Holiday Calendar KS Department Schedule Coordinator - Org view
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1086', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'View Group' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for View Holiday Calendar', 'Allows the user to Open View for View Holiday Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1194', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarFlowView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1195', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarViewPage')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1128', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1129', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- View Holiday Calendar Admin
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1087', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'View Group' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for View Holiday Calendar for Admin', 'Allows the admin to Open View for View Holiday Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1196', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarFlowView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1197', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'holidayCalendarViewPage')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1130', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Holiday Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/

-- KSENROLL-7789 View permission for Academic Calendar
-- admin view
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1088', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for Create Academic Calendar for Admin', 'Allows the admin to Open View for Create Academic Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1198', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for Create Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarFlowView')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1131', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Create Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- admin edit
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1089', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Edit View for Create Academic Calendar for Admin', 'Allows the admin to Edit View for Create Academic Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1199', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit View for Create Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarFlowView')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1132', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Edit View for Create Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- View Academic Calendar KS Department Schedule Coordinator - Org view
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1090', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'View Group' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for View Academic Calendar', 'Allows the user to Open View for View Academic Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1200', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarFlowView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1201', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarEditPage')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1133', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1134', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- View Academic Calendar Admin
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1091', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'View Group' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open View for View Academic Calendar for Admin', 'Allows the admin to Open View for View Academic Calendar', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1202', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarFlowView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1203', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'groupId' and nmspc_cd = 'KR-KRAD'), 'academicCalendarEditPage')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1135', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for View Academic Calendar for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/




