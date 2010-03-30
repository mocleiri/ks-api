TRUNCATE TABLE KRIM_ROLE_T DROP STORAGE
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the users in the Principal table. This role gives users high-level permissions to interact with RICE documents and to login to KUALI.','2',TO_DATE( '20081104143710', 'YYYYMMDDHH24MISS' ),'KUALI','5ADF18B6D4847954E0404F8189D85002','1','User',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with that have received an action request for a given document.','42',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F62A8EEE0404F8189D8770F','59','Approve Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the initiator listed within the route log of a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F63A8EEE0404F8189D8770F','60','Initiator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the initiator and action request recipients listed within the route log of a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F64A8EEE0404F8189D8770F','61','Initiator or Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role can take superuser actions and blanket approve RICE documents as well as being able to modify and assign permissions, responsibilities and roles belonging to the KR namespaces.','1',TO_DATE( '20081108115522', 'YYYYMMDDHH24MISS' ),'KR-SYS','5B31640F0105ADF1E0404F8189D84647','63','Technical Administrator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Edit Document permission for a given document type.,','45',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-NS','5BABFACC4F61A8EEE0404F8189D8770F','66','Document Editor',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the user who took the Complete action on a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F65A8EEE0404F8189D8770F','67','Router',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Open Document permission for a given document type.,','60',TO_DATE( '20090113192616', 'YYYYMMDDHH24MISS' ),'KR-NS','606763510FBF96D3E0404F8189D857A2','83','Document Opener',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an acknowledge action request in the route log of a given document.','42',TO_DATE( '20090121130202', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','6102F3FA08CE45CFE0404F8189D8317E','88','Acknowledge Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an FYI action request in the route log of a given document.','42',TO_DATE( '20090121130203', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','6102F3FA08CF45CFE0404F8189D8317E','89','FYI Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARUUUNTADMINREVIEWER00000ROLE','899','Student System User Role',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role represents the KR System User, that is the user ID RICE uses when it takes programmed actions.','1',TO_DATE( '20090821035258', 'YYYYMMDDHH24MISS' ),'KR-SYS','61815E6C62D0B647E0404F8189D873B3','90','System User',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','102',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','DEPARTMENTADMINREVIEWER00000ROLE','900','Department Admin Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','103',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','DIVISIONADMINREVIEWER0000000ROLE','901','Division Admin Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','103',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','DIVISIONCOMMITTEEREVIEWER000ROLE','902','Division Committee Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','101',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','COLLEGEADMINREVIEWER00000000ROLE','905','College Admin Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','101',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','COLLEGECOMMITTEEREVIEWER0000ROLE','906','College Committee Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','SENATEADMINREVIEWER000000000ROLE','907','Senate Admin Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','SENATECOMMITTEEREVIEWER00000ROLE','908','Senate Committee Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','PUBLICATIONREVIEWER000000000ROLE','909','Publication Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','107',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','OrgAdminReviewer000000000000ROLE','910','Org Admin Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','108',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','OrgCommitteeReviewer00000000ROLE','911','Org Committee Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','109',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTFDMINREVIEWER00000ROLE','912','Approve Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','109',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTGDMINREVIEWER0070ROLE','913','Acknowledge Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','109',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTHDMINREVIEWER00060ROLE','914','FYI Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','110',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTRDMINREVIEWER00800ROLE','915','Initiator or Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','110',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTWDMINREVIEWER00900ROLE','916','Router',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','102',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-LUM','DEPARTMENTCOMITREVIEWER00000ROLE','917','Department Committee Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','111',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEqARTMENT5DMINREVIEWER10330ROLE','918','Permission: KS-SYS Edit Document',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','112',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENT5DMIqREVIEWER13400ROLE','919','Permission: KS-SYS Comment on Document',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091216111952', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMENTWDMINREVIEWER20000ROLE','920','Global Field Editor',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20091216111952', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMANTWDMINREVIEWER20000ROLE','921','Title/Description Field Editor',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','113',TO_DATE( '20091130171545', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMEN294DMI342VIEWER050000RO','922','Adhoc Approve Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','113',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEP295ENTGDMINREVIEWER00040ROLE','923','Adhoc Acknowledge Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','113',TO_DATE( '20091130171546', 'YYYYMMDDHH24MISS' ),'KS-SYS','DEPARTMEN832MINREVIEWER00300ROE','924','Adhoc FYI Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Initiate Document permission for a given document type.','66',TO_DATE( '20090821035258', 'YYYYMMDDHH24MISS' ),'KR-SYS','67F145466E8B9160E0404F8189D86771','95','Document Initiator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an Approval action request (that was not generated via the ad-hoc recipients tab) in the route log of a given document.','42',TO_DATE( '20090821035258', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','67F145466EB09160E0404F8189D86771','97','Non-Ad Hoc Approve Request Recipient',1)
/
