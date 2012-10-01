INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  values ('1','1','1','${kr.krad.url}/krmsAgendaEditor?methodToCall=docHandler','Create a KRMS Agenda','3050','AgendaEditorMaintenanceDocument','0','1','KRMS Agenda Editor Maintenance Document','ebd70731-4d33-4c0b-a958-2b9ca047ae07','2681','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor','2','0')
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
        values ('1','1','1','${kr.krad.url}/maintenance?methodToCall=docHandler','Create a New Agenda','3051','AgendaMaintenanceDocument','0','1','Agenda Maintenance Document','3198b708-6e29-4b19-bf35-51473cf8a3d1','2681','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor','2','0')
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,LBL,PREV_DOC_TYP_VER_NBR,DOC_TYP_DESC,DOC_HDLR_URL,POST_PRCSR,JNDI_URL,BLNKT_APPR_PLCY,ADV_DOC_SRCH_URL,RTE_VER_NBR,NOTIFY_ADDR,APPL_ID,EMAIL_XSL,SEC_XML,VER_NBR,BLNKT_APPR_GRP_ID,RPT_GRP_ID,GRP_ID,HELP_DEF_URL,OBJ_ID,DOC_SEARCH_HELP_URL,DOC_HDR_ID )
        values ('3052','2681','PeopleFlowMaintenanceDocument','0','1','1','PeopleFlowMaintenanceDocument',NULL,NULL,'${kr.krad.url}/peopleFlowMaintenance?methodToCall=docHandler',NULL,NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,'2c0a1333-f60b-47c4-a9b0-76f32f1ed97d',NULL,NULL)
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,LBL,PREV_DOC_TYP_VER_NBR,DOC_TYP_DESC,DOC_HDLR_URL,POST_PRCSR,JNDI_URL,BLNKT_APPR_PLCY,ADV_DOC_SRCH_URL,RTE_VER_NBR,NOTIFY_ADDR,APPL_ID,EMAIL_XSL,SEC_XML,VER_NBR,BLNKT_APPR_GRP_ID,RPT_GRP_ID,GRP_ID,HELP_DEF_URL,OBJ_ID,DOC_SEARCH_HELP_URL,DOC_HDR_ID )
        values ('3053','2681','ContextMaintenanceDocument','0','1','1','KRMS Context Maintenance Document',NULL,'Create a KRMS Context','${kr.krad.url}/maintenance?methodToCall=docHandler','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,'1','1',NULL,'1',NULL,'87413487-8306-4130-b2df-a5d0e42243f9',NULL,NULL)
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,LBL,PREV_DOC_TYP_VER_NBR,DOC_TYP_DESC,DOC_HDLR_URL,POST_PRCSR,JNDI_URL,BLNKT_APPR_PLCY,ADV_DOC_SRCH_URL,RTE_VER_NBR,NOTIFY_ADDR,APPL_ID,EMAIL_XSL,SEC_XML,VER_NBR,BLNKT_APPR_GRP_ID,RPT_GRP_ID,GRP_ID,HELP_DEF_URL,OBJ_ID,DOC_SEARCH_HELP_URL,DOC_HDR_ID )
        values ('3054','2681','TermMaintenanceDocument','0','1','1','KRMS Term Maintenance Document',NULL,'Create a KRMS Term','${kr.krad.url}/maintenance?methodToCall=docHandler','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,'1','1',NULL,'1',NULL,'bc83f80b-85c4-40fd-998c-ef53fdd97e3f',NULL,NULL)
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,LBL,PREV_DOC_TYP_VER_NBR,DOC_TYP_DESC,DOC_HDLR_URL,POST_PRCSR,JNDI_URL,BLNKT_APPR_PLCY,ADV_DOC_SRCH_URL,RTE_VER_NBR,NOTIFY_ADDR,APPL_ID,EMAIL_XSL,SEC_XML,VER_NBR,BLNKT_APPR_GRP_ID,RPT_GRP_ID,GRP_ID,HELP_DEF_URL,OBJ_ID,DOC_SEARCH_HELP_URL,DOC_HDR_ID )
        values ('3055','2681','TermSpecificationMaintenanceDocument','0','1','1','KRMS Term Specification Maintenance Document',NULL,'Create a KRMS Term Specification','${kr.krad.url}/maintenance?methodToCall=docHandler','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,'1','1',NULL,'1',NULL,'57e7ee1d-e44a-4154-9ba4-ee562c434c98',NULL,NULL)
/