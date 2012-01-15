package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestAtpServiceImpl {

    @Resource(name="atpServiceAuthDecorator")
    public AtpService atpService;

    public static String principalId = "123";

    public ContextInfo callContext = null;

    
    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testAtpServiceValidationSetup() {
        assertNotNull(atpService);
    }

    @Test
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
								    MissingParameterException, OperationFailedException, PermissionDeniedException {
		try{
			AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
			assertNotNull(atpInfo);
			assertEquals("testAtpId1", atpInfo.getId());
			assertEquals("testAtp1", atpInfo.getName());
			assertEquals("Desc 101", atpInfo.getDescr().getPlain());
			assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
			assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
			try {
			    atpService.getAtp("totallyBogusAtpId999", callContext);
			    fail("AtpService did not throw DoesNotExistException on getAtp() of nonexistent ATP");
			}
			catch (DoesNotExistException dnee) {}
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
    }

    @Test
    public void testAtpCrud()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setId("newId");
        atpInfo.setName("newId");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("TestDesc1");
        atpInfo.setDescr(rt);
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        try {
            created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getId());
        } catch (AlreadyExistsException e) {
            fail(e.getMessage());
        } catch (DataValidationErrorException e) {
            fail(e.getMessage());
        }
        
        // test read
		AtpInfo fetched = atpService.getAtp("newId", callContext);
		assertNotNull(fetched);
		assertEquals("newId", fetched.getId());
		assertEquals("newId", fetched.getName());
		assertEquals("TestDesc1", fetched.getDescr().getPlain());
		assertEquals("kuali.atp.state.Draft", fetched.getStateKey());
		assertEquals("kuali.atp.type.AcademicCalendar", fetched.getTypeKey());
         
        // test update
        String atpNameOrig = fetched.getName();
        AtpInfo modified = new AtpInfo(fetched);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        try {
	        updated = atpService.updateAtp(fetched.getId(), modified, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
        
        // test delete
        atpInfo = atpService.getAtp("testDeleteAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId1", atpInfo.getId());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId1", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId1", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e){
            fail(e.getMessage());
        }
        // undo the update done above
        updated = atpService.getAtp(updated.getId(), callContext);
        updated.setName(atpNameOrig);
        
        try {
	        updated = atpService.updateAtp(updated.getId(), updated, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig, updated.getName());
    }
    
    @Test
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getId());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = new AtpInfo(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        try {
            updated = atpService.updateAtp(atpInfo.getId(), modified, callContext);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }
    
    @Test
    public void testCreateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setId("newId2");
        atpInfo.setName("newId2");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try {
            AtpInfo created = atpService.createAtp("newId2", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // attempt to get
        AtpInfo retrieved = atpService.getAtp("newId2", callContext);
        
        assertNotNull(retrieved);
        assertEquals("newId2", retrieved.getId());
    }
   
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpService.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getId());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId2", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId2", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetAtpsByDate()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2011, 0, 1);
        List<AtpInfo> atpInfos = atpService.getAtpsByDate(cal.getTime(), callContext);
        assertNotNull("'getAtpsByDate()' should return a list, not null", atpInfos);

        List<String> requiredKeys = new ArrayList<String>(Arrays.asList(
                "testAtpId1", "testAtpId2", "testTermId1", "testTermId2"));
        int listSize = requiredKeys.size();
        assertTrue("Date '"+ cal.getTime().toString() +"' should return at least "+listSize+" records",
                atpInfos.size() >= listSize);

        // make sure the required keys are in the retrieved list:
        for (AtpInfo atpInfo : atpInfos) {
            requiredKeys.remove(atpInfo.getId());
        }
        if (!requiredKeys.isEmpty()) {
            fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
        }
    }
    
    @Test
    public void testSearchForAtps() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testAtpId1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<AtpInfo> atpInfos = atpService.searchForAtps(qbc, callContext);
            assertNotNull(atpInfos);
            assertEquals(1, atpInfos.size());
            AtpInfo atpInfo = atpInfos.get(0);
            assertEquals("testAtpId1", atpInfo.getId());
            assertEquals("testAtp1", atpInfo.getName());
            assertEquals("Desc 101", atpInfo.getDescr().getPlain());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateMilestone() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setId("newId");
        milestone.setName("testCreate");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        
        milestone.setStartDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpService.createMilestone(milestone, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getId());
            assertEquals("testCreate", created.getName());
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        // try to get the just-created milestone
        MilestoneInfo found = atpService.getMilestone("newId", callContext);
        assertNotNull(found);
        
        // ensure we cannot create another of the same id
        MilestoneInfo dupeCreated = null;
        try {
            dupeCreated = atpService.createMilestone(milestone, callContext);
            fail("Did not get an AlreadyExistsException when expected");
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        } catch(DataValidationErrorException e){
        }
    }
        
    @Test
    public void testUpdateMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setId("newId2");
        milestone.setName("testCreate");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        
        milestone.setStartDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
       
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpService.createMilestone(milestone, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getId());
            assertEquals("testCreate", created.getName());
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        MilestoneInfo updateData = atpService.getMilestone("newId2", callContext);
        
        String updatedName = "updated " + updateData.getName();
        
        updateData.setName(updatedName);

        MilestoneInfo updated = null;
        try {
            updated = atpService.updateMilestone("newId2", updateData, callContext);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(updated.getId(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        // now fetch the updated milestone fresh, and check fields
        updated = atpService.getMilestone("newId2", callContext);
        assertNotNull(updated);
        assertEquals(updated.getId(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getId());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());
        
        MilestoneInfo fakeMilestone = null;
        try {
            fakeMilestone = atpService.getMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeMilestone);
        }
    }
    
    @Test
    public void testGetMilestonesByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> milestoneIds = new ArrayList<String>();
        milestoneIds.addAll(Arrays.asList("testId", "testId2"));
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByIds(milestoneIds, callContext);
        
        assertNotNull(milestones);
        assertEquals(milestoneIds.size(), milestones.size());
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            milestoneIds.remove(info.getId());
        }
        
        assertTrue(milestoneIds.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("testId", "fakeKey1", "fakeKey2");
        
        List<MilestoneInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestonesByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestonesByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2011);
        
        Date startDate = cal.getTime();
        
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Date endDate = cal.getTime();
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        assertNotNull("getMilestonesByDates() should return a list, not null", milestones);

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.addAll(Arrays.asList("testId", "testId2"));
        int listSize = expectedIds.size();
        assertTrue("Should have returned at least "+ listSize +" records", milestones.size() >= listSize);

        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getId());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        cal.set(Calendar.YEAR, 1990);
        
        startDate = cal.getTime();
        endDate = cal.getTime();
        
        milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        
        assertTrue(milestones == null || milestones.isEmpty());
    }
    
    @Test
    public void testGetMilestoneIdsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = "kuali.atp.milestone.RegistrationPeriod";
        List<String> milestoneIds = atpService.getMilestoneIdsByType(expectedMilestoneType, callContext);
        assertTrue(milestoneIds.contains("testId2"));
        
        String fakeMilestoneType = "fakeTypeKey";
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestoneIdsByType(fakeMilestoneType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetMilestonesForAtp() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp("testAtpId1", callContext);
        
        assertNotNull(milestones);
        assertEquals(1, milestones.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("testId");
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getId());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<MilestoneInfo> fakeMilestones = null;
        
        try {
            fakeMilestones = atpService.getMilestonesForAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeMilestones);
        }
        
    }
    
    @Test
    public void testSearchForMilestones()throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testId2"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(qbc, callContext);
            assertNotNull(milestoneInfos);
            assertEquals(1, milestoneInfos.size());
            MilestoneInfo milestoneInfo = milestoneInfos.get(0);
            assertEquals("testId2", milestoneInfo.getId());
            assertEquals("testId2", milestoneInfo.getName());

        } catch (Exception e) {
            fail(e.getMessage());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 5, 1);
        Predicate startPredicate = PredicateFactory.greaterThanOrEqual("startDate", new Timestamp(calendar.getTime().getTime()));
        calendar.set(2011, 11, 30);
        Predicate endPredicate = PredicateFactory.lessThanOrEqual("endDate", new Timestamp(calendar.getTime().getTime()));
        qbcBuilder.setPredicates(startPredicate, endPredicate);
        qbc = qbcBuilder.build();
        try {
            List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(qbc, callContext);
            assertNotNull(milestoneInfos);
            assertEquals(2, milestoneInfos.size());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

//    @Test
//    public void testGetType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        try {
//	        TypeInfo typeInfo = atpService.getType(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, callContext);
//	        assertNotNull(typeInfo);
//	        try {
//		        typeInfo = atpService.getType("totally.bogus.type.key", callContext);
//		        fail("Did not receive DoesNotExistException when getting nonexistent TypeInfo");
//	        } catch (DoesNotExistException dnee) { /* expected */ }
//	    } catch (Exception e) {
//	        fail(e.getMessage());
//	    }
//    }

//    @Test
//    public void testGetTypesByRefObjectURI() {
//        try {
//            List<TypeInfo> typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
//            assertNotNull("'getTypesByRefObjectURI()' should return a List<>, not null", typeInfos);
//
//            assertTrue("URI '" + AtpServiceConstants.REF_OBJECT_URI_ATP + "' should have returned at least 26 TypeInfo objects", typeInfos.size() >= 26);
//
//            typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_MILESTONE, callContext);
//            assertNotNull("'getTypesByRefObjectURI()' should return a List<>, not null", typeInfos);
//
//            List<String> requiredTypeKeys = new ArrayList<String>(Arrays.asList(
//                    "kuali.atp.milestone.AdvanceRegistrationPeriod",
//                    "kuali.atp.milestone.RegistrationPeriod",
//                    "kuali.atp.milestone.DropDate",
//                    "kuali.atp.milestone.GradesDue"));
//            int listSize = requiredTypeKeys.size();
//            assertTrue("URI '"+AtpServiceConstants.REF_OBJECT_URI_MILESTONE+"' should have returned at least "+listSize+" TypeInfo objects",
//                    typeInfos.size() >= listSize);
//
//            // make sure the required type keys are in the retrieved list:
//            for (TypeInfo typeInfo : typeInfos) {
//                requiredTypeKeys.remove(typeInfo.getKey());
//            }
//            if (!requiredTypeKeys.isEmpty()) {
//                fail("Failed to find type key '"+requiredTypeKeys.get(0)+"' in TypeInfo list");
//            }
//
//            try {
//	            typeInfos = atpService.getTypesByRefObjectURI("totally.bogus.object.uri", callContext);
//		        fail("Did not receive DoesNotExistException when getting TypeInfos for nonexistent refObjectURI");
//	        } catch (DoesNotExistException dnee) { /* expected */ }
//	    } catch (Exception e) {
//	        fail("Caught "+ e.toString() +": "+ e.getMessage());
//	    }
//    }
//
//    @Test
//    public void testGetState()
//            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
//    	try{
//    		StateInfo stateInfo = atpService.getState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
//    		assertNotNull(stateInfo);
//    		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
//    	 } catch (Exception e) {
// 	        fail("Caught "+ e.toString() +": "+ e.getMessage());
// 	    }
//    }
//
//    @Test
//    public void testGetStatesByProcess()
//            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
//            OperationFailedException{
//    	try{
//    		List<StateInfo> stateInfos =
//                    atpService.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
//    		assertNotNull("getStatesByProcess() should return a list, not null", stateInfos);
//
//    		 List<String> requiredKeys = new ArrayList<String>(Arrays.asList(
//                "kuali.atp.state.Draft", "kuali.atp.state.Official"));
//            int listSize = requiredKeys.size();
//            assertTrue("Key '"+ AtpServiceConstants.ATP_PROCESS_KEY +"' should return at least "+listSize+" records",
//                stateInfos.size()>=listSize);
//
//            // make sure the required keys are in the retrieved list:
//            for (StateInfo stateInfo : stateInfos) {
//                requiredKeys.remove(stateInfo.getKey());
//            }
//            if (!requiredKeys.isEmpty()) {
//                fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
//            }
//    	 } catch (Exception e) {
// 	        fail("Caught "+ e.toString() +": "+ e.getMessage());
// 	    }
//    }
//
//    @Test
//    public void testGetProcessByKey()
//            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
//            OperationFailedException {
//    	StateProcessInfo spInfo = atpService.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
//    	assertNotNull(spInfo);
//		assertEquals(AtpServiceConstants.ATP_PROCESS_KEY, spInfo.getKey());
//    }
//
//    @Test
//    public void testGetInitialValidStates()
//            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
//            OperationFailedException {
//        List<StateInfo> stateInfos =
//                atpService.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
//        assertNotNull("'getInitialValidStates()' should return a list, not null", stateInfos);
//
//        List<String> requiredKeys = new ArrayList<String>(Arrays.asList("kuali.atp.state.Draft"));
//        int listSize = requiredKeys.size();
//        assertTrue("Key '"+ AtpServiceConstants.ATP_PROCESS_KEY +"' should return at least "+listSize+" records",
//                stateInfos.size() >= listSize);
//
//        // make sure the required keys are in the retrieved list:
//        for (StateInfo stateInfo : stateInfos) {
//            requiredKeys.remove(stateInfo.getKey());
//        }
//        if (!requiredKeys.isEmpty()) {
//            fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
//        }
//    }
//
//    @Test
//    public void testGetNextHappyState()
//            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
//        StateInfo stateInfo = atpService.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
//        assertNotNull(stateInfo);
//        assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, stateInfo.getKey());
//    }

    @Test
    public void testValidateAtpAtpRelation()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpId("testAtp1");
        atpRel.setRelatedAtpId("testAtp2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
            List<ValidationResultInfo> vri= atpService.validateAtpAtpRelation("FULL_VALIDATION", "testAtp1", "testAtp2", AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, atpRel, callContext);
            assertTrue(vri.isEmpty());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreatAtpAtpRelation()
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpId("testAtpId1");
        atpRel.setRelatedAtpId("testAtpId2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
            atpService.createAtpAtpRelation("testAtpId1","testAtpId2",atpRel, callContext);
        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        try{
            AtpInfo atpInfo = new AtpInfo();
            atpInfo.setId("testAtpId1-newCC");
            atpInfo.setName("testAtpId1 to new holiday calendar");
            atpInfo.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
            atpInfo.setStateKey("kuali.atp.state.Draft");
            atpInfo.setStartDate(Calendar.getInstance().getTime());
            atpInfo.setEndDate(Calendar.getInstance().getTime());
            AtpInfo cc = null;
            cc = atpService.createAtp("testAtpId1-newCC", atpInfo, callContext);
            assertNotNull(cc);

            atpRel.setRelatedAtpId("testAtpId1-newCC");
            AtpAtpRelationInfo created = atpService.createAtpAtpRelation("testAtpId1","testAtpId1-newCC",atpRel, callContext);
            assertNotNull(created);

            AtpAtpRelationInfo retrieved = atpService.getAtpAtpRelation(created.getId(), callContext);
            assertEquals("testAtpId1", retrieved.getAtpId());
            assertEquals("testAtpId1-newCC", retrieved.getRelatedAtpId());
            assertEquals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }
//
//    @Test
//    public void testGetDataDictionaryEntry()
//            throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
//        DictionaryEntryInfo value = atpService.getDataDictionaryEntry("http://student.kuali.org/wsdl/atp/AtpInfo", callContext);
//
//        assertNotNull(value);
//
//        DictionaryEntryInfo fakeEntry = null;
//        try {
//            fakeEntry = atpService.getDataDictionaryEntry("fakeKey", callContext);
//            fail("Did not get a DoesNotExistException when expected");
//        }
//        catch(DoesNotExistException e) {
//            assertNull(fakeEntry);
//        }
//    }
//
//    @Test
//    public void testGetDataDictionaryEntryKeys()
//            throws OperationFailedException, MissingParameterException, PermissionDeniedException {
//        List<String> results = atpService.getDataDictionaryEntryKeys(callContext);
//
//        assertNotNull(results);
//        assertTrue(!results.isEmpty());
//
//        assertTrue(results.contains("http://student.kuali.org/wsdl/atp/AtpInfo"));
//    }

    @Test
    public void testGetAtpAtpRelationsByTypeAndAtp() {
        try {
            List<AtpAtpRelationInfo> aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testTermId1", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
            assertEquals(1, aaRelInfos.size());
            assertEquals("testTermId2", aaRelInfos.get(0).getRelatedAtpId());

	            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("totallyBogusAtpIdJustMadeUpForTesting", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
                assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
//	            fail("atpService.getAtpAtpRelationsByAtpAndRelationType() did not throw expected DoesNotExistException for nonexistent Atp ID");
            // should return empty list for bogus relation type
            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testAtpId1", "totallyBogusRelationTypeJustMadeUpForTesting", callContext);
            // sigh. service is returning an empty list, but client seems to get a null. have asked KSDevs chat about how to fix; not finding a solution via my google-fu
            assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
            // assertEquals(0, aaRelInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetAtpsByKeys()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpIds = new ArrayList<String>();
        atpIds.addAll(Arrays.asList("testAtpId1", "testAtpId2"));
        
        List<AtpInfo> atps = atpService.getAtpsByIds(atpIds, callContext);
        
        assertNotNull(atps);
        assertEquals(atpIds.size(), atps.size());
        
        // check that all the expected ids came back
        for(AtpInfo info : atps) {
            atpIds.remove(info.getId());
        }
        assertTrue(atpIds.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        List<String> fakeKeys = Arrays.asList("testAtpId1", "fakeKey1", "fakeKey2");
        List<AtpInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpsByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
    public void testGetAtpsByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Calendar startCal = Calendar.getInstance();
        startCal.clear();
        startCal.set(2000, 8, 1); // Sept 1 2000
        Calendar endCal = Calendar.getInstance();
        endCal.clear();
        endCal.set(2001, 5, 1); // June 1 2001

        List<AtpInfo> atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertTrue("There should be at least 5 Academic Time Periods between 9/1/00 and 6/1/01.",
                atpInfos.size() >= 5);
        int priorSize = atpInfos.size();

        endCal.add(Calendar.DAY_OF_YEAR, -1); // Roll back to May 31 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);

        int expectedSize = priorSize - 1;
        assertEquals("There should be "+ expectedSize +" Academic Time Periods between 9/1/00 and 5/31/01.",
                expectedSize, atpInfos.size());

        startCal.add(Calendar.DAY_OF_YEAR, 1); // Sept 2 2000
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertEquals("There should be 1 Academic Time Period between 9/2/00 and 5/31/01.", 1, atpInfos.size());

        startCal.set(2001, 0, 2); // Jan 2 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull("getAtpsByDates() should return an empty list instead of null.", atpInfos);
        assertEquals("There should be zero Academic Time Periods between 1/2/01 and 5/31/01.", 0, atpInfos.size());
    }

    @Test
    public void testGetAtpIdsByType()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedAtpType = AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY;
        List<String> atpIds = atpService.getAtpIdsByType(expectedAtpType, callContext);
        assertTrue(atpIds.contains("testAtpId1"));
        assertTrue(atpIds.contains("termRelationTestingAcal1"));
        
        String expectedEmptyAtpType = AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY;
        atpIds = atpService.getAtpIdsByType(expectedEmptyAtpType, callContext);
        assertTrue(atpIds == null || atpIds.isEmpty());
        
        String fakeAtpType = "fakeTypeKey";
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpIdsByType(fakeAtpType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }

//    @Test
//    public void testGetAllowedTypesForType() {
//        try {
//            List<TypeInfo> typeInfos = atpService.getAllowedTypesForType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
//            assertEquals(6, typeInfos.size());
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }
    
//    @Test
//    public void testGetTypeRelationsByOwnerType() {
//        try {
//            List<TypeTypeRelationInfo> typeInfos = atpService.getTypeRelationsByOwnerType(AtpServiceConstants.ATP_AY_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, callContext);
//            assertNotNull(typeInfos);
//            assertEquals(3, typeInfos.size());
//	    } catch (Exception e) {
//	        fail(e.getMessage());
//	    }
//    }

    @Test
    public void testGetImpactedMilestones() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<MilestoneInfo> impactedMilestones = atpService.getImpactedMilestones("FALLTERM1990INSTRUCTIONPERIOD", callContext);
        assertNotNull(impactedMilestones);
        List<String> impactedMilestoneIds = new ArrayList<String>();
        for (MilestoneInfo impactedMilestone : impactedMilestones) {
            impactedMilestoneIds.add(impactedMilestone.getId());
        }
        assertTrue(impactedMilestoneIds.contains("FALLTERM1990CENSUS"));
    }

    @Test
    public void testCalculateMilestone() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        // Census start needs to be recalculated to 14-sept-1990
        final Date censusExpectedStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 14)).getTime();
        final Date instructionStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 3)).getTime();

        final String censusId = "FALLTERM1990CENSUS";
        final String instructionPeriodId = "FALLTERM1990INSTRUCTIONPERIOD";

        MilestoneInfo census = atpService.getMilestone(censusId, callContext);
        assertFalse(censusExpectedStartDate.equals(census.getStartDate()));
        MilestoneInfo instructionPeriod = atpService.getMilestone(instructionPeriodId, callContext);
        assertEquals(instructionStartDate, instructionPeriod.getStartDate());

        census = atpService.calculateMilestone(censusId, callContext);
        assertTrue("Milestone start date not calculated as expected.", censusExpectedStartDate.equals(census.getStartDate()));
        census = atpService.getMilestone(censusId, callContext);
        // TODO should the milestone be saved in the calculation method or is that a seperate call?
        assertFalse("Milestone was saved after calculation.", censusExpectedStartDate.equals(census.getStartDate()));
    }
}
