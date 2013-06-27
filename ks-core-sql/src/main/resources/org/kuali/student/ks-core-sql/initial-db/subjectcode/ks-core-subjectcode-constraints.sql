
ALTER TABLE KSSC_SUBJ_CD
    ADD CONSTRAINT KSSC_SUBJ_CD_FK1 FOREIGN KEY (TYPE)
    REFERENCES KSSC_SUBJ_CD_TYPE (TYPE_KEY)
/


ALTER TABLE KSSC_SUBJ_CD_JN_ORG
    ADD CONSTRAINT KSSC_SUBJ_CD_JN_ORG_FK1 FOREIGN KEY (SUBJ_CD_ID)
    REFERENCES KSSC_SUBJ_CD (ID)
/

