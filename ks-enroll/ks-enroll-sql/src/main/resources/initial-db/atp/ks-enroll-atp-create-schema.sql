-----------------------------------------------------------------------------
-- DROP all the tables
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPATP_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPATP_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPATP_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPATP_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPMSTONE_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPMSTONE_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_MSTONE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_MSTONE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_MSTONE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_MSTONE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
-----------------------------------------------------------------------------
-- DROP all the tables
-----------------------------------------------------------------------------
CREATE TABLE KSEN_ATP
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATP_TYPE             VARCHAR2(255) NOT NULL ,
	ATP_STATE            VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	ATP_CD               VARCHAR2(255) NULL ,
	END_DT               TIMESTAMP(6) NOT NULL ,
	START_DT             TIMESTAMP(6) NOT NULL ,
	ADMIN_ORG_ID         VARCHAR2(50) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_ATP_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_ATPATP_RELTN
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATP_TYPE             VARCHAR2(255) NOT NULL ,
	ATP_STATE            VARCHAR2(255) NOT NULL ,
	ATP_ID               VARCHAR2(255) NOT NULL ,
	RELATED_ATP_ID       VARCHAR2(255) NOT NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_ATPATP_RELTN_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_ATPMSTONE_RELTN
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	ATP_ID               VARCHAR2(255) NULL ,
	MSTONE_ID            VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_MSTONE
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	MSTONE_TYPE          VARCHAR2(255) NOT NULL ,
	MSTONE_STATE         VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	IS_ALL_DAY           NUMBER(1) NOT NULL ,
	IS_INSTRCT_DAY       NUMBER(1) NOT NULL ,
	IS_RELATIVE          NUMBER(1) NOT NULL ,
	RELATIVE_ANCHOR_MSTONE_ID VARCHAR2(255) NULL ,
	IS_DATE_RANGE        NUMBER(1) NOT NULL ,
	START_DT             TIMESTAMP(6) NULL ,
	END_DT               TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NOT NULL ,
	CREATETIME           TIMESTAMP(6) NOT NULL ,
	CREATEID             VARCHAR2(255) NOT NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/
CREATE TABLE KSEN_MSTONE_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
)
/
