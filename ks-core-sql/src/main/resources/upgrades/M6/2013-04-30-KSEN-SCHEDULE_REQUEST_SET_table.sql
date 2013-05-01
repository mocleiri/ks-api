DECLARE temp NUMBER;
BEGIN

  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_SCHED_RQST_SET';
  IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SCHED_RQST_SET CASCADE CONSTRAINTS PURGE'; END IF;

END;
/

CREATE TABLE KSEN_SCHED_RQST_SET
(
    ID VARCHAR2(255)
  , OBJ_ID VARCHAR2(36)
  , SCHED_RQST_SET_TYPE VARCHAR2(255) NOT NULL
  , SCHED_RQST_SET_STATE VARCHAR2(255) NOT NULL
  , NAME VARCHAR2(255)
  , DESCR_PLAIN VARCHAR2(4000)
  , DESCR_FORMATTED VARCHAR2(4000)
  , MAX_ENROLLMENT_Shared_IND NUMBER(1)
  , MAX_ENROLLMENT NUMBER(10)
  , REF_OBJ_TYPE VARCHAR2(255) NOT NULL
  , VER_NBR NUMBER(19) NOT NULL
  , CREATETIME TIMESTAMP NOT NULL
  , CREATEID VARCHAR2(255) NOT NULL
  , UPDATETIME TIMESTAMP
  , UPDATEID VARCHAR2(255)

)
/

DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_SCHED_RQST_SET_REF_OBJ';
  IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SCHED_RQST_SET_REF_OBJ CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_SCHED_RQST_SET_REF_OBJ
(
    REF_OBJ_ID               VARCHAR2(255) NOT NULL
  , SCHED_RQST_SET_ID             VARCHAR2(255) NOT NULL
)
/