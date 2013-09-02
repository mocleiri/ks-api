

-----------------------------------------------------------------------------
-- KSEN_ROOM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ROOM_TYPE VARCHAR2(255) NOT NULL
        , ROOM_STATE VARCHAR2(255) NOT NULL
        , NAME VARCHAR2(255)
        , DESCR_PLAIN VARCHAR2(4000)
        , DESCR_FORMATTED VARCHAR2(4000)
        , ROOM_CD VARCHAR2(255) NOT NULL
        , BUILDING_ID VARCHAR2(255) NOT NULL
        , FLOOR VARCHAR2(255) NOT NULL
        , VER_NBR NUMBER(19) NOT NULL
        , CREATETIME TIMESTAMP NOT NULL
        , CREATEID VARCHAR2(255) NOT NULL
        , UPDATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
    
    , CONSTRAINT KSEN_ROOM_I4 UNIQUE (BUILDING_ID, ROOM_CD)

)
/

ALTER TABLE KSEN_ROOM
    ADD CONSTRAINT KSEN_ROOMP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_I1 
  ON KSEN_ROOM 
  (BUILDING_ID, FLOOR)
/
CREATE INDEX KSEN_ROOM_I2 
  ON KSEN_ROOM 
  (ROOM_TYPE)
/
CREATE INDEX KSEN_ROOM_I3 
  ON KSEN_ROOM 
  (BUILDING_ID, ROOM_TYPE)
/
CREATE INDEX KSEN_ROOM_IF1 
  ON KSEN_ROOM 
  (BUILDING_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_ACCESS_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_ACCESS_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_ACCESS_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_ACCESS_TYPE
(
      ID VARCHAR2(255)
        , ROOM_ID VARCHAR2(255) NOT NULL
        , ACCESS_TYPE_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT KSEN_ROOM_ACCESS_TYPE_I1 UNIQUE (ROOM_ID, ACCESS_TYPE_ID)

)
/

ALTER TABLE KSEN_ROOM_ACCESS_TYPE
    ADD CONSTRAINT KSEN_ROOM_ACCESS_TYPEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_ACCESS_TYPE_IF1 
  ON KSEN_ROOM_ACCESS_TYPE 
  (ROOM_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_ATTR
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(4000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_ATTR
    ADD CONSTRAINT KSEN_ROOM_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_ATTR_IF1 
  ON KSEN_ROOM_ATTR 
  (OWNER_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_BUILDING
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_BUILDING';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_BUILDING CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_BUILDING
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , BUILDING_TYPE VARCHAR2(255) NOT NULL
        , BUILDING_STATE VARCHAR2(255) NOT NULL
        , NAME VARCHAR2(255)
        , DESCR_PLAIN VARCHAR2(4000)
        , DESCR_FORMATTED VARCHAR2(4000)
        , BUILDING_CD VARCHAR2(255) NOT NULL
        , CAMPUS_KEY VARCHAR2(255) NOT NULL
        , VER_NBR NUMBER(19) NOT NULL
        , CREATETIME TIMESTAMP NOT NULL
        , CREATEID VARCHAR2(255) NOT NULL
        , UPDATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
    
    , CONSTRAINT KSEN_ROOM_BUILDING_I2 UNIQUE (BUILDING_CD)

)
/

ALTER TABLE KSEN_ROOM_BUILDING
    ADD CONSTRAINT KSEN_ROOM_BUILDINGP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_BUILDING_I1 
  ON KSEN_ROOM_BUILDING 
  (CAMPUS_KEY)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_BUILDING_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_BUILDING_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_BUILDING_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_BUILDING_ATTR
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(4000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_BUILDING_ATTR
    ADD CONSTRAINT KSEN_ROOM_BUILDING_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_BUILDING_ATTR_IF1 
  ON KSEN_ROOM_BUILDING_ATTR 
  (OWNER_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_FIXED_RSRC
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_FIXED_RSRC';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_FIXED_RSRC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_FIXED_RSRC
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , FIXED_RSRC_TYPE VARCHAR2(255) NOT NULL
        , QUANTITY NUMBER(22)
        , VER_NBR NUMBER(19) NOT NULL
        , CREATETIME TIMESTAMP NOT NULL
        , CREATEID VARCHAR2(255) NOT NULL
        , UPDATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , ROOM_ID VARCHAR2(255) NOT NULL
    

)
/

ALTER TABLE KSEN_ROOM_FIXED_RSRC
    ADD CONSTRAINT KSEN_ROOM_FIXED_RSRCP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_FIXED_RSRC_IF1 
  ON KSEN_ROOM_FIXED_RSRC 
  (ROOM_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_FIXED_RSRC_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_FIXED_RSRC_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_FIXED_RSRC_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_FIXED_RSRC_ATTR
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(4000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_FIXED_RSRC_ATTR
    ADD CONSTRAINT KSEN_ROOM_FIXED_RSRC_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_FIXED_RSRC_ATTR_IF1 
  ON KSEN_ROOM_FIXED_RSRC_ATTR 
  (OWNER_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_RESP_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_RESP_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_RESP_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_RESP_ORG
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , RESP_ORG_TYPE VARCHAR2(255) NOT NULL
        , RESP_ORG_STATE VARCHAR2(255) NOT NULL
        , ROOM_ID VARCHAR2(255) NOT NULL
        , ORG_ID VARCHAR2(255) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , CREATETIME TIMESTAMP NOT NULL
        , CREATEID VARCHAR2(255) NOT NULL
        , UPDATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_RESP_ORG
    ADD CONSTRAINT KSEN_ROOM_RESP_ORGP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_RESP_ORG_I1 
  ON KSEN_ROOM_RESP_ORG 
  (RESP_ORG_TYPE)
/
CREATE INDEX KSEN_ROOM_RESP_ORG_IF1 
  ON KSEN_ROOM_RESP_ORG 
  (ROOM_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_RESP_ORG_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_RESP_ORG_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_RESP_ORG_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_RESP_ORG_ATTR
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(4000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_RESP_ORG_ATTR
    ADD CONSTRAINT KSEN_ROOM_RESP_ORG_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_RESP_ORG_ATTR_IF1 
  ON KSEN_ROOM_RESP_ORG_ATTR 
  (OWNER_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_USAGE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_USAGE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_USAGE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_USAGE
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , USAGE_TYPE VARCHAR2(255) NOT NULL
        , LAYOUT_TYPE VARCHAR2(255) NOT NULL
        , PREFERRED_CAPACITY NUMBER(22)
        , HARD_CAPACITY NUMBER(22)
        , VER_NBR NUMBER(19) NOT NULL
        , CREATETIME TIMESTAMP NOT NULL
        , CREATEID VARCHAR2(255) NOT NULL
        , UPDATETIME TIMESTAMP
        , UPDATEID CHAR(18)
        , ROOM_ID VARCHAR2(255) NOT NULL
    

)
/

ALTER TABLE KSEN_ROOM_USAGE
    ADD CONSTRAINT KSEN_ROOM_USAGEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_USAGE_I1 
  ON KSEN_ROOM_USAGE 
  (USAGE_TYPE)
/
CREATE INDEX KSEN_ROOM_USAGE_IF1 
  ON KSEN_ROOM_USAGE 
  (ROOM_ID)
/





-----------------------------------------------------------------------------
-- KSEN_ROOM_USAGE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ROOM_USAGE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ROOM_USAGE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ROOM_USAGE_ATTR
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(4000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_ROOM_USAGE_ATTR
    ADD CONSTRAINT KSEN_ROOM_USAGE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEN_ROOM_USAGE_ATTR_IF1 
  ON KSEN_ROOM_USAGE_ATTR 
  (OWNER_ID)
/

