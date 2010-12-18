
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1009', 'TESTUSER', TO_TIMESTAMP('4/15/2010 10:08:04.391000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 10:08:04.391000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'The Faculty of Science', 'Faculty of Science', 'FacultyScience', 'Active', 'kuali.org.Faculty');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1012', 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:22:35.540000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 2, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1000', '1009', 'kuali.org.UBCCurriculumChild');

Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1010', 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'The Faculty of Science Curriculum Committee', 'Faculty of Science Curriculum Committee', 'ScienceCurriculumCommittee', 'Active', 'kuali.org.COC');
   
   Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1011', 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Botany', 'Department of Botany', 'BotanyDept', 'Active', 'kuali.org.Department');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1013', 'TESTUSER', TO_TIMESTAMP('1/22/2009 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:22:35.542000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1009', '1010', 'kuali.org.UBCCurriculumChild');

Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1012', 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:44:16.790000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:44:16.790000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Botany Curriculum Committee', 'Botany Curriculum Committee', 'BotanyCOC', 'Active', 'kuali.org.COC');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1014', 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:47:37.615000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:52:30.198000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1011', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1015', 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:52:30.201000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:52:30.201000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1011', '1012', 'kuali.org.UBCCurriculumChild');
   
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1013', 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:56:35.093000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:56:35.093000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Chemistry', 'Department of Chemistry', 'ChemistryDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1014', 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:57:56.360000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 11:57:56.360000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Chemistry Curriculum Committee', 'Chemistry Curriculum Committee', 'ChemistryCOC', 'Active', 'kuali.org.COC');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1016', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:01:13.118000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:02:06.034000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1013', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1017', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:02:06.037000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:02:06.037000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1013', '1014', 'kuali.org.UBCCurriculumChild');

Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1015', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:07:32.204000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:07:32.204000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Computer Science', 'Department of Computer Science', 'ComputerScienceDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1016', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:09:44.313000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:09:44.313000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Computer Science Curriculum Committee', 'Computer Science Curriculum Committee', 'ComputerScienceCOC', 'Active', 'kuali.org.COC');
   
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1018', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:12:50.218000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:13:47.847000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1015', 'kuali.org.UBCCurriculumChild');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1019', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:13:47.848000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:13:47.848000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1015', '1016', 'kuali.org.UBCCurriculumChild');

Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1017', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:30:42.788000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:30:42.788000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Earth and Ocean Sciences', 'Department of Earth and Ocean Sciences', 'EarthandOceanSciencesDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1018', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:34:07.680000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:34:07.680000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Earth and Ocean Sciences Curriculum Committee', 'Earth and Ocean Sciences Curriculum Committee', 'EarthandOceanSciencesCOC', 'Active', 'kuali.org.COC');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1020', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:39:40.879000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:40:37.523000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1017', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1021', 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:40:37.525000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 12:40:37.525000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1017', '1018', 'kuali.org.UBCCurriculumChild');


Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1019', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:08:36.185000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:37:20.558000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Mathematics', 'Department of Mathematics', 'MathematicsDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1020', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:09:56.631000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:09:56.631000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Mathematics Curriculum Committee', 'Mathematics Curriculum Committee', 'MathematicsCOC', 'Active', 'kuali.org.COC');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1021', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:11:48.438000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:38:53.771000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Microbiology and Immunology', 'Department of Microbiology and Immunology', 'MicrobiologyandImmunologyDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1022', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:14:34.938000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:14:34.938000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Microbiology and Immunology Curriculum Committee', 'Microbiology and Immunology Curriculum Committee', 'MicrobiologyandImmunologyCOC', 'Active', 'kuali.org.COC');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1023', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:25:43.667000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:40:05.742000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'The Department of Physics and Astronomy', 'Department of Physics and Astronomy', 'PhysicsandAstronomyDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1024', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:26:37.069000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:26:37.069000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Physics and Astronomy Curriculum Committee', 'Physics and Astronomy Curriculum Committee', 'PhysicsandAstronomyCOC', 'Active', 'kuali.org.COC');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1025', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:27:41.343000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:44:42.402000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'The Department of Statistics', 'Department of Statistics', 'StatisticsDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1026', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:28:35.931000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:45:24.585000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'The Department of Zoology', 'Department of Zoology', 'ZoologyDept', 'Active', 'kuali.org.Department');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1027', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:29:35.142000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:29:35.142000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Zoology Curriculum Committee', 'Zoology Curriculum Committee', 'ZoologyCOC', 'Active', 'kuali.org.COC');
Insert into KSOR_ORG
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_NAME, ST, TYPE)
 Values
   ('1028', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:43:57.618000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:43:57.618000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Department of Statistics Curriculum Committee', 'Statistics Curriculum Committee', 'StatisticsCOC', 'Active', 'kuali.org.COC');

Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1022', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:36:01.054000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:37:20.565000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1019', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1024', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:36:01.055000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:38:53.775000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1021', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1026', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:36:01.057000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:40:05.748000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1023', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1028', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:36:01.058000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:44:42.406000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1025', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1030', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:36:01.059000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:45:24.588000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1010', '1026', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1023', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:37:20.569000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:37:20.569000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1019', '1020', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1025', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:38:53.777000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:38:53.777000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1021', '1022', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1027', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:40:05.750000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:40:05.750000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1023', '1024', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1029', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:44:42.415000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:44:42.415000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1025', '1028', 'kuali.org.UBCCurriculumChild');
Insert into KSOR_ORG_ORG_RELTN
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE)
 Values
   ('1031', 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:45:24.590000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'TESTUSER', TO_TIMESTAMP('4/15/2010 2:45:24.590000 PM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 1, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('12/31/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Active', '1026', '1027', 'kuali.org.UBCCurriculumChild');
