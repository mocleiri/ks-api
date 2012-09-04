
DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_CO_REG_GRP_TMPLT';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CO_REG_GRP_TMPLT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_CO_REG_GRP_TMPLT
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(255) NULL ,
	REG_GRP_TMPLT_TYPE   VARCHAR2(255) NOT NULL ,
	REG_GRP_TMPLT_STATE  VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	FORMAT_OFFERING_ID   VARCHAR2(255) NOT NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/


ALTER TABLE KSEN_CO_REG_GRP_TMPLT
	ADD CONSTRAINT  KSEN_CO_RGT_P PRIMARY KEY (ID)
/


DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_CO_REG_GRP_TMPLT_AO';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CO_REG_GRP_TMPLT_AO CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_CO_REG_GRP_TMPLT_AO
(
	REG_GRP_TMPLT_ID     VARCHAR2(255) NOT NULL ,
	ACTIVITY_OFFERING_TMPLT_ID VARCHAR2(255) NOT NULL ,
	ACTIVITY_OFFERING_ID VARCHAR2(255) NOT NULL 
)
/


ALTER TABLE KSEN_CO_REG_GRP_TMPLT_AO
	ADD CONSTRAINT  KSEN_CO_RGT_AO_P PRIMARY KEY (REG_GRP_TMPLT_ID,ACTIVITY_OFFERING_TMPLT_ID,ACTIVITY_OFFERING_ID)
/


DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_CO_REG_GRP_TMPLT_ATTR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CO_REG_GRP_TMPLT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_CO_REG_GRP_TMPLT_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/


ALTER TABLE KSEN_CO_REG_GRP_TMPLT_ATTR
	ADD CONSTRAINT  KSEN_CO_RGT_ATTR_P PRIMARY KEY (ID)
/


DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_CO_SEAT_POOL_DEFN';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CO_SEAT_POOL_DEFN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_CO_SEAT_POOL_DEFN
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	SEAT_POOL_DEFN_TYPE  VARCHAR2(255) NOT NULL ,
	SEAT_POOL_DEFN_STATE VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	ACTIVITY_OFFERING_ID VARCHAR2(255) NULL ,
	EXPIR_MSTONE_TYPE    VARCHAR2(255) NULL ,
	PERCENTAGE_IND       NUMBER(1) NULL,
	SEAT_LIMIT           NUMBER NULL ,
	PROCESSING_PRIORITY  NUMBER NULL ,
	POPULATION_ID        VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/


ALTER TABLE KSEN_CO_SEAT_POOL_DEFN
	ADD CONSTRAINT  KSEN_CO_SPD_P PRIMARY KEY (ID)
/


DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_CO_SEAT_POOL_DEFN_ATTR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CO_SEAT_POOL_DEFN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_CO_SEAT_POOL_DEFN_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/


ALTER TABLE KSEN_CO_SEAT_POOL_DEFN_ATTR
	ADD CONSTRAINT  KSEN_CO_SPD_ATTR_P PRIMARY KEY (ID)
/
