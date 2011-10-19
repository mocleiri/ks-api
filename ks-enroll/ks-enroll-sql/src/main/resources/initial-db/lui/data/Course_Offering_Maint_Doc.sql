INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3019','2681','CourseOfferingInfoMaintenanceDocument',0,1,1,'Create a New Course Offering Maintenance Document','Course Offering Info Maintenance Document',NULL,'','${application.url}/spring/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor','1','1','','','2','','','','','48c8e031-ed88-4492-817a-848b2109808d',1)
/

INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2964','3019','Initiated','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'1','','P','','',1) 
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2621','2964','contentFragment','<start name="Initiated">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
')
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2622','2964','activationType','P') 
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2623','2964','mandatoryRoute','false') 
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2624','2964','finalApproval','false') 
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2625','2964','ruleSelector','Template') 
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2965','3019','2964','PRIMARY',1,1) 
/

UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=2 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 1 
/
