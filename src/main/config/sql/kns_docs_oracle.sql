CREATE SEQUENCE LU_ID_SEQ INCREMENT BY 1 START WITH 1000
;
CREATE TABLE LEARN_UNIT_T (
        LU_ID                          NUMBER(8) CONSTRAINT LEARN_UNIT_TN1 NOT NULL,
        OBJ_ID                         VARCHAR2(36) DEFAULT SYS_GUID() CONSTRAINT LEARN_UNIT_TN2 NOT NULL,
        VER_NBR                        NUMBER(8) DEFAULT 1 CONSTRAINT LEARN_UNIT_TN3 NOT NULL,
        STATE                          VARCHAR2(100) CONSTRAINT LEARN_UNIT_TN5 NOT NULL,
        TITLE                          VARCHAR2(100),
     CONSTRAINT LEARN_UNIT_TP1 PRIMARY KEY (
        LU_ID) ,
     CONSTRAINT LEARN_UNIT_TC0 UNIQUE (OBJ_ID) 
)
;
CREATE TABLE KS_DEPT_T (
        DEPT_CD                        VARCHAR2(4) CONSTRAINT KS_DEPT_TN1 NOT NULL,
        DEPT_DESC                      VARCHAR2(100),
        OBJ_ID                         VARCHAR2(36) DEFAULT SYS_GUID() CONSTRAINT KS_DEPT_TN2 NOT NULL,
        VER_NBR                        NUMBER(8) DEFAULT 1 CONSTRAINT KS_DEPT_TN3 NOT NULL,
     CONSTRAINT KS_DEPT_TP1 PRIMARY KEY (
        DEPT_CD),
     CONSTRAINT KS_DEPT_TC0 UNIQUE (OBJ_ID)
)
;
