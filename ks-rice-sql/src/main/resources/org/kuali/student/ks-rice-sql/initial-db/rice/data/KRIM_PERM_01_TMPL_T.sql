TRUNCATE TABLE KRIM_PERM_TMPL_T DROP STORAGE
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '1', 'Default', 'KUALI', '5ADF18B6D4857954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Initiate Document', 'KR-SYS', '5ADF18B6D4BF7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'View/Maintain Agenda', '67', 'KRMS Agenda Permission', 'KR-RULE', 'D2F07FCB2CEDFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '10001', 'Field Access', 'KS-SYS', 'DCBA154A16824CA4B4575E7501F213D7', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Used to create KS screen permissions', '10004', 'Use Screen', 'KS-SYS', 'B1B7AF348A774BBAB697DC96484024E4', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Used to define what actions a user can assign to collaborators', '1', 'Add Collaborator Action', 'KS-SYS', '2DB829B5913844329EE3807EEDF775A1', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '10017', 'Open Document', 'KS-SYS', '7F4307DC63294E1395A1F3BA0224A19B', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'View/Maintain Agenda', '67', 'KRMS Agenda Permission', 'KS-SYS', 'D2F07FCB2D6EFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '120', 'Access Permission', 'KS-SYS', 'AC27A267ET5CAA7E0404F81EEOO30AA', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '121', 'Section Maintenance', 'KS-SYS', 'AC27A267ET5CAA7E0404F81EE3O30AA', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '131', 'Open View', 'KR-KRAD', 'D2F07FCB2D20FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '131', 'Edit View', 'KR-KRAD', 'D2F07FCB2D21FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '132', 'Use View', 'KR-KRAD', 'D2F07FCB2D22FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '133', 'View Field', 'KR-KRAD', 'D2F07FCB2D23FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '133', 'Edit Field', 'KR-KRAD', 'D2F07FCB2D24FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '134', 'View Group', 'KR-KRAD', 'D2F07FCB2D25FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '134', 'Edit Group', 'KR-KRAD', 'D2F07FCB2D26FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '135', 'View Widget', 'KR-KRAD', 'D2F07FCB2D27FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '135', 'Edit Widget', 'KR-KRAD', 'D2F07FCB2D28FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '136', 'Perform Action', 'KR-KRAD', 'D2F07FCB2D29FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '134', 'View Line', 'KR-KRAD', 'D2F07FCB2D2AFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '134', 'Edit Line', 'KR-KRAD', 'D2F07FCB2D2BFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '137', 'View Line Field', 'KR-KRAD', 'D2F07FCB2D2CFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '137', 'Edit Line Field', 'KR-KRAD', 'D2F07FCB2D2DFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '138', 'Perform Line Action', 'KR-KRAD', 'D2F07FCB2D2EFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Recall Document', 'KR-WKFLW', 'D2F07FCB2D31FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Cancel Document', 'KR-WKFLW', '5ADF18B6D4CA7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Save Document', 'KR-WKFLW', '5ADF18B6D4CB7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Edit Document', 'KR-NS', '5ADF18B6D4CC7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Copy Document', 'KR-NS', '5ADF18B6D4AF7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '10', 'Look Up Records', 'KR-NS', '5ADF18B6D4DA7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '10', 'Inquire Into Records', 'KR-NS', '5ADF18B6D4DB7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '11', 'View Inquiry or Maintenance Document Field', 'KR-NS', '5ADF18B6D4DF7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '11', 'Modify Maintenance Document Field', 'KR-NS', '5ADF18B6D4E07954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '11', 'Full Unmask Field', 'KR-NS', '5ADF18B6D4E17954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '11', 'Partial Unmask Field', 'KR-NS', '5ADF18B6D4E27954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '12', 'Use Screen', 'KR-NS', '5ADF18B6D4E67954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Administer Routing for Document', 'KR-WKFLW', '5ADF18B6D4B07954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '13', 'Perform Custom Maintenance Document Function', 'KR-NS', '5ADF18B6D4E97954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '14', 'Use Transactional Document', 'KR-NS', '5ADF18B6D4EC7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '15', 'Modify Batch Job', 'KR-NS', '5ADF18B6D4F07954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '15', 'Upload Batch Input File(s)', 'KR-NS', '5ADF18B6D4F17954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '16', 'Maintain System Parameter', 'KR-NS', '5ADF18B6D4F67954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '18', 'Assign Role', 'KR-IDM', '5ADF18B6D4FC7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '19', 'Grant Permission', 'KR-IDM', '5ADF18B6D5007954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '20', 'Grant Responsibility', 'KR-IDM', '5ADF18B6D5047954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '21', 'Populate Group', 'KR-IDM', '5ADF18B6D5087954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Blanket Approve Document', 'KR-WKFLW', '5ADF18B6D4B17954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Open Document', 'KR-NS', '5ADF18B6D4AE7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '56', 'Create / Maintain Record(s)', 'KR-NS', '603B735FA6C4FE21E0404F8189D8083B', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '57', 'View Inquiry or Maintenance Document Section', 'KR-NS', '603B735FA6C0FE21E0404F8189D8083B', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '57', 'Modify Maintenance Document Section', 'KR-NS', '603B735FA6C1FE21E0404F8189D8083B', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '9', 'Add Note / Attachment', 'KR-NS', '606763510FC096D3E0404F8189D857A2', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '9', 'View Note / Attachment', 'KR-NS', '606763510FC196D3E0404F8189D857A2', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '59', 'Delete Note / Attachment', 'KR-NS', '606763510FC296D3E0404F8189D857A2', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '5', 'Send Ad Hoc Request', 'KR-NS', '662384B381B867A1E0404F8189D868A6', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Route Document', 'KR-WKFLW', '5ADF18B6D4B27954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Add Message to Route Log', 'KR-WKFLW', '430ad531-89e4-11df-98b1-1300c9ee50c1', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '104', 'Comment on Document', 'KS-SYS', '5ADF18B6D4857954W0404F818SD85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '1', 'Edit Document', 'KS-SYS', '5ADF18B6D4857S54EW404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '104', 'Upload to Document', 'KS-SYS', '50DF1801D4857954W0404F818SD85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '1', 'Add Adhoc Reviewer', 'KS-SYS', '50DF1801D4857954W0404F818SD85033', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Withdraw Document', 'KS-SYS', 'A0DF1801D4857954W0404F818SD85033', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '3', 'Remove Reviewers', 'KS-SYS', 'A0DF1801D4857954W0404F818SDA5033', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '8', 'Blanket Approve', 'KS-SYS', 'A0DF1801D4857954W0404F818SDB5033', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '4', 'Take Requested Action', 'KR-NS', '5ADF18B6D4B77954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, '5', 'Ad Hoc Review Document', 'KR-WKFLW', '5ADF18B6D4BB7954E0404F8189D85002', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'KR1000', 'Super User Approve Single Action Request', 'KR-WKFLW', 'D2F07FCB2D64FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'KR1000', 'Super User Approve Document', 'KR-WKFLW', 'D2F07FCB2D65FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'KR1000', 'Super User Disapprove Document', 'KR-WKFLW', 'D2F07FCB2D66FFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Backdoor Restriction', 'KR1001', 'Backdoor Restriction', 'KR-SYS', 'D2F07FCB2D6AFFC9E040760A4A45207E', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 1)
/
