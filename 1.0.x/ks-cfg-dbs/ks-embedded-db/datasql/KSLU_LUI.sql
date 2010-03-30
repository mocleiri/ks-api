TRUNCATE TABLE KSLU_LUI DROP STORAGE
/
INSERT INTO KSLU_LUI (ATP_ID,CLU_ID,EFF_DT,EXP_DT,ID,LUI_CODE,MAX_SEATS,ST,VERSIONIND)
  VALUES ('ATP-1','CLU-1',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000601000000', 'YYYYMMDDHH24MISS' ),'LUI-1','MENG 329 section 101',50,'Approved',1)
/
INSERT INTO KSLU_LUI (ATP_ID,CLU_ID,EFF_DT,EXP_DT,ID,LUI_CODE,MAX_SEATS,ST,VERSIONIND)
  VALUES ('ATP-2','CLU-1',TO_DATE( '20000825000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'LUI-2','MENG 329 section 102',75,'Approved',1)
/
INSERT INTO KSLU_LUI (ATP_ID,CLU_ID,EFF_DT,EXP_DT,ID,LUI_CODE,MAX_SEATS,ST,VERSIONIND)
  VALUES ('ATP-2','CLU-1',TO_DATE( '20000825000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'LUI-3','MENG 329 section 112',75,'activated',1)
/
INSERT INTO KSLU_LUI (ATP_ID,CLU_ID,EFF_DT,EXP_DT,ID,LUI_CODE,MAX_SEATS,ST,VERSIONIND)
  VALUES ('ATP-1','CLU-2',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000601000000', 'YYYYMMDDHH24MISS' ),'LUI-4','BENG 471 section 101',75,'Retired',1)
/
