TRUNCATE TABLE KSLU_LUTYPE DROP STORAGE
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Core','F7E6BBECCF2C408E813497BBA8553E5A','Program containing core requirements','kuali.lu.type.CoreProgram',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credit Course','45F061A5111A417B8F401C0529809B5C','An course offered for academic credit','kuali.lu.type.CreditCourse',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credit Course Format Shell','31561FFA4B2043B98BE2ACC956C9FEE8','A shell for course formats','kuali.lu.type.CreditCourseFormatShell',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Major','E82DFABF0D99439FBF970CB09A4778B6','A Major Discipline','kuali.lu.type.MajorDiscipline',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Non-Credit Course','74F7443203534261A2E9654AE6867A79','A course that is not offered for regular academic credit','kuali.lu.type.NonCreditCourse',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Req','96EAAB2D6B884F388C3DA7858A6F3EB7','Program requirements','kuali.lu.type.Requirement',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Specialization','1CEBE9BAC710481DA398C766B8EE113F','Specialization of a Major Discipline','kuali.lu.type.Variation',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Directed','279D87863C84442D927F317C29E436DE','The exchange of opinions or questions on course material, directed by the instructor.','kuali.lu.type.activity.Directed',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Discussion','2C116DCB4BE147E58E22946268DAF7F3','The exchange of opinions or questions on course material.','kuali.lu.type.activity.Discussion',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Lab','528734054E4A44E4AAE2789490A0D463','Student working on projects in a defined laboratory space.  Instructors are on-hand for students to ask questions and guidance.','kuali.lu.type.activity.Lab',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Lecture','3CB868F97F804AC0B4AE0F84174AE701','Instructor presentation of course materials.','kuali.lu.type.activity.Lecture',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Tutorial','CF0872C0047A4809A3A77E813EEABDBF','Instructor or assistant walking through a learning practice.','kuali.lu.type.activity.Tutorial',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Lecture','55AE1CB7FDEE43DDAE8E4DA47767CE21','Instructor presentation of course materials via the web','kuali.lu.type.activity.WebLecture',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Baccalaureate','6A33734B7426482792951DD942080C05','A Baccalaureate','kuali.lu.type.credential.Baccalaureate',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Course','A8F346262C1B41B79C36BA0E27790BAB','A Shell Course','luType.shell.course',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program','0F7597C4E8DE4B6EBBCCCB8B631B81C6','A Shell Program','luType.shell.program',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Masters','6A33734B7426482792951DD942080C05','Masters level program','kuali.lu.type.credential.Masters',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Professional', sys_guid(),'A program leading to a professional degree','kuali.lu.type.credential.Professional',0)
/