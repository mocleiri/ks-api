TRUNCATE TABLE KSLU_CLU_LO_RELTN_TYPE DROP STORAGE
/
INSERT INTO KSLU_CLU_LO_RELTN_TYPE (EFF_DT,EXPIR_DT,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20100101000000', 'YYYYMMDDHH24MISS' ),'Default Clu-Lo relation type','cluLuType.default')
/
