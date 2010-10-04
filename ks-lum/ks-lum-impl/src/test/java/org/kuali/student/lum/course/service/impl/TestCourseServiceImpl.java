package org.kuali.student.lum.course.service.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.IllegalVersionSequencingException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseFeeInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:course-test-context.xml"})
public class TestCourseServiceImpl {
    @Autowired
    CourseService courseService;
    @Autowired
    StatementService statementService;

    Set<String> subjectAreaSet = new TreeSet<String>(Arrays.asList(CourseDataGenerator.subjectAreas));

    @Test
    public void testCreateCourse() throws Exception {
     System.out.println ("testCreateCourse");
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = null;
        try {
            assertNotNull(cInfo = generator.getCourseTestData());
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);
            assertEquals("draft", createdCourse.getState());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
            assertEquals(cInfo.getStartTerm(),createdCourse.getStartTerm());
            assertEquals(cInfo.getEndTerm(),createdCourse.getEndTerm());
        }
        catch (DataValidationErrorException e)
        {
           dumpValidationErrors (cInfo);
           fail("DataValidationError: " + e.getMessage());
        }  catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void dumpValidationErrors (CourseInfo cInfo) throws Exception {
      List<ValidationResultInfo> validationResults = courseService.validateCourse ("SYSTEM", cInfo);
      for (ValidationResultInfo vr : validationResults) {
       System.out.println (vr.getElement () + " " + vr.getMessage ());
      }
    }

    @Test
    public void testGetCourse() {
     System.out.println ("testGetCourse");
        try {
            CourseDataGenerator generator = new CourseDataGenerator();
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            cInfo.setSpecialTopicsCourse(true);
            cInfo.setPilotCourse(true);
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);

            // get it fresh from database
            CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId());
            assertNotNull(retrievedCourse);

            // confirm it has the right contents
            assertEquals("323", retrievedCourse.getCode().substring(4));
            assertEquals("323", retrievedCourse.getCourseNumberSuffix());

            assertEquals("courseTitle-12", retrievedCourse.getCourseTitle());
            assertEquals("transcriptTitle-49", retrievedCourse.getTranscriptTitle());

            assertEquals("plain-18", retrievedCourse.getDescr().getPlain());
            assertEquals("formatted-17", retrievedCourse.getDescr().getFormatted());

            assertEquals(2, retrievedCourse.getFormats().size());
            FormatInfo info = retrievedCourse.getFormats().get(0);
            assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getType());
            assertEquals(2, info.getActivities().size());
            assertTrue(info.getActivities().get(1).getActivityType().startsWith("kuali.lu.type.activity."));

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String termOffered = retrievedCourse.getTermsOffered().get(0);

            assertTrue("termsOffered-48".equals(termOffered) || "termsOffered-49".equals(termOffered));

            assertEquals(2, retrievedCourse.getUnitsContentOwner().size());
            String orgId = retrievedCourse.getUnitsContentOwner().get(0);
            assertTrue("unitsContentOwner-53".equals(orgId) || "unitsContentOwner-54".equals(orgId));

            assertEquals(4, retrievedCourse.getAttributes().size());
            String[] attrKeys = {"attributes-3", "attributes-4"};
            for (String key : attrKeys) {
                String value = retrievedCourse.getAttributes().get(key);
                assertNotNull(value);
                assertEquals(key, value);
            }

            assertEquals(2, retrievedCourse.getCampusLocations().size());
            String campus = retrievedCourse.getCampusLocations().get(1);
            assertTrue(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH.equals(campus) || CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH.equals(campus));

            /*
             * Test LO assertEquals(2, retrievedCourse.getCourseSpecificLOs().size()); LoDisplayInfo info =
             * retrievedCourse.getCourseSpecificLOs().get(0); // TODO - check its contents
             */

            /*
             * assertEquals(2, retrievedCourse.getCrossListings().size()); CourseCrossListingInfo info =
             * retrievedCourse.getCrossListings().get(0); // TODO - check its contents
             */

            assertEquals("unitsDeployment-56", retrievedCourse.getUnitsDeployment().get(0));

            TimeAmountInfo timeInfo = retrievedCourse.getDuration();
            assertEquals("kuali.atp.duration.Semester", timeInfo.getAtpDurationTypeKey());
            assertEquals(19, timeInfo.getTimeQuantity().intValue());

            // TODO - check effective/expiration dates

            // TODO - check feeInfo


            // TODO - check joints
            // TODO - check metaInfo

            assertEquals(2, retrievedCourse.getTermsOffered().size());

            String atpType = retrievedCourse.getTermsOffered().get(0);
            CluInstructorInfo instructor = retrievedCourse.getPrimaryInstructor();



            assertTrue("termsOffered-48".equals(atpType) || "termsOffered-49".equals(atpType));

            assertEquals("orgId-42", instructor.getOrgId());
            assertEquals("personId-43", instructor.getPersonId());

            assertEquals("draft", retrievedCourse.getState());
            assertTrue(subjectAreaSet.contains(retrievedCourse.getSubjectArea()));

            assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getType());

            assertEquals(2,retrievedCourse.getCreditOptions().size());
            assertEquals("kuali.creditType.credit.degree.11",retrievedCourse.getCreditOptions().get(0).getId());
            assertEquals("kuali.creditType.credit.degree.11",retrievedCourse.getCreditOptions().get(1).getId());

            assertEquals(2,retrievedCourse.getGradingOptions().size());

            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-31"));
            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-32"));

            assertEquals(createdCourse.isPilotCourse(),cInfo.isPilotCourse());
            assertEquals(createdCourse.isSpecialTopicsCourse(), cInfo.isSpecialTopicsCourse());

            // TODO - check variotions
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateCourse() throws Exception {
       System.out.println ("testUpdateCourse");

       CourseDataGenerator generator = new CourseDataGenerator();
       CourseInfo cInfo = null;
       CourseInfo retrievedCourse = null;
       CourseInfo updatedCourse = null;
       CourseInfo createdCourse = null;
       try {
        System.out.println ("Getting test data...");
       cInfo = generator.getCourseTestData();
       } catch (Exception ex) {
        ex.printStackTrace();
        fail ("Got exception getting test data:" + ex.getMessage ());
       }

       assertNotNull(cInfo);
       cInfo.setSpecialTopicsCourse(true);
       cInfo.setPilotCourse(true);
       try {
        System.out.println ("creating course...");
         createdCourse = courseService.createCourse(cInfo);
        } catch (DataValidationErrorException e) {
           dumpValidationErrors (cInfo);
           fail("DataValidationError: " + e.getMessage());
        } catch (Exception ex) {
         ex.printStackTrace();
         fail ("failed creating course" + ":" + ex.getMessage ());
        }
            int initialFormatCount = createdCourse.getFormats().size();

            // minimal sanity check
            assertNotNull(createdCourse);
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
            assertEquals("courseTitle-12", createdCourse.getCourseTitle());
            assertEquals(2, createdCourse.getUnitsContentOwner().size());
            assertEquals(4, createdCourse.getAttributes().size());

            // update some fields
            createdCourse.getUnitsContentOwner().clear();
            AdminOrgInfo testCurrOrg = new AdminOrgInfo();
            testCurrOrg.setOrgId("testOrgId");
            testCurrOrg.setType(CourseAssemblerConstants.SUBJECT_ORG);
            createdCourse.getUnitsContentOwner().add("testOrgId");

            // Delete One Format
            createdCourse.getFormats().remove(0);

            // Delete One Activity from Existing Format
            assertEquals(2, createdCourse.getFormats().get(0).getActivities().size());
            createdCourse.getFormats().get(0).getActivities().remove(0);
            String updActFrmtId = createdCourse.getFormats().get(0).getId();

            // Add two New formats
            FormatInfo newFormat = new FormatInfo();
            newFormat.setType(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
            newFormat.setState("DRAFT");
            Map<String, String> attrMap = new HashMap<String, String>();
            attrMap.put("FRMT", "value");
            newFormat.setAttributes(attrMap);

            // Add two new activities to new formats
            ActivityInfo newActivity1 = new ActivityInfo();
            newActivity1.setActivityType(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE);
            newActivity1.setState("DRAFT");
            newFormat.getActivities().add(newActivity1);

            ActivityInfo newActivity2 = new ActivityInfo();
            newActivity2.setActivityType(CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE);
            newActivity2.setState("DRAFT");
            newFormat.getActivities().add(newActivity2);

            createdCourse.getFormats().add(newFormat);

            FormatInfo newFormat2 = new FormatInfo();
            newFormat2.setType(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
            newFormat2.setState("DRAFT");
            createdCourse.getFormats().add(newFormat2);

            Map<String, String> attributes = createdCourse.getAttributes();
            attributes.put("testKey", "testValue");
            createdCourse.setAttributes(attributes);

            createdCourse.getCreditOptions().remove(1);
            ResultComponentInfo rsltComp = new ResultComponentInfo();
            rsltComp.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);
            rsltComp.getResultValues().add("1");
            rsltComp.getResultValues().add("3");
            createdCourse.getCreditOptions().add(rsltComp);
            createdCourse.getGradingOptions().remove(1);
            createdCourse.getGradingOptions().add("NewGradingOption");

            createdCourse.setSpecialTopicsCourse(false);
            createdCourse.setPilotCourse(false);

            createdCourse.getCourseSpecificLOs().get(0).getLoInfo().getDesc().setPlain("UPDATED!!!");
            createdCourse.getCourseSpecificLOs().remove(1);
            LoDisplayInfo displayInfo = new LoDisplayInfo();
            displayInfo.setLoInfo(new LoInfo());
            displayInfo.getLoInfo().setDesc(new RichTextInfo());
            createdCourse.getCourseSpecificLOs().add(displayInfo);
            createdCourse.getCourseSpecificLOs().get(1).getLoInfo().getDesc().setPlain("BrandNew!!!");
            createdCourse.getCourseSpecificLOs().get(1).getLoCategoryInfoList().add(new LoCategoryInfo());
            createdCourse.getCourseSpecificLOs().get(1).getLoCategoryInfoList().get(0).setId("category-3");

            createdCourse.getFeeJustification().setFormatted("NEWJUSTIFICATION");
            createdCourse.getFees().clear();
            createdCourse.getFees().add(new CourseFeeInfo());
            createdCourse.getFees().get(0).setFeeType("UpdatedFeeType");
            createdCourse.getFees().get(0).getFeeAmounts().clear();
            createdCourse.getFees().get(0).getFeeAmounts().add(new CurrencyAmountInfo());
            createdCourse.getFees().get(0).getFeeAmounts().get(0).setCurrencyQuantity(10);
            createdCourse.getFees().get(0).getFeeAmounts().get(0).setCurrencyTypeKey("PESOS");
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().clear();
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().add(new AffiliatedOrgInfo());
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).setOrgId("NEWORG");
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).setPercentage(Long.valueOf(99));


            //Perform the update
            try {
            System.out.println ("updating course...");
            updatedCourse = courseService.updateCourse(createdCourse);
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (createdCourse);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception ex) {
             ex.printStackTrace();
             fail ("failed updating course: " + ex.getMessage ());
            }
            assertEquals(initialFormatCount + 1, updatedCourse.getFormats().size());

            for (FormatInfo uFrmt : updatedCourse.getFormats()) {
                // Check to see if activities are added to a new format
                if (uFrmt.getAttributes().containsKey("FRMT")) {
                    assertEquals(2, uFrmt.getActivities().size());
                    String actType = uFrmt.getActivities().get(0).getActivityType();
                    assertTrue(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE.equals(actType) || CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE.equals(actType));
                }

                // Check to see if activity is deleted from an existing format
                if(updActFrmtId.equals(uFrmt.getId())) {
                    assertEquals(1, uFrmt.getActivities().size());
                }
            }
            // Test what was returned by updateCourse
            verifyUpdate(updatedCourse);

            // Now explicitly get it
            try {
            System.out.println ("Getting course again...");
            retrievedCourse = courseService.getCourse(createdCourse.getId());
            } catch (Exception ex) {
             ex.printStackTrace();
             fail ("failed getting course again:" + ex.getMessage ());
            }
            verifyUpdate(retrievedCourse);

            // and test for optimistic lock exception
            // NOTE: CourseService.updateCourse(CourseInfo courseInfo) modifies its parameter,
            // as the 'results' BusinessDTORef (our CourseInfo) is simply updated to reflect
            // the new contents of the updated Clu (see the
            // results.getAssembler().assemble(updatedClu, results.getBusinessDTORef(), true);
            // line in CourseServiceMethodInvoker.invokeServiceCalls()
            int currVersion = Integer.parseInt(retrievedCourse.getMetaInfo().getVersionInd());
            if (currVersion > 0) {
                retrievedCourse.getMetaInfo().setVersionInd(Integer.toString(--currVersion));
            }
            try {
                System.out.println ("Updating course again trying to get a version mismatch...");
                courseService.updateCourse(retrievedCourse);
                fail("Failed to throw VersionMismatchException");
            } catch (VersionMismatchException e) {
                System.out.println("Correctly received " + e.getMessage());
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (retrievedCourse);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
        	    e.printStackTrace();
             fail(e.getMessage());
            }
    }

    private void verifyUpdate(CourseInfo updatedCourse) {
        assertNotNull(updatedCourse);

        assertEquals(1, updatedCourse.getUnitsContentOwner().size());
        assertEquals("testOrgId", updatedCourse.getUnitsContentOwner().get(0));

        assertEquals(5, updatedCourse.getAttributes().size());
        assertNotNull(updatedCourse.getAttributes().get("testKey"));
        assertEquals("testValue", updatedCourse.getAttributes().get("testKey"));

        assertEquals(2,updatedCourse.getCreditOptions().size());
//        assertTrue(updatedCourse.getCreditOptions().contains("creditOptions-18"));
//        assertTrue(updatedCourse.getCreditOptions().contains("NewCreditOption"));

        assertEquals(2,updatedCourse.getGradingOptions().size());

        assertTrue(updatedCourse.getGradingOptions().contains("gradingOptions-31"));
        assertTrue(updatedCourse.getGradingOptions().contains("NewGradingOption"));

        assertFalse(updatedCourse.isSpecialTopicsCourse());
        assertFalse(updatedCourse.isPilotCourse());


        assertEquals("NEWJUSTIFICATION",updatedCourse.getFeeJustification().getFormatted());
        assertEquals("UpdatedFeeType",updatedCourse.getFees().get(0).getFeeType());
        assertEquals(Integer.valueOf(10),updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyQuantity());
        assertEquals("PESOS",updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyTypeKey());
        assertEquals("NEWORG",updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getOrgId());
        assertEquals(Long.valueOf(99),updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getPercentage());
    }

    @Test
    public void testDeleteCourse() {
       System.out.println ("testDeleteCourse");
        try {
            CourseDataGenerator generator = new CourseDataGenerator();
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);
            assertEquals("draft", createdCourse.getState());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
            String courseId = createdCourse.getId();
            CourseInfo retrievedCourse = courseService.getCourse(courseId);
            assertNotNull(retrievedCourse);

            courseService.deleteCourse(courseId);
            try {
                retrievedCourse = courseService.getCourse(courseId);
                fail("Retrieval of deleted course should have thrown exception");
            } catch (DoesNotExistException e) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testDynamicAttributes() {
     System.out.println ("testDynamicAttributes");
        CourseDataGenerator generator = new CourseDataGenerator();
         try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);

            Map<String, String> attrMap = new HashMap<String, String>();
            attrMap.put("finalExamStatus","GRD");
            attrMap.put("altFinalExamStatusDescr", "Some123description");
            attrMap.put("proposalTitle", "proposalTitle-1");
            attrMap.put("proposalRationale", "proposalRationale");

            cInfo.setAttributes(attrMap);

            try {
            cInfo = courseService.createCourse(cInfo);
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (cInfo);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
        	    e.printStackTrace();
             fail("failed creating course:" + e.getMessage());
            }
            // Check in LuService if the attributes are mapped properly

            //CourseInfo rInfo = courseService.getCourse(cInfo.getId());

            assertEquals("GRD", cInfo.getAttributes().get("finalExamStatus"));
            assertEquals("Some123description", cInfo.getAttributes().get("altFinalExamStatusDescr"));

        } catch (Exception e) {
            System.out.println ("caught exception: " + e.getClass ().getName ());
            System.out.println ("message: " + e.getMessage ());
            e.printStackTrace (System.out);
            e.printStackTrace();
            fail (e.getMessage ());
        }


    }



    @Test
    public void testCluIsUpdated() {

    }

	@Test
	public void testGetMetadata(){
  System.out.println ("testGetMetadata");
		MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
		metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.course.dto.CourseInfo");

        Map<String, Metadata> properties = metadata.getProperties();
        assertTrue(properties.size() > 0);

        assertTrue(properties.containsKey("state"));
        assertTrue(properties.containsKey("campusLocations"));

        assertTrue(properties.containsKey("formats"));
        metadata = properties.get("formats");

        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");

        properties = metadata.getProperties();
        assertTrue(properties.containsKey("activities"));
        metadata = properties.get("activities");

        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");

        properties = metadata.getProperties();
        assertFalse(properties.containsKey("foo"));

        return;
	}

	@Test
	public void testCourseVersioning() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, IllegalVersionSequencingException{
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo);

        try{
        	courseService.createNewCourseVersion(createdCourse.getVersionInfo().getVersionIndId(), "test make a new version");
        	assertTrue(false);
        }catch(Exception e){
        	assertTrue(true);
        }

        //Make the created the current version
        courseService.setCurrentCourseVersion(createdCourse.getId(), null);

        CourseInfo newCourse = null;
        try{
        	newCourse = courseService.createNewCourseVersion(createdCourse.getVersionInfo().getVersionIndId(), "test make a new version");
        	assertTrue(true);
        }catch(Exception e){
        	assertTrue(false);
        }


        assertNotNull(newCourse);

	}

	@Test
	public void testGetCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		String courseId = "COURSE-STMT-1";
		List<StatementTreeViewInfo> courseStatements = courseService.getCourseStatements(courseId, null, null);
		assertEquals(2, courseStatements.size());
		for (StatementTreeViewInfo tree : courseStatements) {
			checkTreeView(tree, false);
		}
	}

	@Test
	@Ignore // FIXME
	public void testGetCourseStatement_nl() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		String courseId = "COURSE-STMT-1";
		String nlUsageTypeKey = "KUALI.RULE";
		String language = "en";
		List<StatementTreeViewInfo> courseStatements = courseService.getCourseStatements(courseId, nlUsageTypeKey, language);
		assertEquals(2, courseStatements.size());
		for (StatementTreeViewInfo tree : courseStatements) {
			checkTreeView(tree, true);
		}
	}

	@Test
	public void testCreateCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		StatementTreeViewInfo createdTree = courseService.createCourseStatement(courseId, statementTreeViewInfo );
		assertNotNull(createdTree);
		assertEquals(2, createdTree.getStatements().size());
	}

	@Test(expected=InvalidParameterException.class)
	public void testCreateCourseStatement_duplicateTree() throws Exception {
		String courseId = "COURSE-STMT-1";
		String nlUsageTypeKey = "KUALI.RULE";
		String language = "en";
		List<StatementTreeViewInfo> courseStatements = courseService.getCourseStatements(courseId, nlUsageTypeKey, language);
		courseService.createCourseStatement(courseId, courseStatements.get(0));
	}

	@Test(expected=MissingParameterException.class)
	public void testCreateCourseStatement_nullCourseId() throws Exception {

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		@SuppressWarnings("unused")
		StatementTreeViewInfo createdTree = courseService.createCourseStatement(null, statementTreeViewInfo );
	}

	@Test(expected=MissingParameterException.class)
	public void testCreateCourseStatement_nullTree() throws Exception {
		String courseId = "COURSE-STMT-1";

		@SuppressWarnings("unused")
		StatementTreeViewInfo createdTree = courseService.createCourseStatement(courseId, null );
	}

	@Test(expected=DoesNotExistException.class)
	public void testDeleteCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		StatementTreeViewInfo createdTree = courseService.createCourseStatement(courseId, statementTreeViewInfo );
		StatusInfo status = courseService.deleteCourseStatement(courseId, createdTree);
		assertTrue(status.getSuccess());
		List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseId, null, null);
		for (StatementTreeViewInfo statement : statements) {
			if (statement.getId().equals(createdTree.getId())) {
				fail("StatementTree not deleted from course");
			}
		}
		statementService.getStatementTreeView(createdTree.getId());
	}

	@Test(expected=DoesNotExistException.class)
	public void testDeleteCourseStatement_badTree() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		courseService.deleteCourseStatement(courseId, statementTreeViewInfo);
	}

	@Test(expected=DoesNotExistException.class)
	public void testDeleteCourseStatement_badCourse() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		courseService.deleteCourseStatement("xxx", statementTreeViewInfo);
	}

	@Test(expected=MissingParameterException.class)
	public void testDeleteCourseStatement_nullCourseId() throws Exception {
		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		courseService.deleteCourseStatement(null, statementTreeViewInfo);
	}

	@Test(expected=MissingParameterException.class)
	public void testDeleteCourseStatement_nullTreeId() throws Exception {
		courseService.deleteCourseStatement("xxx", null);
	}

	@Test
	public void testUpdateCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		StatementTreeViewInfo createdTree = courseService.createCourseStatement(courseId, statementTreeViewInfo );

        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDesc(toRichText("REQCOMP-1"));
        rc1.setType("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDesc(toRichText("REQCOMP-2"));
        rc2.setType("kuali.reqComponent.type.course.courseset.gpa.min");
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDesc(toRichText("STMT-5"));
        subTree1.setOperator(StatementOperatorTypeKey.AND);
        subTree1.setType("kuali.statement.type.program.entrance");
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);

        StatementTreeViewInfo oldSubTree1 = createdTree.getStatements().get(0);
        createdTree.getStatements().set(0, subTree1);
		StatementTreeViewInfo updatedTree = courseService.updateCourseStatement(courseId, statementTreeViewInfo );
		assertEquals(createdTree.getStatements().get(0).getDesc().getPlain(), updatedTree.getStatements().get(0).getDesc().getPlain());

	}

	@Test
	@Ignore // FIXME need a dictionary that defines StatamentTreeViewInfo
	public void testValidataCourseStatement() throws Exception {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		courseService.validateCourseStatement(courseId, statementTreeViewInfo);
		List<ValidationResultInfo> validations = courseService.validateCourseStatement(courseId, statementTreeViewInfo);
		assertTrue(isEmpty(validations));
	}

	@Test
	@Ignore // FIXME need a dictionary that defines StatamentTreeViewInfo
	public void testValidataCourseStatement_invalidStatement() throws InvalidParameterException, MissingParameterException, OperationFailedException {
		final String courseId = "COURSE-STMT-1";

		StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
		statementTreeViewInfo.setType("an.example.of.a.bad.statementType");
		statementTreeViewInfo.getStatements().get(0).setType("fictional.program");
		statementTreeViewInfo.getStatements().get(0).getReqComponents().set(0, createBadReqComponent());
		List<ValidationResultInfo> validations = courseService.validateCourseStatement(courseId, statementTreeViewInfo);
		assertFalse(isEmpty(validations));
	}


	private static ReqComponentInfo createBadReqComponent() {
		ReqComponentInfo reqCompInfo = new ReqComponentInfo();
//		reqCompInfo.setId("REQCOMP-NL-X");
		reqCompInfo.setId("1234567890123456789012345678901234567890");
		reqCompInfo.setType("kuali.reqComponent.type.courseList.nof");
		reqCompInfo.setState("Active");

		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();

		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("1234567890123456789012345678901234567890");
		field1.setType("kuali.reqComponent.field.type.operator");
		field1.setValue("-1");
		fieldList.add(field1);

		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("2");
		field2.setType("kuali.reqComponent.field.type.operator");
		field2.setValue("greater_than_or_equal_to42");
		fieldList.add(field2);

		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("3");
		field3.setType("kuali.reqComponent.field.type.cluSet.id");
		field3.setValue("CLUSET-NL-Y");
		fieldList.add(field3);

		reqCompInfo.setReqCompFields(fieldList);
		return reqCompInfo;
	}

	private static StatementTreeViewInfo createStatementTree() {
        // Statement Tree
        //                --------- STMT-1:OR ---------
    	//                |                           |
        //           STMT-2:AND                  STMT-3:AND
    	//           |        |                  |        |
        //      REQCOMP-1  REQCOMP-2        REQCOMP-3  REQCOMP-4

        List<StatementTreeViewInfo> subStatements = new ArrayList<StatementTreeViewInfo>(3);
        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        List<ReqComponentInfo> reqCompList2 = new ArrayList<ReqComponentInfo>(3);

        // req components
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDesc(toRichText("REQCOMP-1"));
        rc1.setType("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDesc(toRichText("REQCOMP-2"));
        rc2.setType("kuali.reqComponent.type.course.courseset.gpa.min");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDesc(toRichText("REQCOMP-3"));
        rc3.setType("kuali.reqComponent.type.course.courseset.completed.nof");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDesc(toRichText("REQCOMP-4"));
        rc4.setType("kuali.reqComponent.type.course.permission.instructor.required");

        // statement tree views
        StatementTreeViewInfo statementTree = new StatementTreeViewInfo();
        statementTree.setDesc(toRichText("STMT-1"));
        statementTree.setOperator(StatementOperatorTypeKey.OR);
        statementTree.setType("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDesc(toRichText("STMT-2"));
        subTree1.setOperator(StatementOperatorTypeKey.AND);
        subTree1.setType("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTree2.setDesc(toRichText("STMT-3"));
        subTree2.setOperator(StatementOperatorTypeKey.AND);
        subTree2.setType("kuali.statement.type.program.entrance");

        // construct tree with statements and req components
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);
        reqCompList2.add(rc3);
        reqCompList2.add(rc4);
        subTree2.setReqComponents(reqCompList2);
        subStatements.add(subTree1);
        subStatements.add(subTree2);
        statementTree.setStatements(subStatements);

        return statementTree;
    }

	private static RichTextInfo toRichText(String text) {
		RichTextInfo richTextInfo = new RichTextInfo();
		if (text == null) {
			return null;
		}
		richTextInfo.setPlain(text);
		richTextInfo.setFormatted("<p>" + text + "</p>");
		return richTextInfo;
	}


	private static void checkTreeView(final StatementTreeViewInfo rootTree,  final boolean checkNaturalLanguage) {
        assertNotNull(rootTree);
        List<StatementTreeViewInfo> subTreeView = rootTree.getStatements();
        assertNotNull(subTreeView);
        assertEquals(2, subTreeView.size());
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);

        // Check root tree
        assertNotNull(rootTree);
        assertEquals(2, subTreeView.size());
        assertNotNull(subTree1);
        assertNotNull(subTree2);

        // Check reqComps of sub-tree 1
        assertEquals("STMT-TV-2", subTree1.getId());
        assertEquals(2, subTree1.getReqComponents().size());
        assertEquals("REQCOMP-TV-1", subTree1.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-2", subTree1.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
        	assertEquals("Student must have completed all of MATH 152, MATH 180", subTree1.getReqComponents().get(0).getNaturalLanguageTranslation());
        	assertEquals("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180", subTree1.getReqComponents().get(1).getNaturalLanguageTranslation());
        }

        // Check reqComps of sub-tree 2
        assertEquals("STMT-TV-3", subTree2.getId());
        assertEquals(2, subTree2.getReqComponents().size());
        assertEquals("REQCOMP-TV-3", subTree2.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-4", subTree2.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
        	assertEquals("Student must have completed 1 of MATH 152, MATH 180", subTree2.getReqComponents().get(0).getNaturalLanguageTranslation());
        	assertEquals("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180", subTree2.getReqComponents().get(1).getNaturalLanguageTranslation());
        }
	}

}
