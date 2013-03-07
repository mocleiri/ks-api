CREATE OR REPLACE PROCEDURE "LEVEL_SEQUENCES" (TABLE_ID VARCHAR2, SEQUENCE_ID VARCHAR2,PK VARCHAR2) IS
    STATEMENT VARCHAR2(2000);
    maxid NUMBER;
    cval NUMBER;
BEGIN
    STATEMENT:= 'SELECT MAX(TO_NUMBER('||TABLE_ID||'.'||PK||')) FROM '||TABLE_ID||' WHERE '||TABLE_ID||'.'||PK||' NOT LIKE ''KR%''';
    EXECUTE IMMEDIATE (STATEMENT) INTO maxid;
    STATEMENT := 'SELECT '|| SEQUENCE_ID ||'.NEXTVAL FROM DUAL';
    EXECUTE IMMEDIATE(STATEMENT) INTO cval;
    IF cval!=maxid
    THEN
    STATEMENT := 'ALTER SEQUENCE '|| SEQUENCE_ID || ' INCREMENT BY ' || (maxid-cval);
    EXECUTE IMMEDIATE(STATEMENT);
    STATEMENT := 'SELECT '|| SEQUENCE_ID ||'.NEXTVAL FROM DUAL';
    EXECUTE IMMEDIATE(STATEMENT) INTO cval;
    STATEMENT := 'ALTER SEQUENCE '|| SEQUENCE_ID || ' INCREMENT BY 1';
    EXECUTE IMMEDIATE(STATEMENT);
    END IF;
END;
/
--The KREW_DOC_HDR_S is used by two different tables! so one might be filled out more than another
--CALL LEVEL_SEQUENCES('KREW_DOC_HDR_T','KREW_DOC_HDR_S','DOC_HDR_ID')
--/
CALL LEVEL_SEQUENCES('KREW_DOC_TYP_T','KREW_DOC_HDR_S','DOC_TYP_ID')
/
CALL LEVEL_SEQUENCES('KREW_RTE_NODE_T','KREW_RTE_NODE_S','RTE_NODE_ID')
/
CALL LEVEL_SEQUENCES('KREW_ACTN_TKN_T','KREW_ACTN_TKN_S','ACTN_TKN_ID')
/
CALL LEVEL_SEQUENCES('KREW_ACTN_ITM_T','KREW_ACTN_ITM_S','ACTN_ITM_ID')
/
CALL LEVEL_SEQUENCES('KREW_ACTN_RQST_T','KREW_ACTN_RQST_S','ACTN_RQST_ID')
/
CALL LEVEL_SEQUENCES('KREW_DOC_HDR_EXT_T','KREW_SRCH_ATTR_S','DOC_HDR_EXT_ID')
/

CALL LEVEL_SEQUENCES('KRIM_ROLE_MBR_T','KRIM_ROLE_MBR_ID_S','ROLE_MBR_ID')
/
CALL LEVEL_SEQUENCES('KRIM_PERM_ATTR_DATA_T','KRIM_ATTR_DATA_ID_S','ATTR_DATA_ID')
/
CALL LEVEL_SEQUENCES('KRIM_PERM_T','KRIM_PERM_ID_S','PERM_ID')
/
CALL LEVEL_SEQUENCES('KRIM_ROLE_PERM_T','KRIM_ROLE_PERM_ID_S','ROLE_PERM_ID')
/
CALL LEVEL_SEQUENCES('KRIM_TYP_T','KRIM_TYP_ID_S','KIM_TYP_ID')
/
CALL LEVEL_SEQUENCES('KRIM_TYP_ATTR_T','KRIM_TYP_ATTR_ID_S','KIM_TYP_ATTR_ID')
/
CALL LEVEL_SEQUENCES('KRIM_PERM_TMPL_T','KRIM_PERM_TMPL_ID_S','PERM_TMPL_ID')
/
CALL LEVEL_SEQUENCES('KRIM_ATTR_DEFN_T','KRIM_ATTR_DEFN_ID_S','KIM_ATTR_DEFN_ID')
/

