TRUNCATE TABLE KSLU_CLU_PUBL DROP STORAGE
/
INSERT INTO KSLU_CLU_PUBL (ID,ST,CLU_ID,CLU_PUB_TYPE_ID,EFF_DT,VER_NBR)
  VALUES ('MAJOR-101','active','d4ea77dd-b492-4554-b104-863e42c5f8b7','kuali.lu.publication.Catalog',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),0)
/
