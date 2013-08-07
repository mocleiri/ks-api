--New 12 Anti Req rules
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values           ('Y',null,'CLUID-BSCI330-200708000000:10005:1','KS-SYS','KS-KRMS-PROP-13852','KS-KRMS-RULE-10158','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values           ('Y',null,'CLUID-PHYS685-200001000000:10005:1','KS-SYS','KS-KRMS-PROP-13920','KS-KRMS-RULE-10159','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'fdc7800d-45f6-4c40-80c8-c530bebbb628:10005:1','KS-SYS','KS-KRMS-PROP-13901','KS-KRMS-RULE-10160','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'3c18aff2-eb51-4872-b544-d617c1a2a4f6:10005:1','KS-SYS','KS-KRMS-PROP-13931','KS-KRMS-RULE-10161','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'98835c9e-79df-48e5-8372-c957497754ec:10005:1','KS-SYS','KS-KRMS-PROP-13934','KS-KRMS-RULE-10162','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'26418faf-6bd2-4dac-9570-d1ac09c5eab0:10005:1','KS-SYS','KS-KRMS-PROP-13966','KS-KRMS-RULE-10163','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'2c16e552-67b9-4fea-837a-5fcda06bae5c:10005:1','KS-SYS','KS-KRMS-PROP-13850','KS-KRMS-RULE-10164','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'9cdb07b7-f43d-45ad-a769-7b58c520581e:10005:1','KS-SYS','KS-KRMS-PROP-13923','KS-KRMS-RULE-10165','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'c8572d44-87fa-484f-836f-80e926c7ce76:10005:1','KS-SYS','KS-KRMS-PROP-13937','KS-KRMS-RULE-10166','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'cd05c291-7bf3-4ea7-bfb6-0432b32013ac:10005:1','KS-SYS','KS-KRMS-PROP-14079','KS-KRMS-RULE-10167','10005',1)
/
INSERT INTO KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) VALUES ('Y',NULL,'be1052b5-5c16-4a1c-94a7-73de24f62556:10005:1','KS-SYS','KS-KRMS-PROP-14196','KS-KRMS-RULE-10168','10005',1)
/
Insert into KRMS_RULE_T (ACTV,DESC_TXT,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR) values ('Y',null,'bd2178f9-597d-4eb6-a1c3-5c53dad69d72:10005:1','KS-SYS','KS-KRMS-PROP-14618','KS-KRMS-RULE-10169','10005',1)
/


--Update the 12 child props with new rule IDs
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10158' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13852'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10160' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13901'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10165' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13923'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10163' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13966'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10167' WHERE PROP_ID LIKE 'KS-KRMS-PROP-14079'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10168' WHERE PROP_ID LIKE 'KS-KRMS-PROP-14196'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10164' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13850'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10159' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13920'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10161' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13931'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10162' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13934'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10166' WHERE PROP_ID LIKE 'KS-KRMS-PROP-13937'
/
Update KRMS_PROP_T SET RULE_ID = 'KS-KRMS-RULE-10169' WHERE PROP_ID LIKE 'KS-KRMS-PROP-14618'
/

--New Agenda Items
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11356','KS-KRMS-AGENDA-ITM-10158',null,'KS-KRMS-RULE-10158',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11436','KS-KRMS-AGENDA-ITM-10160',null,'KS-KRMS-RULE-10160',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11479','KS-KRMS-AGENDA-ITM-10159',null,'KS-KRMS-RULE-10159',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11523','KS-KRMS-AGENDA-ITM-10161',null,'KS-KRMS-RULE-10161',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11525','KS-KRMS-AGENDA-ITM-10162',null,'KS-KRMS-RULE-10162',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11585','KS-KRMS-AGENDA-ITM-10163',null,'KS-KRMS-RULE-10163',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11315','KS-KRMS-AGENDA-ITM-10164',null,'KS-KRMS-RULE-10164',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11610','KS-KRMS-AGENDA-ITM-10165',null,'KS-KRMS-RULE-10165',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11613','KS-KRMS-AGENDA-ITM-10166',null,'KS-KRMS-RULE-10166',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11662','KS-KRMS-AGENDA-ITM-10167',null,'KS-KRMS-RULE-10167',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11707','KS-KRMS-AGENDA-ITM-10168',null,'KS-KRMS-RULE-10168',null,2,null,null)
/
Insert into KRMS_AGENDA_ITM_T (AGENDA_ID,AGENDA_ITM_ID,ALWAYS,RULE_ID,SUB_AGENDA_ID,VER_NBR,WHEN_FALSE,WHEN_TRUE) values ('KS-KRMS-AGENDA-11883','KS-KRMS-AGENDA-ITM-10169',null,'KS-KRMS-RULE-10169',null,2,null,null)
/

--Update Agenda items and link our newly created items
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10164' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11668'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10158' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11410'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10160' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11494'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10159' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11541'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10161' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11586'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10162' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11588'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10163' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11654'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10165' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11680'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10166' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11721'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10167' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11743'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10168' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11791'
/
UPDATE KRMS_AGENDA_ITM_T SET WHEN_TRUE = 'KS-KRMS-AGENDA-ITM-10169' WHERE AGENDA_ITM_ID LIKE 'KS-KRMS-AGENDA-ITM-11980'
/

--Clean up relationships with old compound props
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13848'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13851'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13900'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13919'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13921'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13930'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13933'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13936'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-13965'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-14075'
/
delete from KRMS_CMPND_PROP_PROPS_T where CMPND_PROP_ID like 'KS-KRMS-PROP-14194'
/
DELETE FROM KRMS_CMPND_PROP_PROPS_T WHERE CMPND_PROP_ID LIKE 'KS-KRMS-PROP-14617'
/
delete from KRMS_CMPND_PROP_PROPS_T where PROP_ID like 'KS-KRMS-PROP-13933'
/

--this one becomes a child of its grandparent
Insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID,PROP_ID) values ('KS-KRMS-PROP-13932','KS-KRMS-PROP-13935')
/

--update rules to replace compound parents with remaining siblings
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13849' where PROP_ID like 'KS-KRMS-PROP-13848'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13216' where PROP_ID like 'KS-KRMS-PROP-13851'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13442' where PROP_ID like 'KS-KRMS-PROP-13900'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13556' where PROP_ID like 'KS-KRMS-PROP-13919'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13922' where PROP_ID like 'KS-KRMS-PROP-13921'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13653' where PROP_ID like 'KS-KRMS-PROP-13930'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13938' where PROP_ID like 'KS-KRMS-PROP-13936'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-13797' where PROP_ID like 'KS-KRMS-PROP-13965'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-14076' where PROP_ID like 'KS-KRMS-PROP-14075'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-14195' where PROP_ID like 'KS-KRMS-PROP-14194'
/
Update KRMS_RULE_T SET PROP_ID = 'KS-KRMS-PROP-14619' where PROP_ID like 'KS-KRMS-PROP-14617'
/

--delete the orphaned compound props
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13848'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13851'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13900'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13919'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13921'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13930'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13933'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13936'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-13965'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-14075'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-14194'
/
delete from KRMS_PROP_T where PROP_ID like 'KS-KRMS-PROP-14617'
/