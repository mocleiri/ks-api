
-- clean up ksb registry table
DELETE FROM KRSB_SVC_DEF_T;

-- create principal for KS system user
INSERT INTO KRIM_ENTITY_T (ACTV_IND,ENTITY_ID,OBJ_ID,VER_NBR)
  VALUES ('Y','5','VV1B6B919CC96496E0404F8189D822F2',1);
INSERT INTO KRIM_PRNCPL_T (ACTV_IND,ENTITY_ID,OBJ_ID,PRNCPL_ID,PRNCPL_NM,VER_NBR)
  VALUES ('Y','5','5B1ZZB919CCA6496E0404F8189D822F2','5','ks',1);
INSERT INTO KRIM_ENTITY_ENT_TYP_T (ACTV_IND,ENTITY_ID,ENT_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','5','SYSTEM','5B1B6B919CCB6VV6E040ZZ8189D822F2',1);

-- create namespaces for lookups
INSERT INTO KRNS_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_NMSPC_CD)
  VALUES ('KS-LUM', 'F102F3FA08CF45CFAA404FBB89D831AA', 1, 'Kuali Student Learning Unit Management', 'Y', null);
INSERT INTO KRNS_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_NMSPC_CD)
  VALUES ('KS-SYS', 'G102F3FA08CF45CFAA404FBB89D831AA', 1, 'Kuali Student System', 'Y', null);

-- insert kim responsibility for 'Resolve Exception' responsibility template
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('1','KS-SYS','Resolve Exception','Y','2',0,'Responsibility for Kuali Student Exception Routing','5ADFE1V2441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('1','KualiStudentDocument','13','54','5G4F09744G28EF33E0404F8189AAAF24','1',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','BC27A267EF607417E0404F8189DAA0A9','1','63','1',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('1','A102F3FA08CF45CFAA404FBB89D831AA',1,'A','F','*','1','Y');

-- workflow module kim permissions
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5A4F0974494BEAA3E0404F8189D84F24','20','3',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F0974494CEF33E04AAF8189D84F24','21','4',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F0974494DEF33E0404F8189D8AA24','22','15',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5BRF0974494DEF33E0404F8189D8AA24','23','9',1);
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1E','3','13','74','20','KualiStudentDocument');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1F','3','13','75','21','KualiStudentDocument');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1G','3','13','76','22','KualiStudentDocument');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAR02E0404F8189D86F1G','5','13','77','23','KualiStudentDocument');
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AB','20','63','750',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AC','21','63','751',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AD','22','63','752',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8R89D830AD','23','1','753',1);

-- rice system module kim permissions
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F0974494BEF33E0404XX189D8AA24','24','10',1);
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1H','3','13','78','24','KualiStudentDocument');
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7O17E0404F8189OO30AA','24','63','754',1);

-- kim module kim permissions
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) 
  VALUES ('306', '6B14CC58CF58B7B5E0404F8189D84439', 1, '27', 'KR-SYS', 'Full Unmask Field', null, 'Y');
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NM, DESC_TXT, ACTV_IND, NMSPC_CD) 
  VALUES ('307', '638DDCC953F9BCD5E0404F8189D86240', 1, '1', 'Modify Entity', null, 'Y', 'KR-IDM');
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F097X494BEF33E0404XX189D8AA24','25','35',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F09XX494BEF33E0404XX189D8AA24','26','36',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F09744XXBEF33E0404XX189D8AA24','27','37',1);
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F0B74494BEF33XX404XX189D8AA24','28','38',1);
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1D','20','4','70','25','KS*');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1C','20','4','71','26','KS*');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1B','20','4','72','27','KS*');
INSERT INTO KRIM_PERM_ATTR_DATA_T (VER_NBR,OBJ_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_DATA_ID,PERM_ID,ATTR_VAL)
   VALUES (1,'5C7D9976406BAA02E0404F8189D86F1A','20','4','73','28','KS*');
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) 
  VALUES ('79', '6314CC5BCF59B7B5E0404F8189D84439', 1, '306', '11', '5', 'IdentityManagementPersonDocument');
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) 
  VALUES ('80', '6314BC58CF5AB7B5E0404F8189D84439', 1, '306', '11', '6', 'taxId');
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) 
  VALUES ('755', '638DD46CC3F9BCD5E0404F8189D86240', 1, '63', '307', 'Y');
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AE','25','63','756',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AF','26','63','757',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AG','27','63','758',1);
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AH','28','63','759',1);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) 
  VALUES ('760', '6102F3FA0GCF45CFAA404FBB89D831AA', 1, '63', '306', 'Y');

-- insert kim type for 'Department'
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,APPL_URL,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR) 
  VALUES ('Y','${application.url}','org.kuali.rice.student.bo.KualiStudentKimAttributes','100','department','Department','KS-LUM','5ADAA8B6D4AA7954E0404F8189D85002',1);
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y','100','Department Type','KS-LUM','5ADF18B6ACACA954E040AA8189D85002','kimRoleTypeService',1);
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y','100','200','100','5C7D9976406BAA02E0404F8189D86F11','a',1);

-- insert kim role for 'Department' responsibility to use
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR) 
  VALUES ('Y','100','KS-LUM','6102F3FA08CF45CFAA404F8189D831AA','900','Department Committee Reviewer',1);

-- insert kim role members into 'Department Committee Reviewer' role
INSERT INTO KRIM_ROLE_MBR_T (MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) 
  VALUES ('user1','P','5B4AA21E43857717E0404F8189D821F7','900','1290',1);
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (VER_NBR,OBJ_ID,ATTR_DATA_ID,KIM_TYP_ID,ROLE_MBR_ID,KIM_ATTR_DEFN_ID,ATTR_VAL) 
  VALUES (1,'5B4AA21E43857717E0404AA189DAA1F7','5','100','1290','100','English');
INSERT INTO KRIM_ROLE_MBR_T (MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) 
  VALUES ('user2','P','5BAA421E43857717E0404F8189D821F7','900','1291',1);
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (VER_NBR,OBJ_ID,ATTR_DATA_ID,KIM_TYP_ID,ROLE_MBR_ID,KIM_ATTR_DEFN_ID,ATTR_VAL) 
  VALUES (1,'5B4AA21E4385AA17E0404AA189DAA1F7','6','100','1291','100','Music');

-- insert kim responsibility for 'Department Committee Review' route node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('2','KS-LUM','Review','Y','1',0,'Responsibility for Department Review','5ADFE172441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('2','CluDocument','13','7','5B4F09744A28EF33E0404F8189AAAF24','2',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('3','Department Committee Review','16','7','5BAA09744A28EF33E0404F8189AAAF24','2',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('4','false','41','7','5B4F097AAA28EF33E0404F8189AAAF24','2',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('5','false','40','7','5B4F09744A28AA33E0404F8189AAAF24','2',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','5C27A267EF607417E0404F8189DAA0A9','2','900','4',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('3','6102F3FA08CF45CFAA404FBB89D831AA',1,'A','F','*','4','Y');

-- insert kim type for 'College'
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,APPL_URL,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR) 
  VALUES ('Y','${application.url}','org.kuali.rice.student.bo.KualiStudentKimAttributes','101','college','College','KS-LUM','5ADCA8B6D4AA7954E0404F8189D85002',1);
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y','101','College Type','KS-LUM','5ADF18B6ACACA95CE040AA8189D85002','kimRoleTypeService',1);
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y','101','201','101','5C7D9976C06BAA02E0404F8189D86F11','a',1);

-- insert kim role for 'College' responsibility to use
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR) 
  VALUES ('Y','101','KS-LUM','6102F3RA08CF45CFAA404F8189D831AA','901','College Committee Reviewer',1);

-- insert kim role members into 'College Committee Reviewer' role
INSERT INTO KRIM_ROLE_MBR_T (MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) 
  VALUES ('fred','P','5BAA421E43857717E04AAF8189D821F7','901','1292',1);
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (VER_NBR,OBJ_ID,ATTR_DATA_ID,KIM_TYP_ID,ROLE_MBR_ID,KIM_ATTR_DEFN_ID,ATTR_VAL) 
  VALUES (1,'5B4AA21CV385AA17E0404AA189DAA1F7','7','101','1292','101','CollegeArtsHum');

-- insert kim responsibility for 'College' route node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('3','KS-LUM','Review','Y','1',0,'Responsibility for College Committee Review','5ADFE1C2441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('6','CluDocument','13','7','5B4F0974GG28EF33E0404F8189AAAF24','3',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('7','College Committee Review','16','7','5BAG09744GG8EF33E0404F8189AAAF24','3',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('8','false','41','7','5B4F097AAA28EF33E0404F81GGAAAF24','3',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('9','false','40','7','5B4F09744A28GA33E0404F818GGGAF24','3',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','5C27A267EF607417E0404FG189DAA0A9','3','901','5',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('4','6102F3FA08CF45GFAA404FBB89D831AA',1,'A','F','*','5','Y');

-- insert kim type for 'Org Admin'
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y','102','Org Admin Type','KS-SYS','ABCD18BQQACA95CE040AA8189D85002','ksOrgAdminRoleTypeService',1);

-- insert kim role for 'Org Admin' derived role
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR) 
  VALUES ('Y','102','KS-SYS','ABCDF3QQQQCF45CFAA404F8189D831AA','902','Organization Administrator',1);  
  
-- insert kim responsibility for 'Department Admin' route node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('4','KS-LUM','Review','Y','1',0,'Responsibility for Department Admin Review','AAAAE1C2441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('10','CluDocument','13','7','AAAA09QQGG28EF33E0404F8189AAAF24','4',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('11','Department Admin Review','16','7','AAAA09744GQQEF33E0404F8189AAAF24','4',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('12','false','41','7','AAAA097AAA28EF33E0404F8QQQAAAF24','4',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('13','false','40','7','AAAA09744QQ8GA33E0404F818GGGAF24','4',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','AAAAA267EF607Q17E0404FG189DAA0A9','4','902','6',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('5','AAAAF3QQ08CF45GFAA404FBB89D831AA',1,'A','F','*','6','Y');
  
-- insert kim responsibility for 'Senate Admin' route node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('5','KS-LUM','Review','Y','1',0,'Responsibility for Senate Admin Review','BBBQQC2441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('14','CluDocument','13','7','BBBB0974GGQQEF33E0404F8189AAAF24','5',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('15','Senate Admin Review','16','7','BBBB09744GG8EF33EQQ04F8189AAAF24','5',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('16','false','41','7','BBBB097AAA28EF33E0404F81GGAAAF24','5',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('17','false','40','7','BBBB09744A28GA33E0404F818GGGAF24','5',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','BBSBA267EF607417E0404FG189DQQ0A9','5','902','7',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('6','SBBBF3FA08CF45GFAA404QQQ89D831AA',1,'A','F','*','7','Y');
  
  
-- insert kim type for 'Senate Committee'
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,APPL_URL,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR) 
  VALUES ('Y','${application.url}','org.kuali.rice.student.bo.KualiStudentKimAttributes','102','senate','Senate','KS-LUM','QBCCA8B6D4AA7954E0404F8189D85002',1);
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y','103','Senate Committee Type','KS-LUM','ABCC18B6QQACA95CE040AA8189D85002','kimRoleTypeService',1);
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y','102','202','103','ABCC9976C06QQA02E0404F8189D86F11','a',1);

-- insert kim role for 'Senate Committee' responsibility to use
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR) 
  VALUES ('Y','103','KS-LUM','ABCCF3RA08CF45CFAA404F8189D831AA','903','Senate Committee Reviewer',1);

-- insert kim responsibility for 'Senate Committee' route node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('6','KS-LUM','Review','Y','1',0,'Responsibility for College Committee Review','ABCCE1C2441D6320E04AAAA189D85169');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('18','CluDocument','13','7','ABCC0974GG28EF33E0404F8189AAAF24','6',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('19','Senate Committee Review','16','7','ABCC09744GG8EF33E0404F8189AAAF24','6',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('20','false','41','7','ABCC097AAA28EF33E0404F81GGAAAF24','6',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('21','false','40','7','ABCC09744A28GA33E0404F818GGGAF24','6',1);
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','ABCCA267EF607417E0404FG189DAA0A9','6','903','8',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('7','ABCCF3FA08CF45GFAA404FBB89D831AA',1,'A','F','*','8','Y');  

-- insert kim role for 'Department' responsibility to use
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR) 
  VALUES ('Y','1','KS-SYS','6102FOOA08CF45CFAA404F8189D831AA','904','System User',1);
INSERT INTO KRIM_ROLE_MBR_T (MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) 
  VALUES ('5','P','5BAA421E43857717E04AAF818YY821F7','904','1293',1);

-- insert permission for 'Administer Routing for Document' for all KS doc types
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','KS-SYS','5B4F0974494PEF33E0404P8189D84F24','29','3',1);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('81','KualiStudentDocument','13','3','5B4WWW744A31EF33E0404F8189D84F24','29',1);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) 
  VALUES ('761', '638DEE6CC3F9BCD5E0404F8189D86240', 1, '904', '29', 'Y');
