package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

// Note: un-ignore and test within eclipse because the data for courseservice are
// not working via command-line: mvn clean install

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImpl {

    @Resource  // look up bean via variable name, then type
	private CourseOfferingService coServiceValidationDecorator;

    @Resource
    private GradingService gradingService;

    @Resource
    private LRCService lrcService;

    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

	@Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testServiceSetup() {
    	assertNotNull(coServiceValidationDecorator);
    }
	   
    @Test
    public void testGetCourseOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
    	try {
            try{
    			coServiceValidationDecorator.getCourseOffering("Lui-blah", callContext);
                fail("Lui-blah should have thrown DoesNotExistException");
    		}
            catch (DoesNotExistException enee) {
                // expected
            }
    		
    		CourseOfferingInfo co = coServiceValidationDecorator.getCourseOffering("Lui-1", callContext);
    		assertNotNull(co);
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, co.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co.getTypeKey());
            assertEquals("Lui Desc 101", co.getDescr().getPlain());
    	} catch (Exception ex) {
    		fail(ex.getMessage());
    	}    	
    }

    @Test
    public void testCreateCourseOfferingFromCanonical() throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
    	List<String> formatIdList = new ArrayList<String>();
    	CourseOfferingInfo created = coServiceValidationDecorator.createCourseOfferingFromCanonical(
                "CLU-1", "testAtpId1", formatIdList, callContext);
    	assertNotNull(created);
    	assertEquals("CLU-1", created.getCourseId());
    	assertEquals("testAtpId1", created.getTermKey());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey()); 

        CourseOfferingInfo retrieved = coServiceValidationDecorator.getCourseOffering(created.getId(), callContext);
    	assertNotNull(retrieved);
    	assertEquals("CLU-1", retrieved.getCourseId());
    	assertEquals("testAtpId1", retrieved.getTermKey());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey()); 

        assertEquals("CHEM123", retrieved.getCourseOfferingCode());
        assertEquals("Chemistry 123", retrieved.getCourseTitle());

        // make sure a roster was created
        List<GradeRosterInfo> rosters =
                gradingService.getFinalGradeRostersForCourseOffering(retrieved.getId(), callContext);
        assertNotNull(rosters);
        assertEquals(1, rosters.size());

        // test update
        retrieved.setStateKey(LuiServiceConstants.LUI_APROVED_STATE_KEY);
        ResultValuesGroupInfo rv = new ResultValuesGroupInfo();
        rv.setStateKey("test");
        rv.setTypeKey("test");
        rv.setEffectiveDate(Calendar.getInstance().getTime());
        rv.setName("test");
        ResultValueRangeInfo rvr = new ResultValueRangeInfo();
        rv.setResultValueRange(rvr);
        lrcService.createResultValuesGroup(rv, callContext);
        retrieved.setCreditOptions(rv);
        try {
			coServiceValidationDecorator.updateCourseOffering(retrieved.getId(), retrieved, callContext);
			
			CourseOfferingInfo retrieved2 =
                    coServiceValidationDecorator.getCourseOffering(retrieved.getId(), callContext);
			assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, retrieved2.getStateKey()); 
		} catch (VersionMismatchException ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
    }
    
    @Test
    public void testCreateAndGetActivityOffering()
            throws AlreadyExistsException, DataValidationErrorException,InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
		ActivityOfferingInfo ao = new ActivityOfferingInfo();
		ao.setActivityId("CLU-1");
		ao.setTermKey("testAtpId1");
		ao.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		ao.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
		
		List<MeetingScheduleInfo> schedules = new ArrayList<MeetingScheduleInfo>();
		MeetingScheduleInfo schedule1 = new MeetingScheduleInfo();
		schedule1.setSpaceId("room 314");
		schedule1.setTimePeriods("19960415T083000");
		schedules.add(schedule1);
		MeetingScheduleInfo schedule2 = new MeetingScheduleInfo();
		schedule2.setSpaceId("room 316");
		schedule2.setTimePeriods("19960415T083000Z");
		schedules.add(schedule2);
		ao.setMeetingSchedules(schedules);
		
    	List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
    	OfferingInstructorInfo instructor = new OfferingInstructorInfo();
    	instructor.setPersonId("Pers-1");
    	instructor.setPercentageEffort(Float.valueOf("60"));
    	instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
    	instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
    	instructors.add(instructor);
    	ao.setInstructors(instructors);

        List<String> coIdList = Arrays.asList("Lui-1");

        try {
            ActivityOfferingInfo created =
                    coServiceValidationDecorator.createActivityOffering(coIdList, ao, callContext);
            assertNotNull(created);

            ActivityOfferingInfo retrieved =
                    coServiceValidationDecorator.getActivityOffering(created.getId(), callContext);
            assertNotNull(retrieved);

            assertEquals(created.getActivityId(), retrieved.getActivityId());
            assertEquals(created.getTermKey(), retrieved.getTermKey());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
            assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, retrieved.getTypeKey());
            assertEquals(2, retrieved.getMeetingSchedules().size());
            assertEquals(1, retrieved.getInstructors().size());

            // test getActivitiesForCourseOffering
            List<ActivityOfferingInfo> activities =
                    coServiceValidationDecorator.getActivitiesForCourseOffering("Lui-1", callContext);
            assertNotNull(activities);
            assertEquals(1, activities.size());
            assertEquals(created.getActivityId(), activities.get(0).getActivityId());
            assertEquals(created.getId(), activities.get(0).getId());
            assertEquals(2, activities.get(0).getMeetingSchedules().size());
            assertEquals(1, activities.get(0).getInstructors().size());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            fail("Exception from service call :" + ex.getMessage());
        }
    }
    
    @Test
    @Ignore
	public void testCreateAndGetRegistrationGroup() throws AlreadyExistsException, DoesNotExistException,DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,PermissionDeniedException {
    	String courseOfferingId = "Lui-1";
		RegistrationGroupInfo rg = new RegistrationGroupInfo();
		rg.setFormatId("CLU-1");
		rg.setName("RegGroup-1");
		rg.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
		
		try {
			RegistrationGroupInfo created =  coServiceValidationDecorator.createRegistrationGroup(courseOfferingId, rg, callContext);
			assertNotNull(created);
			
			RegistrationGroupInfo retrieved = coServiceValidationDecorator.getRegistrationGroup(created.getId(), callContext);
			assertNotNull(retrieved);
			assertEquals("CLU-1", retrieved.getFormatId());
			assertEquals("RegGroup-1", retrieved.getName());
		    assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
		    assertEquals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, retrieved.getTypeKey());
		    
		    // test getRegGroupsForCourseOffering
		    List<RegistrationGroupInfo> rgs = coServiceValidationDecorator.getRegGroupsForCourseOffering(courseOfferingId, callContext);
		    assertNotNull(rgs);
		    assertTrue(rgs.size() == 1);
		    assertEquals(rgs.get(0).getFormatId(), "CLU-1");
		    assertEquals(rgs.get(0).getId(), created.getId());
		    assertEquals(rgs.get(0).getCourseOfferingId(), courseOfferingId);
		} catch (Exception ex) {
    		fail("exception from service call :" + ex.getMessage());
		}
	}
    
	@Test
	@Ignore
    public void testUpdateCourseOffering() throws DataValidationErrorException, 
    		DoesNotExistException, InvalidParameterException, MissingParameterException, 
    		OperationFailedException, PermissionDeniedException,VersionMismatchException {
	    	try{
		    	CourseOfferingInfo obj = coServiceValidationDecorator.getCourseOffering("Lui-1", callContext);
		    	assertNotNull(obj);
		    	
		    	obj.setTermKey("testAtpId1");
		    	obj.setIsHonorsOffering(true);
		    	obj.setMaximumEnrollment(40);
		    	obj.setMinimumEnrollment(10);
		    	List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
		    	OfferingInstructorInfo instructor = new OfferingInstructorInfo();
		    	instructor.setPersonId("Pers-1");
		    	instructor.setPercentageEffort(Float.valueOf("60"));
		    	instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
		    	instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
		    	instructors.add(instructor);
		    	obj.setInstructors(instructors);
		    	CourseOfferingInfo updated = coServiceValidationDecorator.updateCourseOffering("Lui-1", obj, callContext);
		    	assertNotNull(updated);
		    	
		    	CourseOfferingInfo retrieved = coServiceValidationDecorator.getCourseOffering("Lui-1", callContext);
		    	assertNotNull(retrieved);
		    	assertTrue(retrieved.getIsHonorsOffering());
		    	assertTrue(retrieved.getInstructors().size() == 1);
		    	assertEquals(Integer.valueOf(40), retrieved.getMaximumEnrollment());
		    	assertEquals(Integer.valueOf(10), retrieved.getMinimumEnrollment());
		    	
		    	retrieved.setIsHonorsOffering(false);
		    	List<OfferingInstructorInfo> instructors1 = new ArrayList<OfferingInstructorInfo>();
		    	OfferingInstructorInfo instructor1 = new OfferingInstructorInfo();
		    	instructor1.setPersonId("Pers-2");
		    	instructor1.setPercentageEffort(Float.valueOf("60"));
		    	instructor1.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
		    	instructor1.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
		    	instructors1.add(instructor1);
		    	OfferingInstructorInfo instructor2 = new OfferingInstructorInfo();
		    	instructor2.setPersonId("Pers-1");
		    	instructor2.setPercentageEffort(Float.valueOf("30"));
		    	instructor2.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
		    	instructor2.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
		    	instructors1.add(instructor2);
		    	retrieved.setInstructors(instructors1);
		    	CourseOfferingInfo updated1 = coServiceValidationDecorator.updateCourseOffering("Lui-1", retrieved, callContext);
		    	assertNotNull(updated1);
		    	CourseOfferingInfo retrieved1 = coServiceValidationDecorator.getCourseOffering("Lui-1", callContext);
		    	assertNotNull(retrieved1);
		    	assertTrue(retrieved1.getInstructors().size() == 2);
	    	} catch (Exception ex) {
	    		fail("exception from service call :" + ex.getMessage());
			}
    }   
}
