DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	SOC_TYPE             VARCHAR2(255) NOT NULL ,
	SOC_STATE            VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	TERM_ID              VARCHAR2(255) NOT NULL ,
	SUBJECT_AREA         VARCHAR2(255) NULL ,
	UNITS_CONTENT_OWNER_ID VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_P ON KSEN_SOC
(ID   ASC)
/


ALTER TABLE KSEN_SOC
	ADD CONSTRAINT  KSEN_SOC_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ATTR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(255) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ATTR_P ON KSEN_SOC_ATTR
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ATTR
	ADD CONSTRAINT  KSEN_SOC_ATTR_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ATTR_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ROR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ROR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ROR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	SOC_ROR_TYPE         VARCHAR2(255) NOT NULL ,
	SOC_ROR_STATE        VARCHAR2(255) NOT NULL ,
	TARGET_TERM_ID       VARCHAR2(255) NOT NULL ,
	ITEMS_PROCESSED      NUMBER NULL ,
	ITEMS_EXPECTED       NUMBER NULL ,
	SOURCE_SOC_ID        VARCHAR2(255) NOT NULL ,
	TARGET_SOC_ID        VARCHAR2(255) NOT NULL ,
	MESG_PLAIN           VARCHAR2(4000) NULL ,
	MESG_FORMATTED       VARCHAR2(4000) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ROR_P ON KSEN_SOC_ROR
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ROR
	ADD CONSTRAINT  KSEN_SOC_ROR_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ROR_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ROR_ATTR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ROR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ROR_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(255) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ROR_ATTR_P ON KSEN_SOC_ROR_ATTR
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ROR_ATTR
	ADD CONSTRAINT  KSEN_SOC_ROR_ATTR_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ROR_ATTR_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ROR_ITEM';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ROR_ITEM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ROR_ITEM
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	SOC_ROR_TYPE         VARCHAR2(255) NOT NULL ,
	SOC_ROR_STATE        VARCHAR2(255) NOT NULL ,
	SOURCE_CO_ID         VARCHAR2(255) NOT NULL ,
	TARGET_CO_ID         VARCHAR2(255) NULL ,
	ROR_ID               VARCHAR2(255) NULL ,
	MESG_PLAIN           VARCHAR2(4000) NULL ,
	MESG_FORMATTED       VARCHAR2(4000) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ROR_ITEM_P ON KSEN_SOC_ROR_ITEM
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ROR_ITEM
	ADD CONSTRAINT  KSEN_SOC_ROR_ITEM_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ROR_ITEM_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ROR_ITEM_ATTR';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ROR_ITEM_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ROR_ITEM_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(255) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ROR_ITEM_ATTR_P ON KSEN_SOC_ROR_ITEM_ATTR
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ROR_ITEM_ATTR
	ADD CONSTRAINT  KSEN_SOC_ROR_ITEM_ATTR_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ROR_ITEM_ATTR_P
/



DECLARE TEMP NUMBER;
BEGIN
  SELECT COUNT(*) INTO TEMP FROM USER_TABLES WHERE TABLE_NAME = 'KSEN_SOC_ROR_OPTION';
	IF TEMP > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_SOC_ROR_OPTION CASCADE CONSTRAINTS PURGE'; END IF;
END;
/




CREATE TABLE KSEN_SOC_ROR_OPTION
(
	ID                   VARCHAR2(255) NOT NULL ,
	OPTION_ID            VARCHAR2(255) NOT NULL ,
	ROR_ID               VARCHAR2(255) NOT NULL 
)
/


CREATE UNIQUE INDEX KSEN_SOC_ROR_OPTION_P ON KSEN_SOC_ROR_OPTION
(ID   ASC)
/


ALTER TABLE KSEN_SOC_ROR_OPTION
	ADD CONSTRAINT  KSEN_SOC_ROR_OPTION_P PRIMARY KEY (ID)   USING INDEX KSEN_SOC_ROR_OPTION_P
/



