
--KSENROLL 6972
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%')
/

DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%')
/

DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%'
/

--KSENROLL 7024
--This is fixed by 6972

--KSENROLL 7023
UPDATE KRMS_NL_TMPL_T SET TMPL = 'Course that Restricts Credits'
  WHERE NL_USAGE_ID = 'KS-KRMS-NL-USAGE-1004' AND TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.credit.restriction')
/

--KSENROLL 6992
--This is fixed by 6972

--KSENROLL 6751
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.credit.repeatable'),'A',1,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.freeform.text'),'KS-KRMS-NL-USAGE-1117',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq'),'A',1,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.freeform.text'),'KS-KRMS-NL-USAGE-1118',0)
/

--KSENROLL 6756
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',2,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.course.courseset.completed.all'),'KS-KRMS-NL-USAGE-1119',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',3,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.course.courseset.completed.nof'),'KS-KRMS-NL-USAGE-1120',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',4,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'),'KS-KRMS-NL-USAGE-1121',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',5,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.course.courseset.credits.completed.nof'),'KS-KRMS-NL-USAGE-1122',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',6,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.credit.courseset.completed.nof'),'KS-KRMS-NL-USAGE-1123',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',7,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.course.courseset.gpa.min'),'KS-KRMS-NL-USAGE-1124',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',8,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.cumulative.gpa.min'),'KS-KRMS-NL-USAGE-1125',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',9,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.duration.cumulative.gpa.min'),'KS-KRMS-NL-USAGE-1126',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',10,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.course.courseset.grade.min'),'KS-KRMS-NL-USAGE-1127',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',11,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.course.courseset.nof.grade.min'),'KS-KRMS-NL-USAGE-1128',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',12,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.credits.courseset.completed.nof.org'),'KS-KRMS-NL-USAGE-1129',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',13,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.campus'),'KS-KRMS-NL-USAGE-1130',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',14,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.permission.instructor.required'),'KS-KRMS-NL-USAGE-1131',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',15,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.permission.admin.org'),'KS-KRMS-NL-USAGE-1132',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',18,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.credits.earned.min'),'KS-KRMS-NL-USAGE-1133',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',19,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.admitted.to.program'),'KS-KRMS-NL-USAGE-1134',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',20,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.compl.course.as.of.term'),'KS-KRMS-NL-USAGE-1135',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',21,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.compl.prior.to.term'),'KS-KRMS-NL-USAGE-1136',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',22,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.success.compl.course.between.terms'),'KS-KRMS-NL-USAGE-1137',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',23,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.org'),'KS-KRMS-NL-USAGE-1138',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',24,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.in.class.standing'),'KS-KRMS-NL-USAGE-1139',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',25,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.greater.than.class.standing'),'KS-KRMS-NL-USAGE-1140',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',26,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.less.than.class.standing'),'KS-KRMS-NL-USAGE-1141',0)
/