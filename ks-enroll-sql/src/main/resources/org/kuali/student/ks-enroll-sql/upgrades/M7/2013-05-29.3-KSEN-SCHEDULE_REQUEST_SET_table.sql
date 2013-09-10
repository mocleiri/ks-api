DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SCHED_RQST_SET_';
  IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SCHED_RQST_SET  CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SCHED_RQST_SET
(
  ID                   VARCHAR2(255) NOT NULL ,
  OBJ_ID               VARCHAR2(36) NULL ,
  SCHED_RQST_SET_TYPE  VARCHAR2(255) NOT NULL ,
  SCHED_RQST_SET_STATE VARCHAR2(255) NOT NULL ,
  NAME                 VARCHAR2(255) NULL ,
  DESCR_PLAIN          VARCHAR2(4000) NULL ,
  DESCR_FORMATTED      VARCHAR2(4000) NULL ,
  REF_OBJECT_TYPE      VARCHAR2(255) NOT NULL ,
  MAX_ENRL_SHARED_IND  NUMBER NULL  CONSTRAINT  CHECK_BOOLEAN_908602616 CHECK (MAX_ENRL_SHARED_IND IN (1, 0)),
  MAX_ENRL             NUMBER NULL ,
  VER_NBR              NUMBER(19) NOT NULL ,
  CREATETIME           TIMESTAMP(6) NOT NULL ,
  CREATEID             VARCHAR2(255) NOT NULL ,
  UPDATETIME           TIMESTAMP(6) NULL ,
  UPDATEID             VARCHAR2(255) NULL
)
/


ALTER TABLE KSEN_SCHED_RQST_SET
ADD CONSTRAINT  KSEN_SCHED_RQST_SET_P PRIMARY KEY (ID)
/


DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SCHED_REF_OBJECT';
  IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SCHED_REF_OBJECT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SCHED_REF_OBJECT
(
  SCHED_RQST_SET_ID    VARCHAR2(255) NOT NULL ,
  REF_OBJECT_ID        VARCHAR2(255) NOT NULL
)
/


ALTER TABLE KSEN_SCHED_REF_OBJECT
ADD CONSTRAINT  KSEN_SCHED_REF_OBJECT_P PRIMARY KEY (SCHED_RQST_SET_ID,REF_OBJECT_ID)
/

DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SCHED_RQST_SET_ATTR';
  IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SCHED_RQST_SET_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/


CREATE TABLE KSEN_SCHED_RQST_SET_ATTR
(
  OBJ_ID               VARCHAR2(36) NULL ,
  ATTR_KEY             VARCHAR2(255) NULL ,
  ATTR_VALUE           VARCHAR2(4000) NULL ,
  ID                   VARCHAR2(255) NOT NULL ,
  OWNER_ID             VARCHAR2(255) NULL
)
/


ALTER TABLE KSEN_SCHED_RQST_SET_ATTR
ADD CONSTRAINT  KSEN_SCHED_RQST_SET_ATTR_P PRIMARY KEY (ID)
/

