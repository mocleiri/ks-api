TRUNCATE TABLE KRIM_ROLE_MBR_ATTR_DATA_T DROP STORAGE
/
-- Note added by Bonnie
-- We should pull out the authz setup out of ks-rice-sql.
-- Below is the reference implementation.
-- After switching to UMD reference data, 1000ENGL is not the correct value
-- it is changed to 2677933260. I'll make the update script to fix this problem
-- under ks-enroll-sql
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, ATTR_VAL, KIM_ATTR_DEFN_ID, KIM_TYP_ID, OBJ_ID, ROLE_MBR_ID, VER_NBR)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), '1000ENGL', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'org' AND NMSPC_CD = 'KS-SYS'), '114', 'D2F07FCB2ECCFFC9E040760A4A45207E', (SELECT ROLE_MBR_ID FROM KRIM_ROLE_MBR_T WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'KS Department Schedule Coordinator - Org' AND NMSPC_CD = 'KS-ENR') AND MBR_ID = 'carol'), 1)
/
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, ATTR_VAL, KIM_ATTR_DEFN_ID, KIM_TYP_ID, OBJ_ID, ROLE_MBR_ID, VER_NBR)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'N', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'descendHierarchy' AND NMSPC_CD = 'KS-SYS'), '114', 'D2F07FCB2ECDFFC9E040760A4A45207E', (SELECT ROLE_MBR_ID FROM KRIM_ROLE_MBR_T WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'KS Department Schedule Coordinator - Org' AND NMSPC_CD = 'KS-ENR') AND MBR_ID = 'carol'), 1)
/
